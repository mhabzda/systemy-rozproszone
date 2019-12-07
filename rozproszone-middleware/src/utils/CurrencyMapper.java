package utils;

import Bank.CurrencyIce;
import sr.grpc.gen.Currency;

public class CurrencyMapper {
    public static Currency getCurrencyFor(CurrencyIce currencyIce) {
        for (Currency currency : Currency.values()) {
            if (currency.getNumber() == currencyIce.value()) {
                return currency;
            }
        }
        return Currency.PLN;
    }

    public static Currency getCurrencyFor(String currencyString) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equals(currencyString)) {
                return currency;
            }
        }
        return Currency.PLN;
    }
}
