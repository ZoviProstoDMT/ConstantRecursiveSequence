package com.kozikov.crs.entity;

import com.haulmont.chile.core.annotations.MetaClass;

import java.io.Serializable;
import java.util.List;

@MetaClass(name = "crs_RecurrentRelation")
public class RecurrentRelation implements Serializable {

    private transient static final long serialVersionUID = 1L;

    private final Integer modF;
    private final Integer degree;
    private final List<Integer> coefficients;

    public RecurrentRelation(List<Integer> coefficients, int modF) {
        this.coefficients = coefficients;
        this.degree = coefficients.size();
        this.modF = modF;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    @Override
    public String toString() {
        StringBuilder recurrentRelation = new StringBuilder();
        for (int i = 0; i < degree; i++) {
            if (coefficients.get(i) != 0) {
                if (i != degree - 1) {
                    if (i == 0) {
                        if (coefficients.get(i) == 1 || coefficients.get(i) == -1) {
                            recurrentRelation.append(coefficients.get(i) == -1 ? "-C" : "C");
                        } else {
                            recurrentRelation.append(coefficients.get(i)).append("C");
                        }
                    } else {
                        if (coefficients.get(i) != 1 && coefficients.get(i) != -1) {
                            recurrentRelation.append(coefficients.get(i)).append("C");
                        } else {
                            recurrentRelation.append("C");
                        }
                    }
                    recurrentRelation.append(i).append(coefficients.get(i + 1) >= 0 ? " + " : " - ");
                } else {
                    if (coefficients.get(i) != 1 && coefficients.get(i) != -1) {
                        recurrentRelation.append(coefficients.get(i)).append("C");
                    } else {
                        recurrentRelation.append("C");
                    }
                    recurrentRelation.append(i).append(" = C").append(i + 1);
                }
            }
        }
        return String.valueOf(recurrentRelation);
    }

    public int getDegree() {
        return degree;
    }

    public int getModF() {
        return modF;
    }
}