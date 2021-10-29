package com.kozikov.crs.entity;

import com.haulmont.chile.core.annotations.MetaClass;

import java.util.Collections;

@MetaClass(name = "crs_GreatestCommonDivisor")
public class GreatestCommonDivisor {

    private final Integer modF;
    private final Polynomial gcd;

    public GreatestCommonDivisor(Polynomial one, Polynomial two) {
        this.modF = one.getModF();
        gcd = calculateGCD(one, two);
    }

    public Polynomial getGcdResult() {
        return gcd;
    }

    private Polynomial calculateGCD(Polynomial one, Polynomial two) {
        Polynomial remainder = one.divide(two).getRemainder();
        if (remainder.getDegree() == 0 && remainder.getCoefficients().get(0) == 0) {
            return two;
        } else {
            remainder = calculateGCD(two, remainder);
        }
        return remainder;
    }

    private Polynomial getMonomial() {
        return new Polynomial(Collections.singletonList(1), modF);
    }

    @Override
    public String toString() {
        return String.valueOf(gcd);
    }

    public int getModF() {
        return modF;
    }
}