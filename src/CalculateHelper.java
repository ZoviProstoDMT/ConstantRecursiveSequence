import pojo.Field;
import pojo.GreatestCommonDivisor;
import pojo.LRP;
import pojo.polynomial.Polynomial;
import tests.SequenceTests;

import java.util.List;
import java.util.Map;

public class CalculateHelper {

    public static void main(String[] args) {
        showSimpleDemo();
    }

    private static void showSimpleDemo() {
        Field.setMod(9);
        Polynomial polynomial = new Polynomial(1, 1, 0, 1);

//        List<Integer> initialVector = Arrays.asList(1, 1, 0, 1);
        LRP lrp = new LRP(polynomial);

        System.out.println(lrp.getRecurrentRelation());

        showInformationAbout(lrp);
    }

    private static void showInformationAbout(LRP lrp) {
        System.out.println("============================================");
        System.out.println("Z[x] = " + Field.getMod() + "\n");
        Polynomial characteristicPolynomial = lrp.getCharacteristicPolynomial();
        System.out.println("F(x): " + characteristicPolynomial + " -- " +
                (characteristicPolynomial.isReversible() ? "реверсивный" : "не реверсивный"));
        System.out.println(!characteristicPolynomial.isDecomposable() ? "Неприводимый" :
                "Приводимый, раскладывается на " + lrp.getDecompositionOfCharacteristicPolynomial());
        Polynomial generator = lrp.getGenerator();
        System.out.println("Период T(x): " + lrp.getPeriod());
        System.out.println("Данная ЛРП имеет " + (lrp.isPeriodLargest() ? "максимальный период" : "не максимальный период"));
        System.out.println("Генератор Ф(x): " + generator);
        System.out.println("НОД (F(x), G (x)): " + GreatestCommonDivisor.get(characteristicPolynomial, generator));
        Polynomial minimalPolynomial = lrp.getMinimalPolynomial();
        System.out.println("Минимальный многочлен M(x): " + minimalPolynomial);
        System.out.println();
        System.out.println("Циклический тип: " + lrp.getCyclicType());
        System.out.println("============================================");
        Map<Integer, Integer> frequencyResponse = lrp.getFrequencyResponse();
        List<Integer> sequence = lrp.getSequence(lrp.getPeriod());
        System.out.println("Последовательность u(x): " + sequence);
        System.out.println("Распределение элементов");
        System.out.println(frequencyResponse);
        System.out.println("Критерий Хи квадрат: " + SequenceTests.getX2CriteriaValue(frequencyResponse, sequence.size()));
        System.out.println("============================================");
        int z = 5;
        System.out.println("Рассмотрим I_F(z) для z = " + z);
        System.out.println("I_F(z) = " + lrp.getFI(z));
    }
}