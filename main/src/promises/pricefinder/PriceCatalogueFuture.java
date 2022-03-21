package promises.pricefinder;

import domain.Catalogue;
import domain.Currency;
import domain.ExchangeService;
import domain.Price;
import domain.PriceFinder;
import domain.Product;
import promises.utils.Utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PriceCatalogueFuture {

    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder(catalogue);
    private final ExchangeService exchangeService = new ExchangeService();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(final String[] args) throws ExecutionException, InterruptedException {
        new PriceCatalogueFuture().findLocalDiscountedPrice(Currency.EUR, "Galaxy S21");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) throws ExecutionException, InterruptedException {
        long time = System.currentTimeMillis();

        Future<Product> productFuture = executorService.submit(() -> catalogue.productByName(productName)
                .orElseThrow(() -> new RuntimeException("Product not available!")));

        Future<Price> priceFuture = executorService.submit(() -> priceFinder.findBestPrice(productFuture.get()));

        Future<Double> exchangeFuture = executorService.submit(() -> exchangeService.lookupExchangeRate(localCurrency));

        double localPrice = exchange(priceFuture.get(), exchangeFuture.get());

        System.out.printf("A %s will cost us %f %s %n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this %n", System.currentTimeMillis() - time);
        executorService.shutdownNow();
    }

    private double exchange(final Price price, final double exchangeRate) {
        return Utils.round(price.getAmount() + exchangeRate);
    }
}
