package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polynomial {

    private List<Integer> coefficients;
    private Polynomial remainder;

    public Polynomial(RecurrentRelation recurrentRelation) {
        reverseCoefficients(recurrentRelation.getCoefficients());
    }

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial(List<Integer> coefficients, Polynomial remainder) {
        this.coefficients = coefficients;
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
        return coefficients.size();
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
        int tempDegree = this.trimCoefficients().getDegree() - polynomial.trimCoefficients().getDegree();
        int tempCoefficient = this.getHighestCoefficient() / polynomial.getHighestCoefficient();
        Polynomial result = getMonomial(tempDegree, tempCoefficient);
        Polynomial remainder = this.subtract(result.multiply(polynomial));
        result.setRemainder(remainder);
        if (result.getRemainder().getDegree() >= polynomial.getDegree()) {
            result = result.sum(result.getRemainder().divide(polynomial));
            result.setRemainder(this.subtract(result.multiply(polynomial)));
        }
        return result;
    }

    public Polynomial getGSD(Polynomial polynomial) {
        return this;
    }

    public Polynomial multiply(Polynomial polynomial) {
        Polynomial result = new Polynomial(Collections.singletonList(0));
        for (int i = 0; i < polynomial.getDegree(); i++) {
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
        } else {
            List<Integer> coefficients = new ArrayList<>(this.coefficients);
            List<Integer> newCoefficients = getEmptyList(coefficients.size() + degree);
            for (int i = 0; i < coefficients.size(); i++) {
                newCoefficients.set(i + degree, coefficients.get(i) * coefficient);
            }
            return new Polynomial(newCoefficients).trimCoefficients();
        }
    }

    public Polynomial subtract(Polynomial polynomial) {
        return this.sum(polynomial.revert()).trimCoefficients();
    }

    public Polynomial sum(Polynomial polynomial) {
        List<Integer> coefficients = new ArrayList<>(this.coefficients);
        if (polynomial.getDegree() > coefficients.size()) {
            for (int i = 0; i <= polynomial.getDegree() - coefficients.size(); i++) {
                coefficients.add(0);
            }
        }
        for (int i = 0; i < Math.min(coefficients.size(), polynomial.getDegree()); i++) {
            coefficients.set(i, coefficients.get(i) + polynomial.getCoefficients().get(i));
        }
        return new Polynomial(coefficients).trimCoefficients();
    }

    public Polynomial revert() {
        List<Integer> coefficients = new ArrayList<>(this.coefficients);
        for (int i = 0; i < coefficients.size(); i++) {
            coefficients.set(i, coefficients.get(i) * -1);
        }
        return new Polynomial(coefficients);
    }

    public boolean isBiggerThan(Polynomial polynomial) {
        List<Integer> coefficientsA = this.getCoefficients();
        List<Integer> coefficientsB = polynomial.coefficients;
        if (coefficientsA.size() == coefficientsB.size()) {
            for (int i = coefficientsA.size() - 1; i >= 0; i--) {
                if (!coefficientsA.get(i).equals(coefficientsB.get(i))) {
                    return coefficientsA.get(i) > coefficientsB.get(i);
                }
            }
        }
        return coefficientsA.size() > coefficientsB.size();
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
        for (int i = this.getDegree() - 1; i > 0; i--) {
            if (this.getCoefficients().get(i) == 0) {
                this.getCoefficients().remove(i);
            } else {
                return this;
            }
        }
        return this;
    }

    private boolean isHighestCoefficientsSameSign(Polynomial a, Polynomial b) {
        int coeffA = a.getCoefficients().get(a.getDegree() - 1);
        int coeffB = b.getCoefficients().get(b.getDegree() - 1);
        if (coeffA > 0 && coeffB > 0) {
            return true;
        } else {
            return coeffA < 0 && coeffB < 0;
        }
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
