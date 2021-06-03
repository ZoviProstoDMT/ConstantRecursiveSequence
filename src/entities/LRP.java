package entities;

import java.util.ArrayList;
import java.util.List;

public class LRP {

    private final RecurrentRelation recurrentRelation;
    private final List<Integer> initialVector;

    public LRP(RecurrentRelation recurrentRelation, List<Integer> initialVector) {
        this.recurrentRelation = recurrentRelation;
        this.initialVector = initialVector;
    }

    public List<Integer> getInitialVector() {
        return initialVector;
    }

    public List<Integer> getSequence(int numberOfMembers) {
        return calculateSequence(numberOfMembers);
    }

    public Polynomial getCharacteristicPolynomial() {
        return new Polynomial(recurrentRelation);
    }

    public LRP getImpulse() {
        List<Integer> vector = getEmptyList(recurrentRelation.getDegree());
        vector.set(vector.size() - 1, 1);
        return new LRP(recurrentRelation, vector);
    }

    private List<Integer> calculateSequence(int numberOfMembers) {
        List<Integer> resultSequence = new ArrayList<>(initialVector);
        List<Integer> tempU0 = new ArrayList<>(initialVector);
        for (int i = 0; i < numberOfMembers - initialVector.size(); i++) {
            int nextMemberOfSequence = getNextMemberOfSequence(recurrentRelation, tempU0);
            while (nextMemberOfSequence < 0) {
                nextMemberOfSequence += recurrentRelation.getModF();
            }
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
            if (degreeOfMonomial != 0) {
                calculatedSequence = calculatedSequence.subList(degreeOfMonomial, calculatedSequence.size() - 1);
            }
            for (int i = 0; i < calculatedSequence.size(); i++) {
                calculatedSequence.set(i, (calculatedSequence.get(i) * coefficientOfMonomial) % recurrentRelation.getModF());
            }
        }
        return new LRP(recurrentRelation, calculatedSequence.subList(0, recurrentRelation.getDegree()));
    }

    public List<Integer> multiply(Polynomial polynomial, int numberOfMembers) {
        List<Integer> result = getEmptyList(numberOfMembers);
        for (int i = 0; i < polynomial.getCoefficients().size(); i++) {
            LRP lrp = this.multiply(i, polynomial.getCoefficients().get(i));
            result = sum(result, lrp.getSequence(numberOfMembers));
        }
        return result;
    }

    private List<Integer> sum(List<Integer> one, List<Integer> two) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < one.size(); i++) {
            result.add((one.get(i) + two.get(i)) % recurrentRelation.getModF());
        }
        return result;
    }

    public Polynomial getGenerator() {
        int degree = recurrentRelation.getDegree();
        List<Integer> coefficientsForPolynomial = getEmptyList(degree);
        List<Integer> f = recurrentRelation.getCoefficients();
        coefficientsForPolynomial.set(degree - 1, initialVector.get(0));
        for (int i = 1; i <= degree - 1; i++) {
            int result = initialVector.get(i);
            boolean nextStep = false;
            for (int j = degree - 1; !nextStep; ) {
                for (int ii = i - 1; ii >= 0; ii--) {
                    result -= f.get(j) * initialVector.get(ii);
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

    private List<Integer> getEmptyList(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(0);
        }
        return list;
    }

    @Override
    public String toString() {
        return getCharacteristicPolynomial().toString();
    }
}
