package domain;

public class Price {

    private final double amount;
    private final double discount;

    public Price(final double amount, final double discount) {
        this.amount = amount;
        this.discount = discount;
    }

    public double getAmount() {
        return amount;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return this.getAmount() - (this.getAmount() * this.getDiscount());
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount=" + amount +
                ", discount=" + discount +
                '}';
    }
}
