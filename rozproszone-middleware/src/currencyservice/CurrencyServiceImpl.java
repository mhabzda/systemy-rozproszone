package currencyservice;

import io.grpc.stub.StreamObserver;
import sr.grpc.gen.Currency;
import sr.grpc.gen.CurrencyList;
import sr.grpc.gen.CurrencyServiceGrpc.CurrencyServiceImplBase;
import sr.grpc.gen.ExchangeRate;

public class CurrencyServiceImpl extends CurrencyServiceImplBase {
    private final CurrencyRates currencyRates = CurrencyRates.getInstance();

    @Override
    public void getExchangeRates(CurrencyList request, StreamObserver<ExchangeRate> responseObserver) {
        System.out.println("getExchangeRates");
        try {
            while (true) {
                for (Currency currency : request.getChosenCurrenciesList()) {
                    ExchangeRate exchangeRate = ExchangeRate
                            .newBuilder()
                            .setCurrency(currency)
                            .setRate(currencyRates.getExchangeRates().get(currency))
                            .build();
                    responseObserver.onNext(exchangeRate);
                }
                Thread.sleep(2000);
                modifyRates();
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseObserver.onCompleted();
    }

    private void modifyRates() {
        for (Currency currency : currencyRates.getExchangeRates().keySet()) {
            if (currency != Currency.PLN) {
                float rate;
                if (Math.random() > 0.5) {
                    rate = currencyRates.getExchangeRates().get(currency) + 0.05f;
                } else {
                    rate = currencyRates.getExchangeRates().get(currency) - 0.05f;
                }
                currencyRates.getExchangeRates().put(currency, rate);
            }
        }
    }
}
