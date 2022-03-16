package promises.pricefinder;

import domain.Catalogue;
import domain.Currency;
import domain.Price;
import domain.PriceFinder;
import domain.Product;
import promises.utils.Utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static domain.Currency.USD;

public class PriceCatalogueFaulty {

    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder(catalogue);
    private final AsyncExchangeServiceFaulty exchangeService = new AsyncExchangeServiceFaulty();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(final String[] args) {
        new PriceCatalogueFaulty().findLocalDiscountedPrice(Currency.EUR, "Galaxy S21");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) {
        long time = System.currentTimeMillis();

        lookupProduct(productName)
                .thenComposeAsync(this::findBestPrice, executorService)
                .thenCombineAsync(exchangeService.lookupExchangeRateAsync(USD, localCurrency), this::exchange)
                .thenApply(localPrice -> {
                    String output = String.format("A %s will cost us %f %s %n", productName, localPrice,
                            localCurrency);
                    output += String.format("It took us %d ms to calculate this %n", System.currentTimeMillis() - time);
                    return output;
                })
                .exceptionally(ex -> "Sorry try again next time: " + ex.getCause().getMessage())
                .thenAccept(System.out::println);

    }

    private CompletableFuture<Double> exchange(final Price price, final double exchangeRate) {
        return CompletableFuture.supplyAsync(() -> Utils.round(price.getAmount() + exchangeRate));
    }

    private CompletableFuture<Product> lookupProduct(String productName) {
        return CompletableFuture.supplyAsync(() -> catalogue.productByName(productName)
                .orElseThrow(() -> new RuntimeException("Product not available!")));
    }

    private CompletableFuture<Price> findBestPrice(Product product) {
        return CompletableFuture.supplyAsync(() -> priceFinder.findBestPrice(product));
    }

}
