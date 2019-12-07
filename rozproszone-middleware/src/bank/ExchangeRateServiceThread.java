package bank;

import io.grpc.StatusRuntimeException;
import sr.grpc.gen.Currency;
import sr.grpc.gen.CurrencyList;
import sr.grpc.gen.CurrencyServiceGrpc.CurrencyServiceBlockingStub;
import sr.grpc.gen.ExchangeRate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExchangeRateServiceThread extends Thread {
    private final CurrencyServiceBlockingStub currencyServiceBlockingStub;
    private final Logger logger;
    private List<Currency> currencies;
    private final Map<Currency, Float> rates = new HashMap<>();

    public ExchangeRateServiceThread(CurrencyServiceBlockingStub currencyServiceBlockingStub, Logger logger,
                                     List<Currency> currencies) {
        this.currencyServiceBlockingStub = currencyServiceBlockingStub;
        this.logger = logger;
        this.currencies = currencies;
    }

    @Override
    public void run() {
        getRate();
    }

    private void getRate() {
        CurrencyList currencyList = CurrencyList.newBuilder()
                .addAllChosenCurrencies(currencies)
                .build();

        Iterator<ExchangeRate> rates;
        try {
            rates = currencyServiceBlockingStub.getExchangeRates(currencyList);
            while (rates.hasNext()) {
                ExchangeRate rate = rates.next();
                synchronized (this.rates) {
                    updateRatesMap(rate);
                }
            }
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }
    }

    private void updateRatesMap(ExchangeRate rate) {
        rates.put(rate.getCurrency(), rate.getRate());
        if (rate.getCurrency() != Currency.PLN) {
            logger.info("Rate updated: " + rate.getCurrency() + " " + rate.getRate());
        }
    }

    public Map<Currency, Float> getRates() {
        synchronized (rates) {
            return rates;
        }
    }
}
