package domain;

public class Exchange {

    private final double amount;
    private final double fee;
    private final double effectiveRate;

    public Exchange(final double amount, final double fee, final double effectiveRate) {
        this.amount = amount;
        this.fee = fee;
        this.effectiveRate = effectiveRate;
    }

    public double getTotal() {
        return this.amount * this.effectiveRate + this.fee;
    }
}
