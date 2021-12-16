package pojo.polynomial;

import java.util.HashMap;
import java.util.Map;

public class DecompositionOfPolynomial {
    Map<Polynomial, Integer> decomposeOfPolynomial;

    public DecompositionOfPolynomial() {
        decomposeOfPolynomial = new HashMap<>();
    }

    public DecompositionOfPolynomial(Map<Polynomial, Integer> decomposeOfPolynomial) {
        this.decomposeOfPolynomial = decomposeOfPolynomial;
    }

    public Map<Polynomial, Integer> getDecompositionMap() {
        return decomposeOfPolynomial;
    }

    public int size() {
        return decomposeOfPolynomial.size();
    }

    public void put(Polynomial polynomial, int degree) {
        if (decomposeOfPolynomial == null) {
            decomposeOfPolynomial = new HashMap<>();
        }
        decomposeOfPolynomial.put(polynomial, degree);
    }

    public boolean isDecomposable() {
        if (decomposeOfPolynomial == null || decomposeOfPolynomial.size() == 0) {
            return false;
        } else if (decomposeOfPolynomial.size() == 1) {
            return decomposeOfPolynomial.values().stream().findFirst().orElse(0) > 1;
        }
        return size() > 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        decomposeOfPolynomial.forEach((polynomial, degree) -> {
            if (degree > 1) {
                sb.append("(").append(polynomial).append(")^").append(degree);
            } else {
                sb.append("(").append(polynomial).append(")");
            }
        });
        return new String(sb);
    }
}