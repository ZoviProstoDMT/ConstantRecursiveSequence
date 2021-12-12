package pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static Set<Polynomial> getAllPolynomials(int degree) {
        Set<Polynomial> polynomials = new HashSet<>();
        List<List<Integer>> initialVectors = generateInitialVectors(degree);
        for (List<Integer> initialVector : initialVectors) {
            polynomials.add(new Polynomial(initialVector));
        }
        return polynomials;
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
