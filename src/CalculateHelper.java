import entities.GreatestCommonDivisor;
import entities.LRP;
import entities.Polynomial;
import entities.RecurrentRelation;
import tests.CalculatingTests;

import java.util.Arrays;
import java.util.List;

public class CalculateHelper {
    public static void main(String[] args) {
        CalculatingTests calculatingTests = new CalculatingTests();

        RecurrentRelation recurrentRelation = new RecurrentRelation(5, Arrays.asList(-1, 1, 2, -1));
        System.out.println("Рекуррентное соотношение: " + recurrentRelation);
        System.out.println(recurrentRelation.getCoefficients());
        LRP lrp = new LRP(recurrentRelation, Arrays.asList(3, 3, 1, 1));
        Polynomial characteristicPolynomial = lrp.getCharacteristicPolynomial();
        System.out.println("Характеристический многочлен: " + characteristicPolynomial);
        System.out.println("Коэффициенты характеристического многочлена: " + characteristicPolynomial.getCoefficients());
        System.out.println("============================================");
        System.out.println("Начальный вектор: " + lrp.getInitialVector());
        List<Integer> lrpSequence = lrp.getSequence(20);
        System.out.println("Последовательность начальная:" + lrpSequence);
        Polynomial generator = lrp.getGenerator();
        System.out.println("Генератор данной последовательности: " + generator);
        System.out.println("============================================");
        LRP impulse = lrp.getImpulse();
        System.out.println("Начальный вектор: " + impulse.getInitialVector() + "\t (mod " + recurrentRelation.getModF() + ")");
        List<Integer> lrpSequence1 = impulse.getSequence(20);
        System.out.println("Последовательность импульсной ф-и: " + lrpSequence1);
        System.out.println("Умножим на генератор: " + generator);
        List<Integer> multiply = impulse.multiply(generator, 20);
        System.out.println("Получим последовательность: " + multiply);
        System.out.println("Генератор новой последовательности: " + new LRP(recurrentRelation, multiply.subList(0, recurrentRelation.getDegree())).getGenerator());
        Polynomial divide = characteristicPolynomial.divide(generator);
        System.out.println(characteristicPolynomial + " / " + generator + " = " + divide + " (остаток " + divide.getRemainder() + ")");
        GreatestCommonDivisor gcd = new GreatestCommonDivisor(characteristicPolynomial, generator);
        System.out.println("НОД = " + gcd);
        System.out.println("Минимальный многочлен = " + characteristicPolynomial + " / " + gcd + " = " + characteristicPolynomial.divide(gcd.getGcdResult()));
        System.out.println("============================================");
        System.out.println(new Polynomial(Arrays.asList(0, 1, 2, 3)).divide(new Polynomial(Arrays.asList(1, 2, 2))));
    }
}