package entities;

import java.util.List;

public class RecurrentRelation {

    private final int modF;
    private final int degree;
    private final List<Integer> coefficients;

    public RecurrentRelation(int modF, int degree, List<Integer> coefficients) {
        this.modF = modF;
        this.degree = degree;
        this.coefficients = coefficients;
    }

    public int getModF() {
        return modF;
    }

    public int getDegree() {
        return degree;
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
                    if (coefficients.get(i) != 1 && coefficients.get(i) != -1) {
                        recurrentRelation.append(coefficients.get(i)).append("C");
                    } else {
                        recurrentRelation.append("C");
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
}
