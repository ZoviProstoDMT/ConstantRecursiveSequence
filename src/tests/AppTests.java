package tests;

import helper.Converter;
import pojo.Field;
import pojo.GreatestCommonDivisor;
import pojo.LRP;
import pojo.RecurrentRelation;
import pojo.polynomial.Polynomial;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AppTests extends AbstractTest implements Converter {

    public static void main(String[] args) {
        new AppTests().run();
    }

    private void run() {
        mathOperationsTest();
        converterTest();
        generateToStringValuesTest();
        calculateGeneratorTest();
        calculateExponentTest();
        generateInitialVectorsTest();
        calculateGSDTest();
        polynomialEqualsTest();
        isDecomposableTest();
        getITest();
        polynomialCyclicTypeTest();
    }

    private void mathOperationsTest() {
        startTest();

        Field.setMod(3);
        Polynomial p1 = new Polynomial(0, 1);
        Polynomial p2 = new Polynomial(0, 1);

        Polynomial sum = p1.sum(p2);
        result &= sum.getCoefficients().equals(Arrays.asList(0, 2)) && p1.getCoefficients().equals(Arrays.asList(0, 1))
                && p2.getCoefficients().equals(Arrays.asList(0, 1));

        Polynomial subtract = p1.subtract(p2);
        result &= subtract.isNull() && p1.getCoefficients().equals(Arrays.asList(0, 1))
                && p2.getCoefficients().equals(Arrays.asList(0, 1));

        Polynomial divide = p1.divide(p2);
        result &= divide.getCoefficients().equals(Collections.singletonList(1)) && divide.getRemainder().isNull()
                && p1.getCoefficients().equals(Arrays.asList(0, 1))
                && p2.getCoefficients().equals(Arrays.asList(0, 1));

        Polynomial multiply = p1.multiply(p2);
        result &= multiply.getCoefficients().equals(Arrays.asList(0, 0, 1))
                && p1.getCoefficients().equals(Arrays.asList(0, 1))
                && p2.getCoefficients().equals(Arrays.asList(0, 1));

        completeTest();
    }

    private void converterTest() {
        startTest();

        Field.setMod(3);
        RecurrentRelation recurrentRelation = new RecurrentRelation(Arrays.asList(2, 0, 1, 1));
        LRP lrp1 = new LRP(recurrentRelation, Arrays.asList(0, 0, 0, 1));
        Polynomial characteristicPolynomial = lrp1.getCharacteristicPolynomial();

        Polynomial convertedPolynomial = convertFrom(recurrentRelation);
        result = characteristicPolynomial.equals(convertedPolynomial);

        RecurrentRelation converterRelation = convertFrom(convertedPolynomial);
        result &= converterRelation.equals(recurrentRelation);

        completeTest();
    }

    private void generateToStringValuesTest() {
        startTest();

        Field.setMod(3);
        RecurrentRelation r1 = new RecurrentRelation(Arrays.asList(0, 2, 0, 2, 0, 0));
        Polynomial p11 = new LRP(r1, Arrays.asList(1, 1, 1, 1, 1, 1)).getCharacteristicPolynomial();
        Polynomial p1 = convertFrom(r1);
        result = r1.toString().equals("0C0 + 2C1 + 0C2 + 2C3 + 0C4 + 0C5 = C6") &&
                p11.toString().equals("x^6 + x^3 + x") && p1.toString().equals("x^6 + x^3 + x");

        Field.setMod(7);
        RecurrentRelation r2 = new RecurrentRelation(Arrays.asList(6, 6, 0, 7));
        Polynomial p22 = new LRP(r2, Arrays.asList(0, 0, 7, 1)).getCharacteristicPolynomial();
        Polynomial p2 = convertFrom(r2);
        result &= r2.toString().equals("6C0 + 6C1 + 0C2 + 0C3 = C4") &&
                p22.toString().equals("x^4 + x + 1") && p2.toString().equals("x^4 + x + 1");

        Field.setMod(4);
        RecurrentRelation r3 = new RecurrentRelation(Arrays.asList(3, 0, 4, 4, 3));
        Polynomial p33 = new LRP(r3, Arrays.asList(0, 0, 0, 4, 1)).getCharacteristicPolynomial();
        Polynomial p3 = convertFrom(r3);
        result &= r3.toString().equals("3C0 + 0C1 + 0C2 + 0C3 + 3C4 = C5") &&
                p33.toString().equals("x^5 + x^4 + 1") && p3.toString().equals("x^5 + x^4 + 1");

        completeTest();
    }

    private void calculateGeneratorTest() {
        startTest();

        Field.setMod(5);
        LRP lrp1 = new LRP(new RecurrentRelation(Arrays.asList(-1, 1, -1)), Arrays.asList(3, 3, 1));
        List<Integer> genSeq1 = lrp1.getImpulse().multiply(lrp1.getGenerator(), 20);
        List<Integer> seq1 = lrp1.getSequence(20);
        result = genSeq1.equals(seq1);

        Field.setMod(5);
        LRP lrp2 = new LRP(new RecurrentRelation(Arrays.asList(1, 3, 8, 2)), Arrays.asList(3, 3, 1, 2));
        List<Integer> genSeq2 = lrp2.getImpulse().multiply(lrp2.getGenerator(), 20);
        List<Integer> seq2 = lrp2.getSequence(20);
        result &= genSeq2.equals(seq2);

        Field.setMod(11);
        LRP lrp3 = new LRP(new RecurrentRelation(Arrays.asList(-1, 3, -1, 2, 11, 0, 2)), Arrays.asList(3, -3, 1, 11, 3, 4, 5));
        List<Integer> genSeq3 = lrp3.getImpulse().multiply(lrp3.getGenerator(), 20);
        List<Integer> seq3 = lrp3.getSequence(20);
        result &= genSeq3.equals(seq3);

        completeTest();
    }

    private void calculateExponentTest() {
        startTest();

        Field.setMod(3);
        result = new Polynomial(Arrays.asList(2, 2, 1)).getExp() == 8;
        result &= new Polynomial(Arrays.asList(1, 0, 2, 1)).getExp() == 26;
        result &= new Polynomial(Arrays.asList(2, 2, 2, 1)).getExp() == 13;
        result &= new Polynomial(Arrays.asList(2, 1, 0, 0, 1)).getExp() == 80;
        result &= new Polynomial(Arrays.asList(1, 1, 1, 1, 1)).getExp() == 5;

        Field.setMod(5);
        result &= new Polynomial(Arrays.asList(3, 1, 1, 1)).getExp() == 124;
        result &= new Polynomial(Arrays.asList(1, 4, 3, 1)).getExp() == 62;

        completeTest();
    }

    private void generateInitialVectorsTest() {
        startTest();

        Field.setMod(3);
        int dim = 3;
        List<List<Integer>> list1 = Field.generateInitialVectors(dim);
        result = Math.pow(Field.getMod(), dim) == list1.size();

        Field.setMod(5);
        dim = 4;
        List<List<Integer>> list2 = Field.generateInitialVectors(dim);
        result &= Math.pow(Field.getMod(), dim) == list2.size();

        Field.setMod(7);
        dim = 5;
        List<List<Integer>> list3 = Field.generateInitialVectors(dim);
        result &= Math.pow(Field.getMod(), dim) == list3.size();

        completeTest();
    }

    private void calculateGSDTest() {
        startTest();

        Field.setMod(3);
        result = GreatestCommonDivisor.get(new Polynomial(Arrays.asList(0, 0, 1)),
                new Polynomial(Arrays.asList(0, 1))).toString().equals("x");

        Field.setMod(2);
        result &= GreatestCommonDivisor.get(new Polynomial(Arrays.asList(1, 0, 1)),
                new Polynomial(Arrays.asList(1, 1))).toString().equals("x + 1");

        result &= GreatestCommonDivisor.get(new Polynomial(Arrays.asList(1, 0, 0, 1, 0)),
                new Polynomial(Arrays.asList(1, 0, 1))).toString().equals("x + 1");

        Field.setMod(5);
        result &= GreatestCommonDivisor.get(new Polynomial(Arrays.asList(2, 2, 2, 3, 1)),
                new Polynomial(Arrays.asList(0, 0, 0, 4, 2, 1))).toString().equals("1");

        completeTest();
    }

    private void polynomialEqualsTest() {
        startTest();

        Field.setMod(3);
        LRP lrp1 = new LRP(new RecurrentRelation(Arrays.asList(1, 1, 1)), Arrays.asList(0, 0, 1));
        LRP lrp2 = new LRP(new RecurrentRelation(Arrays.asList(1, 1, 1)), Arrays.asList(0, 2, 0));
        LRP lrp3 = new LRP(new RecurrentRelation(Arrays.asList(1, 1, 1)), Arrays.asList(0, 0, 2));
        result = lrp1.equals(lrp2) && lrp2.equals(lrp1) && !lrp1.equals(lrp3) && !lrp2.equals(lrp3);

        LRP lrp4 = new LRP(new RecurrentRelation(Arrays.asList(2, 0, 1, 1)), Arrays.asList(0, 0, 0, 1));
        LRP lrp5 = new LRP(new RecurrentRelation(Arrays.asList(2, 0, 1, 1)), Arrays.asList(0, 2, 2, 0));
        LRP lrp6 = new LRP(new RecurrentRelation(Arrays.asList(2, 0, 1, 1)), Arrays.asList(1, 1, 0, 2));
        result &= lrp4.equals(lrp5) && lrp5.equals(lrp4) && !lrp4.equals(lrp6) && !lrp5.equals(lrp6);

        Field.setMod(7);
        LRP lrp7 = new LRP(new RecurrentRelation(Arrays.asList(2, 0, 1, 1, 6)), Arrays.asList(1, 1, 1, 0, 1));
        LRP lrp8 = new LRP(new RecurrentRelation(Arrays.asList(2, 0, 1, 1, 6)), Arrays.asList(1, 0, 1, 1, 1));
        result &= !lrp7.equals(lrp8);

        completeTest();
    }

    private void isDecomposableTest() {
        startTest();

        Field.setMod(3);
        LRP lrp1 = new LRP(new RecurrentRelation(Arrays.asList(2, 0, 1, 1)), Arrays.asList(0, 0, 0, 1));
        result = lrp1.getCharacteristicPolynomial().isDecomposable() == Boolean.TRUE;

        completeTest();
    }

    private void getITest() {
        startTest();

        Field.setMod(3);
        LRP lrp1 = new LRP(new RecurrentRelation(Arrays.asList(2, 0, 1, 1)), Arrays.asList(0, 0, 0, 1));
        result = lrp1.getI(2) != 0 && lrp1.getI(4) == 0;

        completeTest();
    }

    private void polynomialCyclicTypeTest() {
        startTest();

        Field.setMod(4);
        LRP lrp1 = new LRP(new RecurrentRelation(Collections.singletonList(3)), Collections.singletonList(1));
        result = lrp1.getCyclicType().toString().equals("2y + y^2");

        Field.setMod(2);
        LRP lrp2 = new LRP(new Polynomial(1, 0, 1, 1, 1, 0, 1));
        result &= lrp2.getCyclicType().toString().equals("y + y^3 + 3y^5 + 3y^15");

        completeTest();
    }
}
