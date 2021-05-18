package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polynomial {

    private List<Integer> coefficients;

    public Polynomial(RecurrentRelation recurrentRelation) {
        reverseCoefficients(recurrentRelation.getCoefficients());
    }

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    private void reverseCoefficients(List<Integer> coefficients) {
        this.coefficients = new ArrayList<>();
        coefficients.forEach(coefficient -> this.coefficients.add(coefficient * -1));
        this.coefficients.add(1);
    }

    private Polynomial divide(Polynomial polynomial) {
        return this;
    }

    public Polynomial divide(int coefficient, int degree) {
        if (coefficient == 0) {
            throw new ArithmeticException();
        } else if (coefficient == 1 && degree == 1) {
            return this;
        } else {
            List<Integer> coefficients = new ArrayList<>(this.coefficients);
            List<Integer> newCoefficients = getEmptyList(coefficients.size() - degree);
            newCoefficients.set(newCoefficients.size() - 1, coefficients.get(coefficients.size() - 1) / coefficient);
            return new Polynomial(newCoefficients);
        }
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
        return result;
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
            return new Polynomial(newCoefficients);
        }
    }

    public Polynomial subtract(Polynomial polynomial) {
        return this.sum(polynomial.revert());
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
        return new Polynomial(coefficients);
    }

    public Polynomial revert() {
        List<Integer> coefficients = new ArrayList<>(this.coefficients);
        for (int i = 0; i < coefficients.size(); i++) {
            coefficients.set(i, coefficients.get(i) * -1);
        }
        return new Polynomial(coefficients);
    }

    private boolean isBiggerThan(Polynomial polynomial) {
        return this.getCoefficients().size() > polynomial.getCoefficients().size();
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
        StringBuilder result = new StringBuilder();
        if (coefficients.get(coefficients.size() - 1) != 0) {
            if (coefficients.get(coefficients.size() - 1) != 1 && coefficients.get(coefficients.size() - 1) != -1) {
                result.append(coefficients.get(coefficients.size() - 1));
            } else {
                result.append(coefficients.get(coefficients.size() - 1) == -1 ? "-" : "");
            }
            if ((coefficients.size() - 1) != 0) {
                result.append("X");
            }
            result.append(coefficients.size() - 1 == 1 ? "" : (coefficients.size() - 1) != 0 ? "^" + (coefficients.size() - 1) : "");
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
