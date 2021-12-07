package pojo;

import java.util.ArrayList;
import java.util.List;

public class Field {
    public static int mod;

    public static List<Integer> normalizeCoefficients(List<Integer> coefficients) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < coefficients.size(); i++) {
            int member = coefficients.get(i) % mod;
            if (coefficients.get(i) < 0) {
                result.add(i, member + mod);
            } else {
                result.add(i, member);
            }
        }
        return result;
    }

    public static List<List<Integer>> generateInitialVectors(int vectorDimension) {
        double vectorsQuantity = Math.pow(mod, vectorDimension);
        List<List<Integer>> vectors = new ArrayList<>();
        List<Integer> vector = getEmptyVector(vectorDimension);
        vectors.add(new ArrayList<>(vector));

        while (vectors.size() != vectorsQuantity) {
            vector = iterateVector(vector);
            if (!vectors.contains(vector)) {
                vectors.add(new ArrayList<>(vector));
            }
        }
        return vectors;
    }

    private static List<Integer> getEmptyVector(int vectorDimension) {
        List<Integer> vector = new ArrayList<>();
        for (int j = 0; j < vectorDimension; j++) {
            vector.add(0);
        }
        return vector;
    }

    private static List<Integer> iterateVector(List<Integer> initialVector) {
        List<Integer> vector = new ArrayList<>(initialVector);
        for (int i = vector.size() - 1; i >= 0; i--) {
            int iteratedInt = (vector.get(i) + 1) % mod;
            vector.set(i, iteratedInt);
            if (iteratedInt != 0) {
                break;
            }
        }
        return vector;
    }

    public static boolean isModPrime() {
        double halfRange = Math.sqrt(mod);
        for (int i = 2; i <= halfRange; i++) {
            if (mod % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static List<Polynomial> getAllDecomposablePolynomials(int degree) {
        List<Polynomial> decomposablePolynomials = new ArrayList<>();
        List<List<Integer>> initialVectors = generateInitialVectors(degree);
        for (List<Integer> initialVector1 : initialVectors) {
            Polynomial p1 = new Polynomial(initialVector1);
            if (p1.getDegree() == 0) {
                continue;
            }
            for (List<Integer> initialVector2 : initialVectors) {
                Polynomial p2 = new Polynomial(initialVector2);
                if (p2.getDegree() == 0) {
                    continue;
                }
                Polynomial multiplyResult = p1.multiply(p2);
                if (multiplyResult.getDegree() == degree && !decomposablePolynomials.contains(multiplyResult)) {
                    decomposablePolynomials.add(multiplyResult);
                }
            }
        }
        return decomposablePolynomials;
    }

    public static List<Integer> getPrimeMembers() {
        List<Integer> members = new ArrayList<>();
        int initialInt = mod;
        for (int i = 2; i < mod; i++) {
            while (initialInt % i == 0) {
                members.add(i);
                initialInt = initialInt / i;
            }
            if (initialInt == 1) {
                break;
            }
        }
        return members;
    }
}
