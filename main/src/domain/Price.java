package domain;

public class Price {

    private final double amount;
    private final double discount;
    private final Currency currency;

    public Price(final double amount, final double discount, final Currency currency) {
        this.amount = amount;
        this.discount = discount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public double getDiscount() {
        return discount;
    }

    public Currency getCurrency() {
        return currency;
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
