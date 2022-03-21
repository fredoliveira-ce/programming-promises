package promises.pricefinder;

import domain.Catalogue;
import domain.Currency;
import domain.ExchangeService;
import domain.Price;
import domain.PriceFinder;
import domain.Product;

public class PriceCatalogueExample {

    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder(catalogue);
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(final String[] args) {
        new PriceCatalogueExample().findLocalDiscountedPrice(Currency.EUR, "Galaxy S21");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) {
        long time = System.currentTimeMillis();

        final Product product = catalogue.productByName(productName)
                .orElseThrow(() -> new RuntimeException("Product not available!"));
        final Price price = priceFinder.findBestPrice(product);

        double exchangeRate = exchangeService.lookupExchangeRate(localCurrency);

        double localPrice = exchange(price, exchangeRate);

        System.out.printf("A %s will cost us %f %s %n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this %n", System.currentTimeMillis() - time);
    }

    private double exchange(final Price price, final double exchangeRate) {
        return price.getAmount() + exchangeRate;
    }
}
