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
            if (coefficients.get(i) != 0) {
                if (i != countOfMembers - 1) {
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
