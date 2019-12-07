package bankservice;

import Bank.*;
import bank.ExchangeRateServiceThread;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import sr.grpc.gen.Currency;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import static utils.CurrencyMapper.getCurrencyFor;

public class BankServiceImpl implements BankService {
    private static final Logger logger = Logger.getLogger(BankServiceImpl.class.getName());
    private final ExchangeRateServiceThread exchangeRateService;

    public BankServiceImpl(ExchangeRateServiceThread exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public String createAccount(PersonData personData, MoneyAmount monthIncome, Current current)
            throws NotSupportedCurrencyException {
        AccountType type = resolveAccountType(monthIncome);
        logger.info(String.format("creating %s account for: %s %s %s",
                type, personData.name, personData.lastName, personData.pesel));

        String uuid = UUID.randomUUID().toString();
        current.adapter.add(
                new AccountImpl(personData, monthIncome, type, exchangeRateService, uuid),
                new Identity(personData.pesel, type.name()));

        return uuid;
    }

    @Override
    public AccountPrx loginStandard(String pesel, String password, Current current) {
        AccountImpl account = (AccountImpl) current.adapter.find(new Identity(pesel, AccountType.STANDARD.name()));
        if (account != null && account.authorize(password)) {
            return AccountPrx.uncheckedCast(current.adapter.createProxy(new Identity(
                    pesel, AccountType.STANDARD.name()))
            );
        }
        return null;
    }

    @Override
    public AccountPrx loginPremium(String pesel, String password, Current current) {
        AccountImpl account = (AccountImpl) current.adapter.find(new Identity(pesel, AccountType.PREMIUM.name()));
        if (account != null && account.authorize(password)) {
            return AccountPrx.uncheckedCast(current.adapter.createProxy(new Identity(
                    pesel, AccountType.PREMIUM.name()))
            );
        }
        return null;
    }

    private AccountType resolveAccountType(MoneyAmount monthIncome) throws NotSupportedCurrencyException {
        if (monthIncome.amount * getExchangeRate(monthIncome.currency) > 10000) {
            return AccountType.PREMIUM;
        } else {
            return AccountType.STANDARD;
        }
    }

    private float getExchangeRate(CurrencyIce currency) throws NotSupportedCurrencyException {
        Map<Currency, Float> exchangeRates = exchangeRateService.getRates();
        if (exchangeRates.containsKey(getCurrencyFor(currency))) {
            return exchangeRates.get(getCurrencyFor(currency));
        } else {
            throw new NotSupportedCurrencyException();
        }
    }
}
