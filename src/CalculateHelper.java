import pojo.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculateHelper {

    public static void main(String[] args) {
        showSimpleDemo();
    }

    private static void showSimpleDemo() {
        Field.mod = 3;
        RecurrentRelation recurrentRelation = new RecurrentRelation(Arrays.asList(1, 2, 2, 1, 1));
        LRP lrp = new LRP(recurrentRelation, Arrays.asList(1, 0, 1, 2, 1));
        Polynomial characteristicPolynomial = lrp.getCharacteristicPolynomial();
        if (!Field.isModPrime()) {
            System.out.println("Рекуррентное соотношение: " + recurrentRelation);
            System.out.println("F(x): " + characteristicPolynomial + "\t Z = " + Field.mod);
            System.out.println();
            System.out.print("Z[x] не является простым. Рассмотрим композицию: ");
            List<Integer> primeMembers = Field.getPrimeMembers();
            System.out.println("Z" + Field.mod + " = " +
                    primeMembers.toString().replace("[", "Z").replace("]", "")
                            .replace(" ", "Z").replace(",", " + "));
            System.out.println();
            for (Integer primeMember : primeMembers) {
                Field.mod = primeMember;
                showInformationAbout(new LRP(new RecurrentRelation(recurrentRelation.getCoefficients()), lrp.getInitialVector()));
            }
        } else {
            showInformationAbout(lrp);
        }
    }

    private static void showInformationAbout(LRP lrp) {
        System.out.println("============================================");
        System.out.println("Z[x] = " + Field.mod + "\n");
        Polynomial characteristicPolynomial = lrp.getCharacteristicPolynomial();
        System.out.print("F(x): " + characteristicPolynomial + " -- " +
                (characteristicPolynomial.isReversible() ? "реверсивный, " : "не реверсивный, "));
        Polynomial minimalPolynomial = lrp.getMinimalPolynomial();
        Map<Polynomial, Integer> decomposeOfCharacteristicPolynomial = characteristicPolynomial.decompose(minimalPolynomial);
        System.out.println(!(decomposeOfCharacteristicPolynomial.size() > 1) ? "неприводимый" :
                "приводимый, раскладывается на " + decomposeOfCharacteristicPolynomial);
        Polynomial generator = lrp.getGenerator();
        System.out.println("Генератор G(x): " + generator);
        GreatestCommonDivisor gcd = new GreatestCommonDivisor(characteristicPolynomial, generator);
        System.out.println("НОД (F(x), G(x)): " + gcd);
        System.out.println("Минимальный многочлен M(x): " + minimalPolynomial);
        System.out.println("Период T(x): " + lrp.getPeriod());
        System.out.println();
        if (characteristicPolynomial.isReversible()) {
            List<List<LRP>> cyclicClasses = lrp.getCyclicClasses();
            System.out.println("Циклических классов: " + cyclicClasses.size() + ". ");
            System.out.println("Размеры циклических классов: " + cyclicClasses.stream().map(List::size).distinct().sorted().collect(Collectors.toList()));
            for (List<LRP> cyclicClass : cyclicClasses) {
                System.out.println("[" + (cyclicClasses.indexOf(cyclicClass) + 1) + "] " + cyclicClass);
            }
            String cyclicType = lrp.getCyclicType(cyclicClasses);
            System.out.println("Циклический тип: " + cyclicType);
        } else {
            System.out.println("Невозможно получить информацию о циклическом типе, так как многочлен не реверсивный");
        }
        System.out.println("============================================");
    }
}