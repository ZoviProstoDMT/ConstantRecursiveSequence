package entities;

public class GreatestCommonDivisor {

    private final Polynomial one;
    private final Polynomial two;
    private final Polynomial gcd;

    public GreatestCommonDivisor(Polynomial one, Polynomial two) {
        this.one = one;
        this.two = two;
        gcd = calculateGCD(one, two);
    }

    public Polynomial getOne() {
        return one;
    }

    public Polynomial getTwo() {
        return two;
    }

    public Polynomial getGcdResult() {
        return gcd;
    }

    private Polynomial calculateGCD(Polynomial one, Polynomial two) {
        Polynomial remainder = one.divide(two).getRemainder();
        if (remainder.getDegree() == 0 && remainder.getCoefficients().get(0) == 0) {
            return two;
        } else {
            calculateGCD(two, remainder);
        }
        return two;
    }

    @Override
    public String toString() {
        return String.valueOf(gcd);
    }
}
