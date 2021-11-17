package pojos;

import java.util.*;

public class Polynomial extends Field {

    private List<Integer> coefficients;
    private Polynomial remainder;

    public Polynomial(RecurrentRelation recurrentRelation) {
        reverseCoefficients(recurrentRelation.getCoefficients());
    }

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = convertToField(coefficients);
    }

    public Polynomial(List<Integer> coefficients, Polynomial remainder) {
        this.coefficients = convertToField(coefficients);
        this.remainder = remainder;
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

    private void reverseCoefficients(List<Integer> coefficients) {
        this.coefficients = new ArrayList<>();
        coefficients.forEach(coefficient -> this.coefficients.add(coefficient * -1));
        this.coefficients.add(1);
    }

    public Polynomial divide(Polynomial polynomial) {
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
        int tempDegree = this.trimCoefficients().getDegree() - polynomial.trimCoefficients().getDegree();
        int tempCoefficient = (this.getHighestCoefficient() * getReverseNumber(polynomial.getHighestCoefficient()) % mod);
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
            List<Integer> newCoefficients = getEmptyList(coefficients.size() + degree);
            for (int i = 0; i < coefficients.size(); i++) {
                newCoefficients.set(i + degree, (coefficients.get(i) * coefficient) % mod);
            }
            return new Polynomial(newCoefficients).trimCoefficients();
        }
    }

    public Polynomial subtract(Polynomial polynomial) {
        return this.sum(polynomial.revert()).trimCoefficients().convertToField();
    }

    public Polynomial sum(Polynomial polynomial) {
        if (this.coefficients.size() > polynomial.getCoefficients().size()) {
            for (int i = 0; i < polynomial.getCoefficients().size(); i++) {
                this.coefficients.set(i, (polynomial.getCoefficients().get(i) + this.coefficients.get(i)) % mod);
            }
            return this;
        } else {
            for (int i = 0; i < this.coefficients.size(); i++) {
                polynomial.getCoefficients().set(i, (polynomial.getCoefficients().get(i) + this.coefficients.get(i)) % mod);
            }
            return polynomial;
        }
    }

    public Polynomial revert() {
        List<Integer> coefficients = new ArrayList<>(this.coefficients);
        for (int i = 0; i < coefficients.size(); i++) {
            coefficients.set(i, coefficients.get(i) * -1);
        }
        return new Polynomial(coefficients, remainder);
    }

    private List<Integer> getEmptyList(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(0);
        }
        return list;
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
        for (int i = this.getDegree(); i > 0; i--) {
            if (this.getCoefficients().get(i) == 0) {
                this.getCoefficients().remove(i);
            } else {
                return this;
            }
        }
        return this;
    }

    private int getReverseNumber(int number) {
        while (number < 0) {
            number += mod;
        }
        for (int i = 0; i < mod; i++) {
            if ((number * i) % mod == 1) {
                return i;
            }
        }
        return 999999999;
    }

    private List<Integer> convertToField(List<Integer> coefficients) {
        ArrayList<Integer> res = new ArrayList<>();
        coefficients.forEach(c -> {
            while (c < 0) {
                c += mod;
            }
            res.add(c % mod);
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

    public int getExp() {
        int degree = coefficients.size() - 1;
        int startDegree = coefficients.size() - 1;
        Polynomial polynomial = new Polynomial(coefficients.subList(0, coefficients.size() - 1)).revert().trimCoefficients();
        Polynomial startPolynomial = new Polynomial(polynomial.getCoefficients());
        while (true) {
            degree++;
            Polynomial tempPolynomial = polynomial.multiply(new Polynomial(Arrays.asList(0, 1)));
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
            if (polynomial.getCoefficients().size() == 1 && polynomial.getCoefficients().get(0) == 1) {
                break;
            }
        }
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
            result.append("X");
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
                            result.append("X");
                        } else {
                            result.append(coefficients.get(i)).append("X");
                        }
                        result.append(i == 1 ? "" : "^" + i);
                    }
                } else {
                    result.append(" - ");
                    if (i == 0) {
                        result.append(coefficients.get(i) * -1);
                    } else {
                        if (coefficients.get(i) == 1 || coefficients.get(i) == -1) {
                            result.append("X");
                        } else {
                            result.append(coefficients.get(i) * -1).append("X");
                        }
                        result.append(i == 1 ? "" : "^" + i);
                    }
                }
            }
        }
        return String.valueOf(result).isEmpty() ? "0" : String.valueOf(result);
    }
}
