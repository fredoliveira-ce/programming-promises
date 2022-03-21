package domain;


public enum Company {
    WISE(0.031, Currency.EUR),
    PAYPAL(0.038, Currency.EUR);

    private final double fee;
    private final Currency currency;

    Company(double fee, Currency currency) {
        this.fee = fee;
        this.currency = currency;
    }

    public double getFee() {
        return this.fee;
    }

    public Currency getCurrency() {
        return currency;
    }
}
