package entities;

import java.util.ArrayList;
import java.util.List;

public class Polynomial {

    private int modF;
    private RecurrentRelation recurrentRelation;
    private List<Integer> coefficients;

    public Polynomial(RecurrentRelation recurrentRelation) {
        this.recurrentRelation = recurrentRelation;
        reverseCoefficients(recurrentRelation.getCoefficients());
    }

    public Polynomial(int modF, List<Integer> coefficients) {
        this.modF = modF;
        this.coefficients = coefficients;
    }

    public int getModF() {
        return modF;
    }

    public void setModF(int modF) {
        this.modF = modF;
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
        setCoefficients(new ArrayList<>());
        coefficients.forEach(coefficient -> this.coefficients.add(coefficient * -1));
        this.coefficients.add(1);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = coefficients.size() - 1; i >= 0; i--) {
            if (coefficients.get(i) != 0) {
                if (coefficients.get(i) != 1 && coefficients.get(i) != -1) {
                    result.append(coefficients.get(i)).append("X^").append(i);
                } else {
                    result.append("X^").append(i);
                }
                if (i != 1) {
                    result.append(" + ");
                }
            }
        }
        return String.valueOf(result);
    }
}
