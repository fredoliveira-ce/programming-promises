package domain;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class PriceFinder {

    private final Catalogue catalogue;
    private final List<Offer> offers;

    public PriceFinder(final Catalogue catalogue) {
        this.catalogue = catalogue;
        this.offers = List.of(
                new Offer(getProduct(1), new Store(Store.Partner.AMAZON), new Price(100, 0, Currency.EUR)),
                new Offer(getProduct(2),  new Store(Store.Partner.AMAZON), new Price(600, 0.03, Currency.EUR)),
                new Offer(getProduct(2),  new Store(Store.Partner.BOL), new Price(630, 0.02, Currency.EUR)),
                new Offer(getProduct(2),  new Store(Store.Partner.EBAY), new Price(700, 0.05, Currency.EUR)),
                new Offer(getProduct(3),  new Store(Store.Partner.AMAZON), new Price(100, 0, Currency.EUR)),
                new Offer(getProduct(4),  new Store(Store.Partner.AMAZON), new Price(100, 0, Currency.EUR)),
                new Offer(getProduct(5),  new Store(Store.Partner.AMAZON), new Price(100, 0, Currency.EUR)),
                new Offer(getProduct(6),  new Store(Store.Partner.AMAZON), new Price(100, 0, Currency.EUR))
        );
    }

    public Price findBestPrice(final Product product) {
        return offers.stream()
                .filter(offer -> product.getId().equals(offer.getProduct().getId()))
                .map(Offer::getPrice)
                .min(Comparator.comparingDouble(Price::getTotal))
                .orElseThrow(() -> new RuntimeException("Product is no longer available!"));
    }

    private Product getProduct(final Integer id) {
        return Objects.requireNonNull(this.catalogue).productById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
    }

}
