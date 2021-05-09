package entities;

import java.util.List;

public class RecurrentRelation {

    private int modF;
    private int countOfMembers;
    private List<Integer> coefficients;

    public RecurrentRelation(int modF, int countOfMembers, List<Integer> coefficients) {
        this.modF = modF;
        this.countOfMembers = countOfMembers;
        this.coefficients = coefficients;
    }

    public int getModF() {
        return modF;
    }

    public void setModF(int modF) {
        this.modF = modF;
    }

    public int getCountOfMembers() {
        return countOfMembers;
    }

    public void setCountOfMembers(int countOfMembers) {
        this.countOfMembers = countOfMembers;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    @Override
    public String toString() {
        StringBuilder recurrentRelation = new StringBuilder();
        for (int i = 0; i < countOfMembers; i++) {
            if (i != countOfMembers - 1) {
                recurrentRelation.append(coefficients.get(i)).append("C").append(i).append(coefficients.get(i + 1) >= 0 ? " + " : " - ");
            } else {
                recurrentRelation.append(coefficients.get(i)).append("C").append(i);
            }
        }
        return String.valueOf(recurrentRelation);
    }
}
