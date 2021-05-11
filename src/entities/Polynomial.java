package entities;

import java.util.ArrayList;
import java.util.List;

public class Polynomial {

    private RecurrentRelation recurrentRelation;
    private List<Integer> coefficients;

    public Polynomial(RecurrentRelation recurrentRelation) {
        this.recurrentRelation = recurrentRelation;
        reverseCoefficients(recurrentRelation.getCoefficients());
    }

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public RecurrentRelation getRecurrentRelation() {
        return recurrentRelation;
    }

    public void setRecurrentRelation(RecurrentRelation recurrentRelation) {
        this.recurrentRelation = recurrentRelation;
    }

    private void reverseCoefficients(List<Integer> coefficients) {
        this.coefficients = new ArrayList<>();
        coefficients.forEach(coefficient -> this.coefficients.add(coefficient * -1));
        this.coefficients.add(1);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (coefficients.get(coefficients.size() - 1) == 1 || coefficients.get(coefficients.size() - 1) == -1) {
            result.append("X^").append(coefficients.size() - 1);
        } else {
            result.append(coefficients.get(coefficients.size() - 1)).append("X^").append(coefficients.size() - 1);
        }
        for (int i = coefficients.size() - 2; i >= 0; i--) {
            if (coefficients.get(i) != 0) {
                if (coefficients.get(i) > 0) {
                    result.append(" + ");
                    if (i == 0) {
                        result.append(coefficients.get(i));
                    } else {
                        if (coefficients.get(i) == 1 || coefficients.get(i) == -1) {
                            result.append("X^").append(i);
                        } else {
                            result.append(coefficients.get(i)).append("X^").append(i);
                        }
                    }
                } else {
                    result.append(" - ");
                    if (i == 0) {
                        result.append(coefficients.get(i));
                    } else {
                        if (coefficients.get(i) == 1 || coefficients.get(i) == -1) {
                            result.append("X^").append(i);
                        } else {
                            result.append(coefficients.get(i) * -1).append("X^").append(i);
                        }
                    }
                }
            }
        }
        return String.valueOf(result);
    }
}
