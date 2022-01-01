import pojo.Field;
import pojo.GreatestCommonDivisor;
import pojo.LRP;
import pojo.polynomial.Polynomial;

import java.util.Arrays;
import java.util.List;

public class CalculateHelper {

    public static void main(String[] args) {
        showSimpleDemo();
    }

    private static void showSimpleDemo() {
        Field.setMod(8);
        Polynomial polynomial = new Polynomial(1, 1, 0, 8, 0, 0, 1);
        List<Integer> initialVector = Arrays.asList(1, 2, 3, 4, 5, 1);
        LRP lrp = new LRP(polynomial, initialVector);

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
    }
}