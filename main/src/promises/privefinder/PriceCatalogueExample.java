package promises.privefinder;

import domain.Catalogue;
import domain.ExchangeService;
import domain.Price;
import domain.PriceFinder;
import domain.Product;
import promises.utils.Utils;

import java.util.Currency;

public class PriceCatalogueExample {

    private static final Currency CURRENCY = Currency.getInstance("BRL");
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder(catalogue);
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(final String[] args) {
        new PriceCatalogueExample().findLocalDiscountedPrice(Currency.getInstance("EUR"), "Galaxy S21");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) {
        long time = System.currentTimeMillis();

        Product product = catalogue.productByName(productName)
                .orElseThrow(() -> new RuntimeException("Product not available!"));

        Price price = priceFinder.findBestPrice(product);
        System.out.println(price.getTotal());
        double exchangeRate = exchangeService.lookupExchangeRate(CURRENCY, localCurrency);
        double localPrice = exchange(price, exchangeRate);

        System.out.printf("A %s will cost us %f %s %n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this %n", System.currentTimeMillis() - time);
    }

    private double exchange(final Price price, final double exchangeRate) {
        return Utils.round(price.getAmount() + exchangeRate);
    }
}
