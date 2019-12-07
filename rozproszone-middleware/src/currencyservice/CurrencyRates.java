package currencyservice;

import sr.grpc.gen.Currency;

import java.util.HashMap;
import java.util.Map;

public class CurrencyRates {
    private static CurrencyRates instance = null;
    private Map<Currency, Float> exchangeRates = new HashMap<>();

    private CurrencyRates() {
        exchangeRates.put(Currency.PLN, 1f);
        exchangeRates.put(Currency.EUR, 4.27f);
        exchangeRates.put(Currency.USD, 3.56f);
        exchangeRates.put(Currency.GBP, 4.86f);
    }

    public static CurrencyRates getInstance() {
        if (instance == null) {
            instance = new CurrencyRates();
        }
        return instance;
    }

    public Map<Currency, Float> getExchangeRates() {
        return exchangeRates;
    }
}
