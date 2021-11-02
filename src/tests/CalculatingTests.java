package tests;

import entities.Field;
import entities.LRP;
import entities.Polynomial;
import entities.RecurrentRelation;

import java.util.Arrays;
import java.util.List;

public class CalculatingTests {

    public static void run() {
        System.out.println();
        calculateGeneratorTest();
        calculateExponentTest(false);
        System.out.println();
    }

    public static void calculateGeneratorTest() {
        Field.modF = 5;
        LRP lrp1 = new LRP(new RecurrentRelation(Arrays.asList(-1, 1, -1)), Arrays.asList(3, 3, 1));
        List<Integer> genSeq1 = lrp1.getImpulse().multiply(lrp1.getGenerator(), 20);
        List<Integer> seq1 = lrp1.getSequence(20);
        boolean test1 = genSeq1.equals(seq1);

        Field.modF = 4;
        LRP lrp2 = new LRP(new RecurrentRelation(Arrays.asList(-1, 3, -1, 2)), Arrays.asList(3, -3, 1, 2));
        List<Integer> genSeq2 = lrp2.getImpulse().multiply(lrp2.getGenerator(), 20);
        List<Integer> seq2 = lrp2.getSequence(20);
        boolean test2 = genSeq2.equals(seq2);

        Field.modF = 11;
        LRP lrp3 = new LRP(new RecurrentRelation(Arrays.asList(-1, 3, -1, 2, 11, 0, 2)), Arrays.asList(3, -3, 1, 2, 3, 4, 5));
        List<Integer> genSeq3 = lrp3.getImpulse().multiply(lrp3.getGenerator(), 20);
        List<Integer> seq3 = lrp3.getSequence(20);
        boolean test3 = genSeq3.equals(seq3);

        if (test1 && test2 && test3) {
            System.out.println("- TEST Вычисление генератора последовательности - PASSED");
        } else {
            System.err.println("- TEST Вычисление генератора последовательности - FAILED");
        }
    }

    public static void calculateExponentTest(boolean printResults) {
        Field.modF = 3;
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

        Field.modF = 5;
        Polynomial test6Polynomial = new Polynomial(Arrays.asList(3, 1, 1, 1));
        int test6Exp = test6Polynomial.getExp();
        boolean test6 = test6Exp == 124;

        Polynomial test7Polynomial = new Polynomial(Arrays.asList(1, 4, 3, 1));
        int test7Exp = test7Polynomial.getExp();
        boolean test7 = test7Exp == 62;

        if (test1 && test2 && test3 && test4 && test5 && test6 && test7) {
            System.out.println("- TEST Вычисление экспоненты многочлена - PASSED");
        } else {
            System.err.println("- TEST Вычисление экспоненты многочлена - FAILED");
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
}
