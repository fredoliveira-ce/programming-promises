package domain;

public class Store {

    private final Partner partner;

    public Store(final Partner partner) {
        this.partner = partner;
    }

    public String getName() {
        return partner.name();
    }

    enum Partner {
        AMAZON, BOL, EBAY, WALMART, JUMBO, HEMA
    }

    @Override
    public String toString() {
        return "Store{" +
                "partner=" + partner +
                '}';
    }
}
