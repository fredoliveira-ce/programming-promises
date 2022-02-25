package domain;

public enum Company {
    WISE(0.031),
    PAYPAL(0.038);

    private final double fee;

    Company(double fee) {
        this.fee = fee;
    }

    public double getFee() {
        return this.fee;
    }
}
