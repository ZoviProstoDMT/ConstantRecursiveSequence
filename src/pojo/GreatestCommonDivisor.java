package pojo;

import pojo.polynomial.Polynomial;

import java.util.Collections;

public class GreatestCommonDivisor {

    public static Polynomial get(Polynomial a, Polynomial b) {
        if (a.getDegree() < b.getDegree()) {
            Polynomial temp = a;
            a = b;
            b = temp;
        }
        Polynomial remainder = a.divide(b).getRemainder();
        if (remainder.getDegree() == 0 && remainder.getCoefficients().get(0) == 0) {
            return b;
        } else {
            remainder = get(b, remainder);
        }
        return remainder.getDegree() == 0 ? new Polynomial(Collections.singletonList(1)) : remainder;
    }

    public static int get(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (a == 0) return b;
        if (b == 0) return a;
        int factorsOfTwoInA = Integer.numberOfTrailingZeros(a),
                factorsOfTwoInB = Integer.numberOfTrailingZeros(b),
                commonFactorsOfTwo = Math.min(factorsOfTwoInA, factorsOfTwoInB);
        a >>= factorsOfTwoInA;
        b >>= factorsOfTwoInB;
        while (a != b) {
            if (a > b) {
                a = (a - b);
                a >>= Integer.numberOfTrailingZeros(a);
            } else {
                b = (b - a);
                b >>= Integer.numberOfTrailingZeros(b);
            }
        }
        return a << commonFactorsOfTwo;
    }
}
