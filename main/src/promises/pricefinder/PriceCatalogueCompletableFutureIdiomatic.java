package promises.pricefinder;

import domain.Catalogue;
import domain.ExchangeService;
import domain.Price;
import domain.PriceFinder;
import domain.Product;
import promises.utils.Utils;

import java.util.Currency;
import java.util.concurrent.CompletableFuture;

public class PriceCatalogueCompletableFutureIdiomatic {

    private static final Currency CURRENCY = Currency.getInstance("BRL");
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder(catalogue);
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(final String[] args) {
        new PriceCatalogueCompletableFutureIdiomatic().findLocalDiscountedPrice(Currency.getInstance("EUR"), "Galaxy S21");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) {
        long time = System.currentTimeMillis();

        lookupProduct(productName)
                .thenCompose(this::findBestPrice)
                .thenCombine(lookupExchange(localCurrency), (this::exchange))
                .thenAccept(localPrice -> {
                    System.out.printf("A %s will cost us %f %s %n", productName, localPrice, localCurrency);
                    System.out.printf("It took us %d ms to calculate this %n", System.currentTimeMillis() - time);
                })
                .join();
    }

    private double exchange(final Price price, final double exchangeRate) {
        return Utils.round(price.getAmount() + exchangeRate);
    }

    private CompletableFuture<Product> lookupProduct(String productName) {
        return CompletableFuture.supplyAsync(() -> catalogue.productByName(productName)
                .orElseThrow(() -> new RuntimeException("Product not available!")));
    }

    private CompletableFuture<Price> findBestPrice(Product product) {
        return CompletableFuture.supplyAsync(() -> priceFinder.findBestPrice(product));
    }

    private CompletableFuture<Double> lookupExchange(Currency localCurrency) {
        return CompletableFuture.supplyAsync(() -> exchangeService.lookupExchangeRate(CURRENCY, localCurrency));
    }

}
