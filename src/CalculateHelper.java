import pojo.Field;
import pojo.GreatestCommonDivisor;
import pojo.LRP;
import pojo.RecurrentRelation;
import pojo.polynomial.Polynomial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateHelper {

    public static void main(String[] args) {
        showSimpleDemo();
    }

    private static void showSimpleDemo() {
        Field.mod = 8;
        LRP lrp = new LRP(new Polynomial(Arrays.asList(2, 2, 1)));
        lrp.getPrimaryPeriod();
//        showInformationAbout(lrp);
    }

    private static void showInformationAbout(LRP lrp) {
        System.out.println("============================================");
        System.out.println("Z[x] = " + Field.mod + "\n");
        Polynomial characteristicPolynomial = lrp.getCharacteristicPolynomial();
        System.out.println("F(x): " + characteristicPolynomial + " -- " +
                (characteristicPolynomial.isReversible() ? "реверсивный" : "не реверсивный"));
        System.out.println(!characteristicPolynomial.isDecomposable() ? "Неприводимый" :
                "Приводимый, раскладывается на " + lrp.getDecompositionOfCharacteristicPolynomial());
        if (characteristicPolynomial.isDecomposable()) {
            lrp.getDecompositionOfCharacteristicPolynomial().getDecompositionMap().forEach(
                    (polynomial, degree) -> showInformationAbout(new LRP(polynomial, lrp.getInitialVector()))
            );
        }
        Polynomial generator = lrp.getGenerator();
        System.out.println("Период T(x): " + lrp.getPeriod());
        System.out.println("Данная ЛРП имеет " + (lrp.getPeriod() == (Math.pow(Field.mod, characteristicPolynomial.getDegree()) - 1) ?
                "максимальный период" : "не максимальный период"));
        System.out.println("Генератор G(x): " + generator);
        System.out.println("НОД (F(x), G(x)): " + GreatestCommonDivisor.get(characteristicPolynomial, generator));
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
            String cyclicType = lrp.getCyclicType(cyclicClasses);
            System.out.println("Циклический тип: " + cyclicType);
        } else {
            System.out.println("Невозможно получить информацию о циклическом типе, так как многочлен не реверсивный");
        }
        System.out.println("============================================");
    }
}