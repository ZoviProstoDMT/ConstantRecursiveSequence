package entities;

import java.util.ArrayList;
import java.util.List;

public class Polynomial {

    private int modF;
    private RecurrentRelation recurrentRelation;
    private List<Integer> coefficients;

    public Polynomial(RecurrentRelation recurrentRelation) {
        this.recurrentRelation = recurrentRelation;
        coefficients = new ArrayList<>(recurrentRelation.getCoefficients());
        coefficients.add(1);
    }

    public Polynomial(int modF, List<Integer> coefficients) {
        this.modF = modF;
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
}
