package bankservice;

import Bank.*;
import bank.Bank;
import bank.ExchangeRateServiceThread;
import com.zeroc.Ice.Current;
import sr.grpc.gen.Currency;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Logger;

import static utils.CurrencyMapper.getCurrencyFor;

public class AccountImpl implements Account {
    private static final Logger logger = Logger.getLogger(Bank.class.getName());

    private final String name;
    private final String lastName;
    private final String pesel;
    private final MoneyAmount monthIncome;
    private final AccountType accountType;
    private final ExchangeRateServiceThread exchangeRateService;
    private final String uuidPassword;

    public AccountImpl(PersonData data, MoneyAmount monthIncome, AccountType accountType,
                       ExchangeRateServiceThread exchangeRateService, String password) {
        this.name = data.name;
        this.lastName = data.lastName;
        this.pesel = data.pesel;
        this.monthIncome = monthIncome;
        this.accountType = accountType;
        this.exchangeRateService = exchangeRateService;
        this.uuidPassword = password;
    }

    @Override
    public CreditCostPair getCreditCost(MoneyAmount amount, Date finishDate, Current current)
            throws NotSupportedCurrencyException, AccountTypeException {
        if (accountType == AccountType.STANDARD) {
            throw new AccountTypeException();
        }

        logger.info(String.format("evaluating credit costs for: %d %s", amount.amount, amount.currency));

        LocalDateTime currentDate = LocalDateTime.now();
        int yearDifference = finishDate.year - currentDate.getYear();
        int monthDifference = finishDate.month - currentDate.getMonthValue();

        Map<Currency, Float> exchangeRates = exchangeRateService.getRates();
        float rate = exchangeRates.getOrDefault(getCurrencyFor(amount.currency), 0f);
        if (rate == 0f) {
            throw new NotSupportedCurrencyException();
        }

        long creditCost = (long) ((amount.amount * 0.05f) * (monthDifference + yearDifference * 12));
        MoneyAmount nativeCurrencyMoney = new MoneyAmount((long) (creditCost * rate), CurrencyIce.PLN);
        MoneyAmount actualCurrencyMoney = new MoneyAmount(creditCost, amount.currency);
        return new CreditCostPair(nativeCurrencyMoney, actualCurrencyMoney);
    }

    @Override
    public MoneyAmount getActualAmount(Current current) {
        logger.info(String.format("actual amount of money for: %s %s is %d %s", name, lastName,
                5 * monthIncome.amount, monthIncome.currency));

        return new MoneyAmount(5 * monthIncome.amount, monthIncome.currency);
    }

    public boolean authorize(String uuid) {
        return uuidPassword.equals(uuid);
    }
}
