package pojo;

import java.util.ArrayList;
import java.util.List;

public class Field {
    public static int mod = 3;

    public static List<Integer> normalizeCoefficients(List<Integer> coefficients) {
        ArrayList<Integer> result = new ArrayList<>(coefficients);
        for (int i = 0; i < result.size(); i++) {
            while (result.get(i) < 0) {
                int integer = result.get(i);
                result.set(i, integer + mod);
            }
            result.set(i, result.get(i) % mod);
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
                System.out.println(vector);
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

}
