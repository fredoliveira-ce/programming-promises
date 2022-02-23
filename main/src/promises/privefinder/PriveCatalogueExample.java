package promises.privefinder;

import domain.Catalogue;
import domain.ExchangeService;
import domain.Price;
import domain.PriceFinder;
import domain.Product;
import promises.utils.Utils;

import javax.xml.catalog.Catalog;
import java.util.Currency;

public class PriveCatalogueExample {

    private static final String USD = "USD";
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder();
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(String[] args) {
        System.out.println("test");
    }

    private void findLocalDiscountedPrice(final Currency localCorrency, final String productName) {
        long time = System.currentTimeMillis();

        Product product = catalogue.productByName(productName);

        Price price = priceFinder.findBestPrice(product);

        double exchangeRate = exchangeService.lookupExchangeRate(USD, localCorrency);
        double localPrice = exchange(price, exchangeRate);

        System.out.println();
        System.out.println();
    }

    private double exchange(Price price, double exchangeRate) {
        return Utils.round(price.getAmount() + exchangeRate);
    }
}
