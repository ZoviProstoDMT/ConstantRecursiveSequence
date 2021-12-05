package pojo;

import java.util.Collections;

public class GreatestCommonDivisor {

    private final Polynomial gcd;

    public GreatestCommonDivisor(Polynomial one, Polynomial two) {
        gcd = calculateGCD(one, two);
    }

    public Polynomial getGcdResult() {
        return gcd;
    }

    private Polynomial calculateGCD(Polynomial one, Polynomial two) {
        if (one.getDegree() < two.getDegree()) {
            Polynomial temp = one;
            one = two;
            two = temp;
        }
        Polynomial remainder = one.divide(two).getRemainder();
        if (remainder.getDegree() == 0 && remainder.getCoefficients().get(0) == 0) {
            return two;
        } else {
            remainder = calculateGCD(two, remainder);
        }
        return remainder.getDegree() == 0 ? new Polynomial(Collections.singletonList(1)) : remainder;
    }

    @Override
    public String toString() {
        return String.valueOf(gcd);
    }
}
