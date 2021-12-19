import pojo.CyclicType;
import pojo.Field;
import pojo.GreatestCommonDivisor;
import pojo.LRP;
import pojo.polynomial.Polynomial;

import java.util.List;
import java.util.stream.Collectors;

public class CalculateHelper {

    public static void main(String[] args) {
        showSimpleDemo();
    }

    private static void showSimpleDemo() {
        Field.setMod(997);
        Polynomial a = new Polynomial(0, 1, 0, 1);
        Polynomial b = new Polynomial(0, 1, 0, 0, 0, 3);
        System.out.println(CyclicType.compositionOf(a, b));
//        showInformationAbout(new LRP(new Polynomial(2, 3, 1)));
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
        if (characteristicPolynomial.isReversible()) {
            List<List<LRP>> cyclicClasses = lrp.getCyclicClasses();
            System.out.println("Циклических классов: " + cyclicClasses.size() + ". ");
            System.out.println("Размеры циклических классов: " + cyclicClasses.stream().map(List::size).distinct().sorted().collect(Collectors.toList()));
            for (int i = 0; i < cyclicClasses.size(); i++) {
                List<LRP> cyclicClass = cyclicClasses.get(i);
                System.out.println("[" + (i + 1) + "] " + cyclicClass);
            }
            System.out.println("Циклический тип: " + lrp.getCyclicType());
        } else {
            System.out.println("Невозможно получить информацию о циклическом типе, так как многочлен не реверсивный");
        }
        System.out.println("============================================");
    }
}