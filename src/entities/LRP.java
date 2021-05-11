package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LRP {

    private RecurrentRelation recurrentRelation;
    private List<Integer> u0;
    private List<Integer> tempU0;
    private List<Integer> sequence;

    public LRP(RecurrentRelation recurrentRelation, List<Integer> u0) {
        this.recurrentRelation = recurrentRelation;
        this.u0 = u0;
    }

    public List<Integer> getU0() {
        return u0;
    }

    public void setU0(List<Integer> u0) {
        this.u0 = u0;
    }

    public RecurrentRelation getRecurrentRelation() {
        return recurrentRelation;
    }

    public void setRecurrentRelation(RecurrentRelation recurrentRelation) {
        this.recurrentRelation = recurrentRelation;
    }

    public List<Integer> getTempU0() {
        return tempU0;
    }

    public void setTempU0(List<Integer> tempU0) {
        this.tempU0 = tempU0;
    }

    public List<Integer> getSequence(int numberOfMembers) {
        sequence = calculateSequence(numberOfMembers);
        return sequence;
    }

    public void setSequence(List<Integer> sequence) {
        this.sequence = sequence;
    }

    private List<Integer> calculateSequence(int numberOfMembers) {
        List<Integer> resultSequence = new ArrayList<>(u0);
        tempU0 = new ArrayList<>(u0);
        for (int i = 0; i < numberOfMembers - u0.size(); i++) {
            int nextMemberOfSequence = getNextMemberOfSequence(recurrentRelation, tempU0);
            tempU0 = makeALeftShiftForSequence(tempU0, nextMemberOfSequence);
            resultSequence.add(nextMemberOfSequence);
        }
        return resultSequence;
    }

    private int getNextMemberOfSequence(RecurrentRelation recurrentRelation, List<Integer> u0) {
        int result = 0;
        int modF = recurrentRelation.getModF();
        if (u0.size() > recurrentRelation.getCoefficients().size()) {
            u0 = u0.subList(u0.size() - recurrentRelation.getCoefficients().size(), u0.size() - 1);
        }
        for (int i = 0; i < u0.size(); i++) {
            int member = recurrentRelation.getCoefficients().get(i) * u0.get(i);
            result += member % modF;
            result %= modF;
        }
        return result;
    }

    private List<Integer> makeALeftShiftForSequence(List<Integer> tempU0, int nextMember) {
        tempU0.add(nextMember);
        return tempU0.subList(1, tempU0.size());
    }

    public LRP multiply(int degreeOfMonomial, int coefficientOfMonomial) {
        List<Integer> calculatedSequence = calculateSequence(degreeOfMonomial + 21);
        if (coefficientOfMonomial != 0) {
            calculatedSequence = calculatedSequence.subList(degreeOfMonomial, calculatedSequence.size() - 1);
            for (int i = 0; i < calculatedSequence.size(); i++) {
                calculatedSequence.set(i, (calculatedSequence.get(i) * coefficientOfMonomial) % recurrentRelation.getModF());
            }
        }
        return new LRP(recurrentRelation, calculatedSequence.subList(0, recurrentRelation.getDegree()));
    }

    public LRP multiply(Polynomial polynomial) {
        LRP result = this;
        for (int i = 0; i < polynomial.getCoefficients().size(); i++) {
            result = result.multiply(i, polynomial.getCoefficients().get(i));
        }
        return result;
    }

    public Polynomial getGenerator() {
        ArrayList<Integer> coefficientsForPolynomial = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));
        int degree = recurrentRelation.getDegree();
        Polynomial polynomial = new Polynomial(recurrentRelation);
        List<Integer> f = polynomial.getCoefficients();
        coefficientsForPolynomial.set(degree - 1, u0.get(0));
        for (int i = 1; i <= degree - 1; i++) {
            int result = u0.get(i);
            boolean nextStep = false;
            for (int j = degree - 1; !nextStep; ) {
                for (int ii = i - 1; ii >= 0; ii--) {
                    result -= f.get(j) * u0.get(ii);
                    result %= recurrentRelation.getModF();
                    j--;
                    if (ii == 0) {
                        nextStep = true;
                        break;
                    }
                }
            }
            coefficientsForPolynomial.set(degree - i - 1, result);
        }
        return new Polynomial(coefficientsForPolynomial);
    }

    public List<Integer> getSubSequence(int numberOfMember) {
        if (sequence.size() > numberOfMember + recurrentRelation.getDegree()) {
            return sequence.subList(numberOfMember, numberOfMember + recurrentRelation.getDegree());
        } else {
            List<Integer> sequence = calculateSequence(numberOfMember + recurrentRelation.getDegree());
            return sequence.subList(numberOfMember, numberOfMember + recurrentRelation.getDegree());
        }
    }

    public List<List<Integer>> getStateMatrix() {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> firstState = getSubSequence(0);
        result.add(firstState);
        int i = 1;
        while (true) {
            List<Integer> nextState = getSubSequence(i);
            if (nextState.equals(firstState)) {
                break;
            }
            result.add(nextState);
            i++;
        }
        return result;
    }
}
