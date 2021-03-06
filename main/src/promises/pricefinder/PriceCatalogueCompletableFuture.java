package promises.pricefinder;

import domain.Catalogue;
import domain.Currency;
import domain.ExchangeService;
import domain.Price;
import domain.PriceFinder;
import domain.Product;
import promises.utils.Utils;

import java.util.concurrent.CompletableFuture;

public class PriceCatalogueCompletableFuture {

    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder(catalogue);
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(final String[] args) {
        new PriceCatalogueCompletableFuture().findLocalDiscountedPrice(Currency.EUR, "Galaxy S21");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) {
        long time = System.currentTimeMillis();

        CompletableFuture<Product> productCF = CompletableFuture.supplyAsync(() -> catalogue.productByName(productName)
                .orElseThrow(() -> new RuntimeException("Product not available!")));

        CompletableFuture<Price> priceCF = CompletableFuture.supplyAsync(() -> priceFinder.findBestPrice(productCF.join()));

        CompletableFuture<Double> exchangeCF = CompletableFuture.supplyAsync(() -> exchangeService.lookupExchangeRate(localCurrency));

        double localPrice = exchange(priceCF.join(), exchangeCF.join());

        System.out.printf("A %s will cost us %f %s %n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this %n", System.currentTimeMillis() - time);
    }

    private double exchange(final Price price, final double exchangeRate) {
        return Utils.round(price.getAmount() + exchangeRate);
    }
}
