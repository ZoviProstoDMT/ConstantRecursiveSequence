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

    private void reverseCoefficients(List<Integer> coefficients) {
        this.coefficients = new ArrayList<>();
        coefficients.forEach(coefficient -> this.coefficients.add(coefficient * -1));
        this.coefficients.add(1);
    }

    public Polynomial divide(Polynomial polynomial) {
        if (this.coefficients.size() == polynomial.coefficients.size()) {
            return new Polynomial(Collections.singletonList(1), this.subtract(polynomial));
        } else if (!this.isBiggerThan(polynomial)) {
            return new Polynomial(Collections.singletonList(0));
        }
        int shift = this.simplifyCoefficients().getCoefficients().size() - polynomial.simplifyCoefficients().getCoefficients().size();
        Polynomial result = new Polynomial(coefficients.subList(shift, coefficients.size()));
        Polynomial remainder = this.subtract(result.multiply(polynomial));
        result.setRemainder(remainder);
        if (remainder.getCoefficients().size() >= polynomial.getCoefficients().size()) {
            result = result.sum(remainder.divide(polynomial));
        }
        return result;
    }

    public Polynomial getNod(Polynomial polynomial) {
        return this;
    }

    public Polynomial multiply(Polynomial polynomial) {
        Polynomial result = new Polynomial(Collections.singletonList(0));
        for (int i = 0; i < polynomial.getCoefficients().size(); i++) {
            Polynomial tempMultiply = this.multiply(polynomial.getCoefficients().get(i), i);
            result = result.sum(tempMultiply);
        }
        return result.simplifyCoefficients();
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
            return new Polynomial(newCoefficients).simplifyCoefficients();
        }
    }

    public Polynomial subtract(Polynomial polynomial) {
        return this.sum(polynomial.revert()).simplifyCoefficients();
    }

    public Polynomial sum(Polynomial polynomial) {
        List<Integer> coefficients = new ArrayList<>(this.coefficients);
        if (polynomial.getCoefficients().size() > coefficients.size()) {
            for (int i = 0; i <= polynomial.getCoefficients().size() - coefficients.size(); i++) {
                coefficients.add(0);
            }
        }
        for (int i = 0; i < Math.min(coefficients.size(), polynomial.getCoefficients().size()); i++) {
            coefficients.set(i, coefficients.get(i) + polynomial.getCoefficients().get(i));
        }
        return new Polynomial(coefficients).simplifyCoefficients();
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

    private Polynomial simplifyCoefficients() {
        for (int i = this.getCoefficients().size() - 1; i > 0; i--) {
            if (this.getCoefficients().get(i) == 0) {
                this.getCoefficients().remove(i);
            } else {
                return this;
            }
        }
        return this;
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
