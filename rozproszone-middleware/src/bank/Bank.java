package bank;

import bankservice.BankServiceImpl;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sr.grpc.gen.Currency;
import sr.grpc.gen.CurrencyServiceGrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static utils.CurrencyMapper.getCurrencyFor;

public class Bank {
    public static final int PORT = 50051;
    private static final Logger logger = Logger.getLogger(Bank.class.getName());

    private final ManagedChannel channel;
    private final ExchangeRateServiceThread exchangeRateServiceThread;

    public Bank(String host, int port, List<Currency> currencies) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid needing certificates.
                .usePlaintext(true)
                .build();

        exchangeRateServiceThread = new ExchangeRateServiceThread(
                CurrencyServiceGrpc.newBlockingStub(channel), logger, currencies);
        exchangeRateServiceThread.start();
    }

    public static void main(String[] args) throws Exception {
        List<Currency> currencies = askForCurrencies();

        Bank bank = new Bank("localhost", PORT, currencies);

        bank.startBankService();
        bank.joinBankExchangeRateThread();
        bank.shutdownCurrencyServerChannel();
    }

    public void shutdownCurrencyServerChannel() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void joinBankExchangeRateThread() {
        try {
            exchangeRateServiceThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startBankService() {
        Communicator communicator = null;

        try {
            communicator = Util.initialize(new String[0], "config.server");
            ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");

            BankServiceImpl bankServant = new BankServiceImpl(exchangeRateServiceThread);

            adapter.add(bankServant, new Identity("bank1", "bank"));
            adapter.activate();

            logger.info("Now waiting for events from clients...");

            communicator.waitForShutdown();

        } catch (Exception e) {
            System.err.println(e);
        }
        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    private static List<Currency> askForCurrencies() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Choose currencies in which bank is interested in: (PLN is native)\n" +
                "EUR, USD, GBP");
        String line = in.readLine();
        String[] currenciesTab = line.split(" ");

        System.out.println(Arrays.toString(currenciesTab));

        List<Currency> currencies = new ArrayList<>();
        currencies.add(Currency.PLN);
        for (String currency : currenciesTab) {
            currencies.add(getCurrencyFor(currency));
        }
        return currencies;
    }
}
