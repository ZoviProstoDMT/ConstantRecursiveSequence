package pojo;

import pojo.polynomial.Polynomial;

import java.util.*;

public class Field {

    private static final Map<Integer, FieldInfo> allFields = new HashMap<>();
    //    private static final Map<Polynomial, Integer> notDecomposableMod2 = new HashMap<>();
    public static FieldInfo fieldInfo;
    private static int mod;

    public static int getMod() {
        return mod;
    }

    public static void setMod(int mod) {
        Field.mod = mod;
        if (allFields.containsKey(mod)) {
            Field.fieldInfo = allFields.get(mod);
        } else {
            boolean isPrime = isModPrime();
            boolean isPrimary = !isPrime && isModPrimary();
            int p = mod;
            int n = 1;
            if (isPrimary) {
                Map<Integer, Integer> primaryMembers = getPrimaryMembers();
                p = primaryMembers.keySet().stream().findFirst().orElse(0);
                n = primaryMembers.values().stream().findFirst().orElse(0);
            }
            FieldInfo fieldInfo = new FieldInfo(isPrime, isPrimary, p, n);
            Field.fieldInfo = fieldInfo;
            Field.allFields.put(mod, fieldInfo);
        }
    }

    public static FieldInfo getFieldInfo() {
        return fieldInfo;
    }

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
            vectors.add(new ArrayList<>(vector));
        }
        return vectors;
    }

    public static Set<Polynomial> getAllPolynomials(int degree) {
        Set<Polynomial> polynomials = new HashSet<>();
        List<List<Integer>> initialVectors = generateInitialVectors(degree);
        for (List<Integer> initialVector : initialVectors) {
            polynomials.add(new Polynomial(initialVector, false));
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

    private static boolean isModPrime() {
        double halfRange = Math.sqrt(mod);
        for (int i = 2; i <= halfRange; i++) {
            if (mod % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isModPrimary() {
        return getPrimaryMembers().values().stream().anyMatch(integer -> integer > 1);
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

    private static Map<Integer, Integer> getPrimaryMembers() {
        Map<Integer, Integer> result = new HashMap<>();
        for (Integer primeMember : getPrimeMembers()) {
            result.compute(primeMember, (key, value) -> value == null ? 1 : ++value);
        }
        return result;
    }

//    private void generateNotDecomposablePolynomials() {
//        notDecomposableMod2.put(new Polynomial(1, 0), 1);
//        notDecomposableMod2.put(new Polynomial(1, 1), 1);
//        notDecomposableMod2.put(new Polynomial(1, 1, 1), 3);
//        notDecomposableMod2.put(new Polynomial(1, 0, 1, 1), 7);
//        notDecomposableMod2.put(new Polynomial(1, 1, 0, 1), 7);
//        notDecomposableMod2.put(new Polynomial(1, 0, 0, 1, 1), 15);
//        notDecomposableMod2.put(new Polynomial(1, 1, 0, 0, 1), 15);
//        notDecomposableMod2.put(new Polynomial(1, 1, 1, 1, 1), 5);
//        notDecomposableMod2.put(new Polynomial(1, 0, 0, 1, 0, 1), 31);
//        notDecomposableMod2.put(new Polynomial(1, 0, 1, 0, 0, 1), 31);
//        notDecomposableMod2.put(new Polynomial(1, 0, 1, 1, 1, 1), 31);
//        notDecomposableMod2.put(new Polynomial(1, 1, 0, 1, 1, 1), 31);
//        notDecomposableMod2.put(new Polynomial(1, 1, 1, 0, 1, 1), 31);
//        notDecomposableMod2.put(new Polynomial(1, 0, 0, 0, 0, 1, 1), 63);
//        notDecomposableMod2.put(new Polynomial(1, 0, 0, 1, 0, 0, 1), 63);
//        notDecomposableMod2.put(new Polynomial(1, 0, 1, 0, 1, 1, 1), 63);
//    }
}

class FieldInfo {
    private final boolean isPrime;
    private final boolean isPrimary;
    private final int p;
    private final int n;

    public FieldInfo(boolean isPrime, boolean isPrimary, int p, int n) {
        this.isPrime = isPrime;
        this.isPrimary = isPrimary;
        this.p = p;
        this.n = n;
    }

    public boolean isPrime() {
        return isPrime;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public int getP() {
        return p;
    }

    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "isPrime=" + isPrime +
                ", isPrimary=" + isPrimary +
                ", p=" + p +
                ", n=" + n +
                '}';
    }
}