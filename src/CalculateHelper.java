import pojo.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateHelper {

    public static void main(String[] args) {
        showSimpleDemo();
    }

    private static void showSimpleDemo() {
        Field.mod = 4;
        System.out.println("Fp = " + Field.mod);
        RecurrentRelation recurrentRelation = new RecurrentRelation(Arrays.asList(3, 3));
        System.out.println("Рекуррентное соотношение: " + recurrentRelation);
        LRP lrp = new LRP(recurrentRelation, Arrays.asList(1, 1));
        int tempPeriod = lrp.getSequenceSize();
        Polynomial characteristicPolynomial = lrp.getCharacteristicPolynomial();
        System.out.println("Характеристический многочлен: " + characteristicPolynomial);
        if (!Field.isModPrime()) {

        }
        System.out.println("============================================");
        System.out.println("Начальный вектор u0: " + lrp.getInitialVector());
        List<Integer> lrpSequence = lrp.getSequence(tempPeriod);
        System.out.println("Последовательность u(x): " + lrpSequence);
        Polynomial generator = lrp.getGenerator();
        System.out.println("Генератор G(x): " + generator);
        System.out.println("============================================");
        GreatestCommonDivisor gcd = new GreatestCommonDivisor(characteristicPolynomial, generator);
        System.out.println("НОД (F(x), G(x)): " + gcd);
        System.out.println("Минимальный многочлен M(x): " + lrp.getMinimalPolynomial());
        System.out.println("Экспонента F(x): " + tempPeriod);
        System.out.println("Экспонента M(x): " + lrp.getPeriod());
        System.out.println("============================================");
        List<List<LRP>> cyclicClasses = lrp.getCyclicClasses();
        System.out.println("Циклических классов: " + cyclicClasses.size() + ". ");
        System.out.println("Размеры циклических классов: " + cyclicClasses.stream().map(List::size).distinct().sorted().collect(Collectors.toList()));
        String cyclicType = lrp.getCyclicType(cyclicClasses);
        for (List<LRP> cyclicClass : cyclicClasses) {
            System.out.println("[" + (cyclicClasses.indexOf(cyclicClass) + 1) + "] " + cyclicClass);
        }
        System.out.println("Циклический тип: " + cyclicType);
    }
}