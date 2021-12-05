package pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRP extends Field {

    private final RecurrentRelation recurrentRelation;
    private final List<Integer> initialVector;
    private final Polynomial characteristicPolynomial;
    private Polynomial minimalPolynomial;
    private Polynomial generatorPolynomial;
    private int period = 0;

    public LRP(RecurrentRelation recurrentRelation, List<Integer> initialVector) {
        this.recurrentRelation = recurrentRelation;
        this.initialVector = normalizeCoefficients(initialVector).subList(0, recurrentRelation.getDegree());
        characteristicPolynomial = new Polynomial(recurrentRelation);
    }

    public List<Integer> getInitialVector() {
        return initialVector;
    }

    public List<Integer> getSequence(int numberOfMembers) {
        return calculateSequence(numberOfMembers);
    }

    public Polynomial getCharacteristicPolynomial() {
        return characteristicPolynomial;
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
                nextMemberOfSequence += Field.mod;
            }
            tempU0 = makeALeftShiftForSequence(tempU0, nextMemberOfSequence);
            resultSequence.add(nextMemberOfSequence);
        }
        return resultSequence;
    }

    private int getNextMemberOfSequence(RecurrentRelation recurrentRelation, List<Integer> u0) {
        int result = 0;
        if (u0.size() > recurrentRelation.getCoefficients().size()) {
            u0 = u0.subList(u0.size() - recurrentRelation.getCoefficients().size(), u0.size() - 1);
        }
        for (int i = 0; i < u0.size(); i++) {
            int member = recurrentRelation.getCoefficients().get(i) * u0.get(i);
            result += member % mod;
            result %= mod;
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
                calculatedSequence.set(i, (calculatedSequence.get(i) * coefficientOfMonomial) % mod);
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

    public int getPeriod() {
        if (period == 0) {
            if (getCharacteristicPolynomial().isDecomposable()) {
                period = getMinimalPolynomial().getExp();
            } else {
                period = (int) (Math.pow(Field.mod, getCharacteristicPolynomial().getDegree()) - 1);
            }
        }
        return period;
    }

    private List<Integer> sum(List<Integer> one, List<Integer> two) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < one.size(); i++) {
            result.add((one.get(i) + two.get(i)) % mod);
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
                    result %= mod;
                    j--;
                    if (ii == 0) {
                        nextStep = true;
                        break;
                    }
                }
            }
            coefficientsForPolynomial.set(degree - i - 1, result);
        }
        if (generatorPolynomial == null) {
            generatorPolynomial = new Polynomial(coefficientsForPolynomial);
        }
        return generatorPolynomial;
    }

    public Polynomial getMinimalPolynomial() {
        if (minimalPolynomial == null) {
            minimalPolynomial = getCharacteristicPolynomial()
                    .divide(new GreatestCommonDivisor(getGenerator(), getCharacteristicPolynomial()).getGcdResult());
        }
        return minimalPolynomial;
    }

    public List<List<LRP>> getCyclicClasses() {
        List<List<LRP>> cyclicClasses = new ArrayList<>();
        List<List<Integer>> initialVectors = generateInitialVectors(initialVector.size());
        for (List<Integer> initialVector : initialVectors) {
            LRP lrp = new LRP(recurrentRelation, initialVector);
            boolean notEqualsLRP = true;
            for (List<LRP> cyclicClass : cyclicClasses) {
                if (!cyclicClass.isEmpty() && cyclicClass.get(0).equals(lrp)) {
                    cyclicClass.add(lrp);
                    notEqualsLRP = false;
                }
            }
            if (notEqualsLRP) {
                List<LRP> list = new ArrayList<>();
                list.add(lrp);
                cyclicClasses.add(list);
            }
        }
        return cyclicClasses;
    }

    public String getCyclicType(List<List<LRP>> cyclicClasses) {
        Map<Integer, Integer> cyclicClassesCount = new HashMap<>();
        StringBuilder cyclicType = new StringBuilder();
        for (List<LRP> cyclicClass : cyclicClasses) {
            int size = cyclicClass.size();
            if (cyclicClassesCount.containsKey(size)) {
                cyclicClassesCount.put(size, cyclicClassesCount.get(size) + 1);
            } else {
                cyclicClassesCount.put(size, 1);
            }
        }
        for (Map.Entry<Integer, Integer> classCount : cyclicClassesCount.entrySet()) {
            if (classCount.getKey() == 1) {
                cyclicType.append(String.format("%sy + ", classCount.getValue()));
            } else {
                cyclicType.append(String.format("%sy^%s + ", classCount.getValue(), classCount.getKey()));
            }
        }
        return String.valueOf(new StringBuilder(cyclicType.reverse().substring(3)).reverse());
    }

    private List<Integer> getEmptyList(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(0);
        }
        return list;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Integer integer : getSequence(getCharacteristicPolynomial().getExp())) {
            hashCode += 31 * integer;
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LRP) {
            if (this == obj) {
                return true;
            }
            if (!this.recurrentRelation.equals(((LRP) obj).recurrentRelation)) {
                return false;
            }
            if (initialVector.stream().noneMatch(integer -> integer != 0) &&
                    ((LRP) obj).getInitialVector().stream().anyMatch(integer -> integer != 0)) {
                return false;
            }
            if (initialVector.stream().anyMatch(integer -> integer != 0) &&
                    ((LRP) obj).getInitialVector().stream().noneMatch(integer -> integer != 0)) {
                return false;
            }
            if (this.hashCode() != obj.hashCode()) {
                return false;
            }
            int period = getPeriod();
            LRP temp = new LRP(this.recurrentRelation, initialVector);
            for (int i = 1; i < period; i++) {
                temp = temp.multiply(1, 1);
                if (temp.getSequence(period).equals(((LRP) obj).getSequence(period))) {
                    return true;
                }
            }
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getSequence(20).toString();
    }
}
