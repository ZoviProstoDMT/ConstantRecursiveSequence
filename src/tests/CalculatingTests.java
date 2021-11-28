package tests;

import pojos.Field;
import pojos.LRP;
import pojos.Polynomial;
import pojos.RecurrentRelation;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CalculatingTests {

    public static void run() {
        System.out.println();
        calculateGeneratorTest();
        calculateExponentTest(false);
        generateInitialVectorsTest();
        System.out.println();
    }

    public static void calculateGeneratorTest() {
        Instant start = Instant.now();

        Field.mod = 5;
        LRP lrp1 = new LRP(new RecurrentRelation(Arrays.asList(-1, 1, -1)), Arrays.asList(3, 3, 1));
        List<Integer> genSeq1 = lrp1.getImpulse().multiply(lrp1.getGenerator(), 20);
        List<Integer> seq1 = lrp1.getSequence(20);
        boolean test1 = genSeq1.equals(seq1);

        Field.mod = 5;
        LRP lrp2 = new LRP(new RecurrentRelation(Arrays.asList(1, 3, 1, 2)), Arrays.asList(3, 3, 1, 2));
        List<Integer> genSeq2 = lrp2.getImpulse().multiply(lrp2.getGenerator(), 20);
        List<Integer> seq2 = lrp2.getSequence(20);
        boolean test2 = genSeq2.equals(seq2);

        //todo понять почему число 11 не проходит тест в коеффициентах? Не обнуляется % mod
        Field.mod = 11;
        LRP lrp3 = new LRP(new RecurrentRelation(Arrays.asList(-1, 3, -1, 2, 10, 0, 2)), Arrays.asList(3, -3, 1, 2, 3, 4, 5));
        List<Integer> genSeq3 = lrp3.getImpulse().multiply(lrp3.getGenerator(), 20);
        List<Integer> seq3 = lrp3.getSequence(20);
        boolean test3 = genSeq3.equals(seq3);

        Instant finish = Instant.now();
        long testingTimeMillis = Duration.between(start, finish).toMillis();
        String testingTime = testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";

        if (test1 && test2 && test3) {
            System.out.println("- TEST Вычисление генератора последовательности - PASSED (" + testingTime + ")");
        } else {
            System.err.println("- TEST Вычисление генератора последовательности - FAILED (" + testingTime + ")");
        }
    }

    public static void calculateExponentTest(boolean printResults) {
        Instant start = Instant.now();

        Field.mod = 3;
        Polynomial test1Polynomial = new Polynomial(Arrays.asList(2, 2, 1));
        int test1Exp = test1Polynomial.getExp();
        boolean test1 = test1Exp == 8;

        Polynomial test2Polynomial = new Polynomial(Arrays.asList(1, 0, 2, 1));
        int test2Exp = test2Polynomial.getExp();
        boolean test2 = test2Exp == 26;

        Polynomial test3Polynomial = new Polynomial(Arrays.asList(2, 2, 2, 1));
        int test3Exp = test3Polynomial.getExp();
        boolean test3 = test3Exp == 13;

        Polynomial test4Polynomial = new Polynomial(Arrays.asList(2, 1, 0, 0, 1));
        int test4Exp = test4Polynomial.getExp();
        boolean test4 = test4Exp == 80;

        Polynomial test5Polynomial = new Polynomial(Arrays.asList(1, 1, 1, 1, 1));
        int test5Exp = test5Polynomial.getExp();
        boolean test5 = test5Exp == 5;

        Field.mod = 5;
        Polynomial test6Polynomial = new Polynomial(Arrays.asList(3, 1, 1, 1));
        int test6Exp = test6Polynomial.getExp();
        boolean test6 = test6Exp == 124;

        Polynomial test7Polynomial = new Polynomial(Arrays.asList(1, 4, 3, 1));
        int test7Exp = test7Polynomial.getExp();
        boolean test7 = test7Exp == 62;

        Instant finish = Instant.now();
        long testingTimeMillis = Duration.between(start, finish).toMillis();
        String testingTime = testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";

        if (test1 && test2 && test3 && test4 && test5 && test6 && test7) {
            System.out.println("- TEST Вычисление экспоненты многочлена - PASSED ( " + testingTime + ")");
        } else {
            System.err.println("- TEST Вычисление экспоненты многочлена - FAILED (" + testingTime + ")");
        }
        if (printResults) {
            System.out.printf("%s : Expected result - %s, Actual result - %s%n", test1Polynomial, 8, test1Exp);
            System.out.printf("%s : Expected result - %s, Actual result - %s%n", test2Polynomial, 26, test2Exp);
            System.out.printf("%s : Expected result - %s, Actual result - %s%n", test3Polynomial, 13, test3Exp);
            System.out.printf("%s : Expected result - %s, Actual result - %s%n", test4Polynomial, 80, test4Exp);
            System.out.printf("%s : Expected result - %s, Actual result - %s%n", test5Polynomial, 5, test5Exp);
            System.out.printf("%s : Expected result - %s, Actual result - %s%n", test6Polynomial, 124, test6Exp);
            System.out.printf("%s : Expected result - %s, Actual result - %s%n", test7Polynomial, 62, test7Exp);
        }
    }

    public static void generateInitialVectorsTest() {
        Instant start = Instant.now();

        Field.mod = 3;
        int dim = 3;
        List<List<Integer>> list1 = Field.generateInitialVectors(dim);
        boolean test1 = Math.pow(Field.mod, dim) == list1.size();

        Field.mod = 5;
        dim = 4;
        List<List<Integer>> list2 = Field.generateInitialVectors(dim);
        boolean test2 = Math.pow(Field.mod, dim) == list2.size();

        Field.mod = 7;
        dim = 5;
        List<List<Integer>> list3 = Field.generateInitialVectors(dim);
        boolean test3 = Math.pow(Field.mod, dim) == list3.size();

        Instant finish = Instant.now();
        long testingTimeMillis = Duration.between(start, finish).toMillis();
        String testingTime = testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";

        if (test1 && test2 && test3) {
            System.out.println("- TEST Генерация всевозможных начальных векторов - PASSED (" + testingTime + ")");
        } else {
            System.err.println("- TEST Генерация всевозможных начальных векторов - FAILED (" + testingTime + ")");
        }
    }
}
