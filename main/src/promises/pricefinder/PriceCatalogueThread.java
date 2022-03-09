package promises.pricefinder;

import domain.Catalogue;
import domain.ExchangeService;
import domain.Price;
import domain.PriceFinder;
import domain.Product;
import promises.utils.Utils;

import java.util.Currency;

public class PriceCatalogueThread {

    private static final Currency CURRENCY = Currency.getInstance("BRL");
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder(catalogue);
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(final String[] args) throws InterruptedException {
        new PriceCatalogueThread().findLocalDiscountedPrice(Currency.getInstance("EUR"), "Galaxy S21");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) throws InterruptedException {
        long time = System.currentTimeMillis();

        PriceRunnable runnablePrice = new PriceRunnable(productName);
        ExchangeRunnable runnableExchange = new ExchangeRunnable(localCurrency);

        Thread priceThread = new Thread(runnablePrice);
        Thread exchangeThread = new Thread(runnableExchange);

        priceThread.start();
        exchangeThread.start();

        priceThread.join();
        exchangeThread.join();

        double localPrice = exchange(runnablePrice.getPrice(), runnableExchange.getExchangeRate());

        System.out.printf("A %s will cost us %f %s %n", productName, localPrice, localCurrency);
        System.out.printf("It took us %d ms to calculate this %n", System.currentTimeMillis() - time);
    }

    private double exchange(final Price price, final double exchangeRate) {
        return Utils.round(price.getAmount() + exchangeRate);
    }

    private class PriceRunnable implements Runnable {

        private final String productName;
        private Price price;

        public PriceRunnable(final String productName) {
            this.productName = productName;
        }

        @Override
        public void run() {
            final Product product = catalogue.productByName(productName)
                    .orElseThrow(() -> new RuntimeException("Product not available!"));

            this.price = priceFinder.findBestPrice(product);
        }

        public Price getPrice() {
            return price;
        }
    }

    private class ExchangeRunnable implements Runnable {
        private final Currency localCurrency;
        private double exchangeRate;

        public ExchangeRunnable(final Currency localCurrency) {
            this.localCurrency = localCurrency;
        }

        @Override
        public void run() {
            this.exchangeRate = exchangeService.lookupExchangeRate(CURRENCY, localCurrency);
        }

        public double getExchangeRate() {
            return exchangeRate;
        }
    }
}
