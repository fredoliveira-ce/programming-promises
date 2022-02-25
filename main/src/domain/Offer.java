package domain;

public class Offer {

    private final Product product;
    private final Store store;
    private final Price price;

    public Offer(final Product product, final Store store, final Price price) {
        this.product = product;
        this.store = store;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public Price getPrice() {
        return price;
    }

    public Store getStore() {
        return store;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "product=" + product +
                ", store=" + store +
                ", price=" + price +
                '}';
    }
}
