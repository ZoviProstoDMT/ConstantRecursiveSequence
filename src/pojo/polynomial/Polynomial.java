package pojo.polynomial;

import helper.Converter;
import org.jetbrains.annotations.Nullable;
import pojo.Field;
import pojo.GreatestCommonDivisor;
import pojo.LeastCommonMultiple;

import java.util.*;

public class Polynomial extends Field implements Converter {

    private final List<Integer> coefficients;
    DecompositionOfPolynomial decomposeOfPolynomial = new DecompositionOfPolynomial();
    private Polynomial remainder;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = trimCoefficients(convertToField(coefficients));
    }

    public Polynomial(List<Integer> coefficients, Polynomial remainder) {
        this.coefficients = convertToField(coefficients);
        this.remainder = remainder;
    }

    public Polynomial(Integer... coefficients) {
        this.coefficients = new ArrayList<>(Arrays.asList(coefficients));
    }

    public Polynomial(List<Integer> coefficients, boolean isConvertedToField) {
        if (isConvertedToField) {
            this.coefficients = trimCoefficients(convertToField(coefficients));
        } else {
            this.coefficients = trimCoefficients(coefficients);
        }
    }

    public static Polynomial getMonomialMinusOne(int degree) {
        Polynomial monomial = getMonomial(degree);
        monomial.getCoefficients().set(0, -1);
        return monomial;
    }

    public static Polynomial getMonomial(int degree) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < degree; i++) {
            list.add(0);
        }
        list.add(1);
        return new Polynomial(list);
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public Polynomial getRemainder() {
        return remainder;
    }

    public void setRemainder(Polynomial remainder) {
        this.remainder = remainder;
    }

    public int getDegree() {
        return coefficients.size() - 1;
    }

    public int getHighestCoefficient() {
        return coefficients.get(coefficients.size() - 1);
    }

    public int getLowestCoefficient() {
        return coefficients.get(0);
    }

    public boolean isDecomposable() {
        return getDecomposeOfPolynomial().isDecomposable();
    }

    public DecompositionOfPolynomial getDecomposeOfPolynomial() {
        if (decomposeOfPolynomial == null) {
            decomposeOfPolynomial = new DecompositionOfPolynomial();
            Polynomial minimalDecomposeMember = getMinimalDecomposeMember();
            if (minimalDecomposeMember == null) {
                decomposeOfPolynomial.put(this, 1);
            } else {
                decomposeOfPolynomial = this.decompose(minimalDecomposeMember);
            }
        }
        return decomposeOfPolynomial;
    }

    @Nullable
    public Polynomial getMinimalDecomposeMember() {
        Polynomial decomposition = null;
        Set<Polynomial> allPolynomials = Field.getAllPolynomials(getDegree());
        for (Polynomial p1 : allPolynomials) {
            if (p1.getDegree() == 0 || p1.getHighestCoefficient() != 1) {
                continue;
            }
            for (Polynomial p2 : allPolynomials) {
                if (p2.getDegree() == 0 || p2.getHighestCoefficient() != 1) {
                    continue;
                }
                Polynomial multiplyResult = p1.multiply(p2);
                if (multiplyResult.equals(this)) {
                    Polynomial temp = (p1.getDegree() < p2.getDegree() ? p1 : p2);
                    if (decomposition == null) {
                        decomposition = temp;
                    } else {
                        decomposition = decomposition.getDegree() <= temp.getDegree() ? decomposition : temp;
                    }
                }
            }
        }
        return decomposition;
    }

    public Polynomial divide(Polynomial polynomial) {
        if (this.lessThan(polynomial)) {
            return getNullPolynomial();
        }
        if (polynomial.getDegree() == 0) {
            this.setRemainder(new Polynomial(Collections.singletonList(0)));
            switch (polynomial.getCoefficients().get(0)) {
                case 0:
                    return null;
                case 1:
                    return this;
                case -1:
                    return this.revert();
                default:
                    Polynomial result = this.multiply(getReverseNumber(polynomial.getCoefficients().get(0)), 0);
                    result.setRemainder(new Polynomial(Collections.singletonList(0)));
                    return result;
            }
        }
        if (this.getDegree() == 0) {
            return getNullPolynomial();
        }
        if (polynomial.getHighestCoefficient() > 1 || polynomial.getHighestCoefficient() < -1) {
            polynomial = polynomial.normalize();
        }
        //todo добавить деление на моном
        if (polynomial.getDegree() == 1 && polynomial.getCoefficients().equals(Arrays.asList(0, 1))) {
            List<Integer> tempCoefficients = new ArrayList<>(this.getCoefficients());
            tempCoefficients.remove(0);
            Polynomial result = new Polynomial(tempCoefficients);
            result.setRemainder(new Polynomial(Collections.singletonList(0)));
            return result;
        }
        int tempDegree = this.trimCoefficients().getDegree() - polynomial.trimCoefficients().getDegree();
        int tempCoefficient = (this.getHighestCoefficient() * getReverseNumber(polynomial.getHighestCoefficient()) % Field.getMod());
        Polynomial result = getMonomial(tempDegree, tempCoefficient);
        Polynomial remainder = this.subtract(result.multiply(polynomial));
        result.setRemainder(remainder);
        if (result.getRemainder().getDegree() >= polynomial.getDegree()) {
            Polynomial tempDivide = result.getRemainder().divide(polynomial);
            result = result.sum(tempDivide);
            result.setRemainder(tempDivide.getRemainder());
        }
        return result;
    }

    public Polynomial multiply(Polynomial polynomial) {
        Polynomial result = new Polynomial(Collections.singletonList(0));
        for (int i = 0; i < polynomial.getCoefficients().size(); i++) {
            Polynomial tempMultiply = this.multiply(polynomial.getCoefficients().get(i), i);
            result = result.sum(tempMultiply);
        }
        return result.trimCoefficients();
    }

    private Polynomial multiply(int coefficient, int degree) {
        if (coefficient == 0) {
            return new Polynomial(Collections.singletonList(0));
        } else if (coefficient == 1 && degree == 0) {
            return this;
        } else if (coefficient == -1 && degree == 0) {
            return this.revert();
        } else {
            List<Integer> coefficients = new ArrayList<>(this.coefficients);
            List<Integer> newCoefficients = getListWithNulls(coefficients.size() + degree);
            for (int i = 0; i < coefficients.size(); i++) {
                newCoefficients.set(i + degree, (coefficients.get(i) * coefficient) % Field.getMod());
            }
            return new Polynomial(newCoefficients).trimCoefficients();
        }
    }

    public Polynomial subtract(Polynomial polynomial) {
        return this.sum(polynomial.revert()).trimCoefficients().convertToField();
    }

    public Polynomial sum(Polynomial b) {
        int aSize = getCoefficients().size();
        int bSize = b.getCoefficients().size();
        boolean aLessThanB = aSize <= bSize;
        List<Integer> resultCoefficients = new ArrayList<>(aLessThanB ? b.getCoefficients() : getCoefficients());
        for (int i = 0; i < resultCoefficients.size(); i++) {
            int ai = aSize > i ? getCoefficients().get(i) : 0;
            int bi = bSize > i ? b.getCoefficients().get(i) : 0;
            resultCoefficients.set(i, (ai + bi) % Field.getMod());
        }
        return new Polynomial(resultCoefficients);
    }

    public Polynomial revert() {
        List<Integer> coefficients = new ArrayList<>(this.coefficients);
        for (int i = 0; i < coefficients.size(); i++) {
            coefficients.set(i, coefficients.get(i) * -1);
        }
        return new Polynomial(coefficients, remainder);
    }

    public boolean isNull() {
        return getCoefficients().stream().noneMatch(coefficient -> coefficient != 0);
    }

    private Polynomial getMonomial(int degree, int coefficient) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < degree; i++) {
            list.add(0);
        }
        list.add(coefficient);
        return new Polynomial(list);
    }

    private Polynomial trimCoefficients() {
        for (int i = getDegree(); i > 0; i--) {
            if (getCoefficients().get(i) == 0) {
                getCoefficients().remove(i);
            } else {
                return this;
            }
        }
        return this;
    }

    private List<Integer> trimCoefficients(List<Integer> coefficients) {
        if (coefficients.size() == 1) {
            return coefficients;
        }
        if (coefficients.stream().noneMatch(integer -> integer != 0)) {
            return getListWithNulls(1);
        }
        for (int i = coefficients.size() - 1; i >= 0; i--) {
            if (coefficients.get(i) == 0) {
                coefficients.remove(i);
            } else {
                break;
            }
        }
        return coefficients;
    }

    private int getReverseNumber(int number) {
        while (number < 0) {
            number += Field.getMod();
        }
        for (int i = 0; i < Field.getMod(); i++) {
            if ((number * i) % Field.getMod() == 1) {
                return i;
            }
        }
        throw new RuntimeException("There are no reverse number for " + number + " in Z" + Field.getMod());
    }

    private List<Integer> convertToField(List<Integer> coefficients) {
        ArrayList<Integer> res = new ArrayList<>();
        coefficients.forEach(c -> {
            while (c < 0) {
                c += Field.getMod();
            }
            res.add(c % Field.getMod());
        });
        return res;
    }

    private Polynomial convertToField() {
        return new Polynomial(convertToField(this.coefficients));
    }

    public Polynomial normalize() {
        return this.multiply(new Polynomial(Collections.singletonList(getReverseNumber(this.getHighestCoefficient()))));
    }

    private Polynomial getNullPolynomial() {
        Polynomial temp = new Polynomial(Collections.singletonList(0));
        temp.setRemainder(new Polynomial(Collections.singletonList(0)));
        return temp;
    }

    public boolean isReversible() {
        return GreatestCommonDivisor.get(getCoefficients().get(0), Field.getMod()) == 1;
    }

    public DecompositionOfPolynomial decompose(Polynomial minimalPolynomial) {
        Map<Polynomial, Integer> decompositionOfCharacteristicPolynomial = new HashMap<>();
        if (minimalPolynomial.getDegree() != getDegree() || minimalPolynomial.getCoefficients().size() == 1) {
            Polynomial temp = this;
            while (true) {
                Polynomial divideRes = temp.divide(minimalPolynomial);
                if (divideRes.getRemainder().isNull()) {
                    decompositionOfCharacteristicPolynomial.compute(minimalPolynomial, (polynomial, degree) -> degree == null ? 1 : degree + 1);
                    if (divideRes.getDegree() == minimalPolynomial.getDegree()) {
                        decompositionOfCharacteristicPolynomial.compute(divideRes, (polynomial, degree) -> degree == null ? 1 : degree + 1);
                        break;
                    }
                    temp = divideRes;
                } else {
                    decompositionOfCharacteristicPolynomial.put(temp, 1);
                    break;
                }
            }
        } else {
            decompositionOfCharacteristicPolynomial.put(this, 1);
        }
        return new DecompositionOfPolynomial(decompositionOfCharacteristicPolynomial);
    }

    public boolean lessThan(Polynomial p) {
        if (getDegree() == p.getDegree()) {
            List<Integer> coefficientsA = getCoefficients();
            List<Integer> coefficientsB = p.getCoefficients();
            for (int i = coefficientsA.size() - 1; i >= 0; i--) {
                if (!Objects.equals(coefficientsA.get(i), coefficientsB.get(i))) {
                    return coefficientsA.get(i) < coefficientsB.get(i);
                }
            }
        } else {
            return getDegree() <= p.getDegree();
        }
        return false;
    }

    public int getExp() {
        if (!isReversible()) {
            if (getDegree() == 1) {
                return 2;
            }
            int exp = 1;
            for (Map.Entry<Polynomial, Integer> member : getDecomposeOfPolynomial().getDecompositionMap().entrySet()) {
                Polynomial polynomial = member.getKey();
                int tempExp = polynomial.getExp();
                exp = LeastCommonMultiple.get(exp, tempExp);
            }
            return exp;
        }
        int degree = coefficients.size() - 1;
        int startDegree = coefficients.size() - 1;
        Polynomial polynomial = new Polynomial(coefficients.subList(0, coefficients.size() - 1)).revert().trimCoefficients();
        Polynomial startPolynomial = new Polynomial(polynomial.getCoefficients());
        do {
            degree++;
            Polynomial tempPolynomial = polynomial.multiply(1, 1);
            int olderDegree = tempPolynomial.getCoefficients().size() - 1;
            int olderCoefficient = tempPolynomial.getCoefficients().get(olderDegree);
            if (olderDegree != startDegree) {
                polynomial = tempPolynomial;
                continue;
            }
            tempPolynomial.getCoefficients().remove(olderDegree);
            tempPolynomial.trimCoefficients();
            Polynomial multipliedToOlderCoefficient = new Polynomial(startPolynomial.getCoefficients()).multiply(olderCoefficient, 0);
            polynomial = tempPolynomial.sum(multipliedToOlderCoefficient);
            polynomial.trimCoefficients();
        } while (polynomial.getCoefficients().size() != 1 || polynomial.getCoefficients().get(0) != 1);
        return degree;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Polynomial) {
            return this.coefficients.equals(((Polynomial) obj).coefficients);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (coefficients.size() == 1) {
            return String.valueOf(coefficients.get(0));
        } else if (coefficients.get(coefficients.size() - 1) != 0) {
            if (coefficients.get(coefficients.size() - 1) != 1 && coefficients.get(coefficients.size() - 1) != -1) {
                result.append(coefficients.get(coefficients.size() - 1));
            } else {
                result.append(coefficients.get(coefficients.size() - 1) == -1 ? "-" : "");
            }
            result.append("x");
            result.append(coefficients.size() - 1 == 1 ? "" : "^" + (coefficients.size() - 1));
        }
        for (int i = coefficients.size() - 2; i >= 0; i--) {
            if (coefficients.get(i) != 0) {
                if (coefficients.get(i) > 0) {
                    result.append(" + ");
                    if (i == 0) {
                        result.append(coefficients.get(i));
                    } else {
                        if (coefficients.get(i) == 1 || coefficients.get(i) == -1) {
                            result.append("x");
                        } else {
                            result.append(coefficients.get(i)).append("x");
                        }
                        result.append(i == 1 ? "" : "^" + i);
                    }
                } else {
                    result.append(" - ");
                    if (i == 0) {
                        result.append(coefficients.get(i) * -1);
                    } else {
                        if (coefficients.get(i) == 1 || coefficients.get(i) == -1) {
                            result.append("x");
                        } else {
                            result.append(coefficients.get(i) * -1).append("x");
                        }
                        result.append(i == 1 ? "" : "^" + i);
                    }
                }
            }
        }
        return String.valueOf(result).isEmpty() ? "0" : String.valueOf(result);
    }
}
