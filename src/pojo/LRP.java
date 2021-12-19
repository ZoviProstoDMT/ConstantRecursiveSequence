package pojo;

import helper.Converter;
import pojo.polynomial.DecompositionOfPolynomial;
import pojo.polynomial.Polynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LRP extends Field implements Converter {

    private final RecurrentRelation recurrentRelation;
    private final List<Integer> initialVector;
    private final Polynomial characteristicPolynomial;
    private DecompositionOfPolynomial decompositionOfCharacteristicPolynomial;
    private Polynomial minimalPolynomial;
    private Polynomial generatorPolynomial;
    private int period = 0;
    private boolean isPeriodLargest;

    public LRP(RecurrentRelation recurrentRelation, List<Integer> initialVector) {
        this.recurrentRelation = recurrentRelation;
        this.initialVector = normalizeCoefficients(initialVector).subList(0, recurrentRelation.getDegree());
        characteristicPolynomial = convertFrom(recurrentRelation);
    }

    public LRP(Polynomial characteristicPolynomial) {
        this.characteristicPolynomial = new Polynomial(characteristicPolynomial.getCoefficients());
        this.initialVector = getImpulseVector();
        recurrentRelation = convertFrom(characteristicPolynomial);
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
        return new LRP(recurrentRelation, getImpulseVector());
    }

    public boolean isPeriodLargest() {
        if (period == 0) {
            return false;
        } else if (Field.fieldInfo.isPrime()) {
            isPeriodLargest = period == (Math.pow(Field.getMod(), characteristicPolynomial.getDegree()) - 1);
        } else if (Field.fieldInfo.isPrimary()) {
            isPeriodLargest = period == (Math.pow(Field.getMod(), characteristicPolynomial.getDegree()) - 1) * (Math.pow(Field.getMod(), Field.fieldInfo.getN() - 1));
        }
        return isPeriodLargest;
    }

    private List<Integer> getImpulseVector() {
        if (characteristicPolynomial.getDegree() == 0) {
            return characteristicPolynomial.getCoefficients();
        }
        List<Integer> vector = getListWithNulls(characteristicPolynomial.getDegree());
        vector.set(vector.size() - 1, 1);
        return vector;
    }

    private List<Integer> calculateSequence(int numberOfMembers) {
        List<Integer> resultSequence = new ArrayList<>(initialVector);
        List<Integer> tempU0 = new ArrayList<>(initialVector);
        for (int i = 0; i < numberOfMembers - initialVector.size(); i++) {
            int nextMemberOfSequence = getNextMemberOfSequence(recurrentRelation, tempU0);
            while (nextMemberOfSequence < 0) {
                nextMemberOfSequence += Field.getMod();
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
            result += member % Field.getMod();
            result %= Field.getMod();
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
                calculatedSequence.set(i, (calculatedSequence.get(i) * coefficientOfMonomial) % Field.getMod());
            }
        }
        return new LRP(recurrentRelation, calculatedSequence.subList(0, recurrentRelation.getDegree()));
    }

    public List<Integer> multiply(Polynomial polynomial, int numberOfMembers) {
        List<Integer> result = getListWithNulls(numberOfMembers);
        for (int i = 0; i < polynomial.getCoefficients().size(); i++) {
            LRP lrp = this.multiply(i, polynomial.getCoefficients().get(i));
            result = sum(result, lrp.getSequence(numberOfMembers));
        }
        return result;
    }

    public DecompositionOfPolynomial getDecompositionOfCharacteristicPolynomial() {
        if (decompositionOfCharacteristicPolynomial == null) {
            decompositionOfCharacteristicPolynomial = getCharacteristicPolynomial().getDecomposeOfPolynomial();
        }
        return decompositionOfCharacteristicPolynomial;
    }

    public int getPeriod() {
        int oldMod = Field.getMod();
        if (period == 0) {
            if (getCharacteristicPolynomial().isDecomposable()) {
                period = 1;
                List<Integer> tempPeriods = new ArrayList<>();
                getCharacteristicPolynomial().getDecomposeOfPolynomial().getDecompositionMap().forEach(
                        (polynomial, degree) -> tempPeriods.add(new LRP(polynomial).getPeriod())
                );
                for (Integer tempPeriod : tempPeriods) {
                    period = LeastCommonMultiple.get(period, tempPeriod);
                }
                return period;
            } else {
                if (Field.fieldInfo.isPrime()) {
                    period = getMinimalPolynomial().getExp();
                } else if (Field.fieldInfo.isPrimary()) {
                    period = getPrimaryPeriod();
                } else {
                    period = getComplexPeriod();
                }
            }
        }
        Field.setMod(oldMod);
        return period;
    }

    private List<Integer> sum(List<Integer> one, List<Integer> two) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < one.size(); i++) {
            result.add((one.get(i) + two.get(i)) % Field.getMod());
        }
        return result;
    }

    public Polynomial getGenerator() {
        if (generatorPolynomial == null) {
            int degree = recurrentRelation.getDegree();
            List<Integer> coefficientsForPolynomial = getListWithNulls(degree);
            List<Integer> f = recurrentRelation.getCoefficients();
            coefficientsForPolynomial.set(degree - 1, initialVector.get(0));
            for (int i = 1; i <= degree - 1; i++) {
                int result = initialVector.get(i);
                boolean nextStep = false;
                for (int j = degree - 1; !nextStep; ) {
                    for (int ii = i - 1; ii >= 0; ii--) {
                        result -= f.get(j) * initialVector.get(ii);
                        result %= Field.getMod();
                        j--;
                        if (ii == 0) {
                            nextStep = true;
                            break;
                        }
                    }
                }
                coefficientsForPolynomial.set(degree - i - 1, result);
            }
            generatorPolynomial = new Polynomial(coefficientsForPolynomial);
        }
        return generatorPolynomial;
    }

    public Polynomial getMinimalPolynomial() {
        if (minimalPolynomial == null) {
            minimalPolynomial = getCharacteristicPolynomial()
                    .divide(GreatestCommonDivisor.get(getGenerator(), getCharacteristicPolynomial()));
        }
        return minimalPolynomial;
    }

    public List<List<LRP>> getCyclicClasses() {
        List<List<LRP>> cyclicClasses = new ArrayList<>();
        List<List<Integer>> initialVectors = generateInitialVectors(initialVector.size());
        int period = getPeriod();
        for (List<Integer> initialVector : initialVectors) {
            LRP lrp = new LRP(recurrentRelation, initialVector);
            boolean notEqualsLRP = true;
            for (List<LRP> cyclicClass : cyclicClasses) {
                if (!cyclicClass.isEmpty() && cyclicClass.get(0).equals(lrp, period)) {
                    cyclicClass.add(lrp);
                    notEqualsLRP = false;
                    break;
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

    public int getI(int a) {
        return getSequence(getPeriod()).indexOf(a) + 1;
    }

    private int getPrimaryPeriod() {
        Polynomial fx = getCharacteristicPolynomial();
        int p = Field.fieldInfo.getP();
        int n = Field.fieldInfo.getN();
        System.out.println("Дано кольцо Z(" + p + "^" + n + ")");
        System.out.println("F(x) = " + fx + " над Z" + Field.getMod());
        int oldMod = Field.getMod();
        Field.setMod(p);
        Polynomial reducedFx = new LRP(fx).getCharacteristicPolynomial();
        int e = reducedFx.getExp();
        System.out.println("Находим редукцию /F(x), взяв первоначальный F(x), приведя коэффициенты по модулю 'p'");
        System.out.print("Редукция /F(x) = " + reducedFx + " над Z" + p);
        System.out.println(" - " + (reducedFx.isDecomposable() ? "Приводим" : "Неприводим"));
        System.out.println("exp /F(x) = " + e);
        System.out.println("ЛРП редукции имеет " + (e == (Math.pow(Field.getMod(), this.characteristicPolynomial.getDegree()) - 1) ?
                "максимальный период" : "не максимальный период"));
        Field.setMod(oldMod);
        Polynomial highestPolynomial = Polynomial.getMonomialMinusOne(e);
        System.out.println();
        System.out.println("Проверим, достигается ли равенство T(F(x)) = T(/F(x)) * p^n-1");
        System.out.println();
        System.out.println("Разделим " + highestPolynomial + " на F(x) с остатком по модулю " + oldMod);
        Polynomial divide = highestPolynomial.divide(fx);
        System.out.println(highestPolynomial + " = (" + fx + ") * (" + divide + ") + (" + divide.getRemainder() + ")");
        Polynomial ux;
        if (divide.getRemainder().getDegree() == 0) {
            ux = new Polynomial(Collections.singletonList(divide.getRemainder().getLowestCoefficient() / p));
        } else {
            List<Integer> coefficients = new ArrayList<>(divide.getRemainder().getCoefficients());
            for (int i = 0; i < coefficients.size(); i++) {
                coefficients.set(i, coefficients.get(i) / p);
            }
            ux = new Polynomial(coefficients);
        }
        System.out.println(highestPolynomial + " = ( ... ) * ( ... ) + " + p + "(" + ux + ")");
        System.out.println("u(x) = " + ux);
        System.out.println();

        int tfx = e * (int) Math.pow(p, n - 1);
        if ((p > 2) || ((p == 2) && (n == 2))) {
            System.out.println("Сложилась ситуация, когда p > 2 или p = 2 и n = 2");
            System.out.println("Убедимся, что u(x) не равняется 0");
            if (!ux.isNull()) {
                System.out.println("u(x) = " + ux + " не равно 0");
                System.out.println("Тогда T(F(x)) = T(/F(x)) * p^n-1 = " + e + " * " + (int) Math.pow(p, n - 1) + " = " + tfx);
                return tfx;
            } else {
                System.out.println("u(x) = " + ux);
                System.out.println("Равенство не достигается");
            }
        } else if ((p == 2) && (n > 2)) {
            System.out.println("Сложилась ситуация, когда p = 2 и n > 2");
            System.out.println("Убедимся, что u(x)^2 + u(x) не сравнимо с 0 по модулю (/F(x))");
            Polynomial ux2 = ux.multiply(ux);
            Polynomial sumUx = ux2.sum(ux);
            System.out.println("(" + ux2 + ") + (" + ux + ") = " + sumUx);
            Polynomial divideRes = sumUx.divide(reducedFx);
            System.out.println(sumUx + " cравнимо с " + divideRes.getRemainder() + " по модулю (" + reducedFx + ")");
            if (!divideRes.getRemainder().isNull()) {
                System.out.println("Тогда T(F(x)) = T(/F(x)) * p^n-1 = " + e + " * " + (int) Math.pow(p, n - 1) + " = " + tfx);
                return tfx;
            } else {
                System.out.println("Равенство не достигается");
            }
        }
        if (ux.isNull()) {
            System.out.println("Тогда новый T(x) = exp /F(x) * p = " + (e * p));
            return e * p;
        } else {
            int newExp = ux.getExp();
            System.out.println("exp " + ux + " = " + newExp);
            e *= newExp;
            System.out.println("Тогда новый T(x) = " + e);
            return e;
        }
    }

    private int getComplexPeriod() {
        System.out.print("Z[x] не является простым. Рассмотрим композицию: ");
        List<Integer> primeMembers = Field.getPrimeMembers();
        System.out.println("Z" + Field.getMod() + " = " +
                primeMembers.toString().replace("[", "Z").replace("]", "")
                        .replace(" ", "Z").replace(",", " + "));
        System.out.println();
        List<Integer> compositionOfPeriods = new ArrayList<>();
        for (int i = 0; i < primeMembers.size(); i++) {
            Field.setMod(primeMembers.get(i));
            LRP newLrp = new LRP(new RecurrentRelation(recurrentRelation.getCoefficients()), getInitialVector());
            System.out.println("F" + i + "(x) = " + newLrp.getCharacteristicPolynomial() + " (mod " + Field.getMod() + ")");
            System.out.println("T" + i + "(x) = " + newLrp.getPeriod());
            System.out.println();
            compositionOfPeriods.add(newLrp.getPeriod());
        }
        System.out.println("Значит пероид будет равен = " + compositionOfPeriods);
        return LeastCommonMultiple.get(compositionOfPeriods.get(0), compositionOfPeriods.get(1));
    }

    public CyclicType getCyclicType() {
        return new CyclicType(this);
    }

    public boolean equals(LRP lrp, int period) {
        if (this == lrp) {
            return true;
        }
        if (initialVector.equals(lrp.getInitialVector())) {
            return true;
        }
        if (initialVector.stream().noneMatch(integer -> integer != 0) &&
                lrp.getInitialVector().stream().anyMatch(integer -> integer != 0)) {
            return false;
        }
        if (initialVector.stream().anyMatch(integer -> integer != 0) &&
                lrp.getInitialVector().stream().noneMatch(integer -> integer != 0)) {
            return false;
        }
        if (this.hashCode() != lrp.hashCode()) {
            return false;
        }
        LRP temp = new LRP(this.recurrentRelation, initialVector);
        String thisSeq = temp.getSequence(initialVector.size()).toString()
                .replace("[", "").replace("]", "");
        String objSeq = lrp.getSequence(period + initialVector.size()).toString();
        return objSeq.contains(thisSeq);
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Integer integer : getSequence(getCharacteristicPolynomial().getExp())) {
            hashCode += integer;
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
            if (initialVector.equals(((LRP) obj).getInitialVector())) {
                return true;
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
            int period;
            if (this.period != 0) {
                period = this.period;
            } else if (((LRP) obj).period != 0) {
                period = ((LRP) obj).period;
            } else {
                period = getPeriod();
            }
            LRP temp = new LRP(this.recurrentRelation, initialVector);
            String thisSeq = temp.getSequence(initialVector.size()).toString()
                    .replace("[", "").replace("]", "");
            String objSeq = ((LRP) obj).getSequence(period + initialVector.size()).toString();
            return objSeq.contains(thisSeq);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getSequence(15).toString();
    }
}
