package tests;

import pojo.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class AppTests {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println();
        generateToStringValuesTest();
        calculateGeneratorTest();
        calculateExponentTest();
        generateInitialVectorsTest();
        calculateGSDTest();
        polynomialCyclicTypeCalculatingTest();
        polynomialEqualsTest();
        System.out.println();
    }

    public static void calculateGeneratorTest() {
        Instant start = Instant.now();
        boolean test;

        Field.mod = 5;
        LRP lrp1 = new LRP(new RecurrentRelation(Arrays.asList(-1, 1, -1)), Arrays.asList(3, 3, 1));
        List<Integer> genSeq1 = lrp1.getImpulse().multiply(lrp1.getGenerator(), 20);
        List<Integer> seq1 = lrp1.getSequence(20);
        test = genSeq1.equals(seq1);

        Field.mod = 5;
        LRP lrp2 = new LRP(new RecurrentRelation(Arrays.asList(1, 3, 8, 2)), Arrays.asList(3, 3, 1, 2));
        List<Integer> genSeq2 = lrp2.getImpulse().multiply(lrp2.getGenerator(), 20);
        List<Integer> seq2 = lrp2.getSequence(20);
        test &= genSeq2.equals(seq2);

        Field.mod = 11;
        LRP lrp3 = new LRP(new RecurrentRelation(Arrays.asList(-1, 3, -1, 2, 11, 0, 2)), Arrays.asList(3, -3, 1, 11, 3, 4, 5));
        List<Integer> genSeq3 = lrp3.getImpulse().multiply(lrp3.getGenerator(), 20);
        List<Integer> seq3 = lrp3.getSequence(20);
        test &= genSeq3.equals(seq3);

        Instant finish = Instant.now();
        long testingTimeMillis = Duration.between(start, finish).toMillis();
        String testingTime = testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";

        if (test) {
            System.out.println("- TEST Вычисление генератора последовательности - PASSED (" + testingTime + ")");
        } else {
            System.err.println("- TEST Вычисление генератора последовательности - FAILED (" + testingTime + ")");
        }
    }

    public static void calculateExponentTest() {
        Instant start = Instant.now();
        boolean test;

        Field.mod = 3;
        test = new Polynomial(Arrays.asList(2, 2, 1)).getExp() == 8;

        test &= new Polynomial(Arrays.asList(1, 0, 2, 1)).getExp() == 26;

        test &= new Polynomial(Arrays.asList(2, 2, 2, 1)).getExp() == 13;

        test &= new Polynomial(Arrays.asList(2, 1, 0, 0, 1)).getExp() == 80;

        test &= new Polynomial(Arrays.asList(1, 1, 1, 1, 1)).getExp() == 5;

        Field.mod = 5;
        test &= new Polynomial(Arrays.asList(3, 1, 1, 1)).getExp() == 124;

        test &= new Polynomial(Arrays.asList(1, 4, 3, 1)).getExp() == 62;

        Instant finish = Instant.now();
        long testingTimeMillis = Duration.between(start, finish).toMillis();
        String testingTime = testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";

        if (test) {
            System.out.println("- TEST Вычисление экспоненты многочлена - PASSED ( " + testingTime + ")");
        } else {
            System.err.println("- TEST Вычисление экспоненты многочлена - FAILED (" + testingTime + ")");
        }
    }

    public static void generateInitialVectorsTest() {
        Instant start = Instant.now();
        boolean test;

        Field.mod = 3;
        int dim = 3;
        List<List<Integer>> list1 = Field.generateInitialVectors(dim);
        test = Math.pow(Field.mod, dim) == list1.size();

        Field.mod = 5;
        dim = 4;
        List<List<Integer>> list2 = Field.generateInitialVectors(dim);
        test &= Math.pow(Field.mod, dim) == list2.size();

        Field.mod = 7;
        dim = 5;
        List<List<Integer>> list3 = Field.generateInitialVectors(dim);
        test &= Math.pow(Field.mod, dim) == list3.size();

        Instant finish = Instant.now();
        long testingTimeMillis = Duration.between(start, finish).toMillis();
        String testingTime = testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";

        if (test) {
            System.out.println("- TEST Генерация всевозможных начальных векторов - PASSED (" + testingTime + ")");
        } else {
            System.err.println("- TEST Генерация всевозможных начальных векторов - FAILED (" + testingTime + ")");
        }
    }

    public static void generateToStringValuesTest() {
        Instant start = Instant.now();
        boolean test;

        Field.mod = 3;
        RecurrentRelation r1 = new RecurrentRelation(Arrays.asList(2, 0, 1));
        Polynomial p1 = new Polynomial(r1);
        test = r1.toString().equals("2C0 + 0C1 + C2 = C3") && p1.toString().equals("X^3 + 2X^2 + 1");

        Field.mod = 7;
        RecurrentRelation r2 = new RecurrentRelation(Arrays.asList(0, 0, 0, 0, 6, 0, 7));
        Polynomial p2 = new Polynomial(r2);
        test &= r2.toString().equals("0C0 + 0C1 + 0C2 + 0C3 + 6C4 = C5") && p2.toString().equals("X^5 + X^4");

        Field.mod = 4;
        RecurrentRelation r3 = new RecurrentRelation(Arrays.asList(0, -5));
        Polynomial p3 = new Polynomial(r3);
        test &= r3.toString().equals("0C0 + 3C1 = C2") && p3.toString().equals("X^2 + X");

        test &= new Polynomial(Arrays.asList(0, 0, 1, 0)).toString().equals("X^2");

        Instant finish = Instant.now();
        long testingTimeMillis = Duration.between(start, finish).toMillis();
        String testingTime = testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";

        if (test) {
            System.out.println("- TEST Создание текста для соотношений и многочленов - PASSED (" + testingTime + ")");
        } else {
            System.err.println("- TEST Создание текста для соотношений и многочленов - FAILED (" + testingTime + ")");
        }
    }

    public static void calculateGSDTest() {
        Instant start = Instant.now();
        boolean test;

        Field.mod = 3;
        test = new GreatestCommonDivisor(new Polynomial(Arrays.asList(0, 0, 1)),
                new Polynomial(Arrays.asList(0, 1))).getGcdResult().toString().equals("X");

        Field.mod = 2;
        test &= new GreatestCommonDivisor(new Polynomial(Arrays.asList(1, 0, 1)),
                new Polynomial(Arrays.asList(1, 1))).getGcdResult().toString().equals("X + 1");

        test &= new GreatestCommonDivisor(new Polynomial(Arrays.asList(1, 0, 0, 1, 0)),
                new Polynomial(Arrays.asList(1, 0, 1))).getGcdResult().toString().equals("X + 1");

        Field.mod = 5;
        test &= new GreatestCommonDivisor(new Polynomial(Arrays.asList(2, 2, 2, 3, 1)),
                new Polynomial(Arrays.asList(0, 0, 0, 4, 2, 1))).getGcdResult().toString().equals("1");

        Instant finish = Instant.now();
        long testingTimeMillis = Duration.between(start, finish).toMillis();
        String testingTime = testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";

        if (test) {
            System.out.println("- TEST Вычисление НОД - PASSED (" + testingTime + ")");
        } else {
            System.err.println("- TEST Вычисление НОД - FAILED (" + testingTime + ")");
        }
    }

    public static void polynomialEqualsTest() {
        Instant start = Instant.now();
        boolean test;

        Field.mod = 3;
        LRP lrp1 = new LRP(new RecurrentRelation(Arrays.asList(1, 1, 1)), Arrays.asList(0, 0, 1));
        LRP lrp2 = new LRP(new RecurrentRelation(Arrays.asList(1, 1, 1)), Arrays.asList(0, 2, 0));
        LRP lrp3 = new LRP(new RecurrentRelation(Arrays.asList(1, 1, 1)), Arrays.asList(0, 0, 2));
        test = lrp1.equals(lrp2) && lrp2.equals(lrp1) && !lrp1.equals(lrp3) && !lrp2.equals(lrp3);

        LRP lrp4 = new LRP(new RecurrentRelation(Arrays.asList(2, 0, 1, 1)), Arrays.asList(0, 0, 0, 1));
        LRP lrp5 = new LRP(new RecurrentRelation(Arrays.asList(2, 0, 1, 1)), Arrays.asList(0, 2, 2, 0));
        LRP lrp6 = new LRP(new RecurrentRelation(Arrays.asList(2, 0, 1, 1)), Arrays.asList(1, 1, 0, 2));
        test &= lrp4.equals(lrp5) && lrp5.equals(lrp4) && !lrp4.equals(lrp6) && !lrp5.equals(lrp6);

        Instant finish = Instant.now();
        long testingTimeMillis = Duration.between(start, finish).toMillis();
        String testingTime = testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";

        if (test) {
            System.out.println("- TEST Эквивалентность многочленов - PASSED (" + testingTime + ")");
        } else {
            System.err.println("- TEST Эквивалентность многочленов - FAILED (" + testingTime + ")");
        }
    }

    public static void polynomialCyclicTypeCalculatingTest() {
        Instant start = Instant.now();
        boolean test;

        Field.mod = 2;
        LRP lrp = new LRP(new RecurrentRelation(Arrays.asList(1, 1)), Arrays.asList(0, 1));
        test = lrp.getCyclicType(lrp.getCyclicClasses()).equals("1y + 1y^3");

        Instant finish = Instant.now();
        long testingTimeMillis = Duration.between(start, finish).toMillis();
        String testingTime = testingTimeMillis / 1000 + "s " + testingTimeMillis % 1000 + "ms";

        if (test) {
            System.out.println("- TEST Циклический тип - PASSED (" + testingTime + ")");
        } else {
            System.err.println("- TEST Циклический тип - FAILED (" + testingTime + ")");
        }
    }
}
