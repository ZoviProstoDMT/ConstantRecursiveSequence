import entities.LRP;
import entities.Polynomial;
import entities.RecurrentRelation;

import java.util.Arrays;
import java.util.List;

public class CalculateHelper {
    public static void main(String[] args) {
//        RecurrentRelation recurrentRelation = new RecurrentRelation(5, 7, Arrays.asList(0, 4, 2, 0, 3, 1, 2));
//        System.out.println("Рекуррентное соотношение: " + recurrentRelation);
//        Polynomial polynomial = new Polynomial(recurrentRelation);
//        System.out.println("Характеристический многочлен: " + polynomial);
//        System.out.println("Коэффициенты характеристического многочлена: " + polynomial.getCoefficients());
//        System.out.println("============================================");
//        LRP lrp = new LRP(recurrentRelation, Arrays.asList(3, 1, 1, 0, 4, 1, 0));
//        System.out.println("Начальный вектор: " + lrp.getU0());
//        List<Integer> lrpSequence = lrp.getSequence(30);
//        System.out.println("Последовательность размера " + lrpSequence.size() + ": " + lrpSequence);
//        System.out.println("Генератор данной последовательности: " + lrp.getGenerator());
//        System.out.println("============================================");
//        LRP lrp1 = new LRP(recurrentRelation, Arrays.asList(0, 0, 0, 0, 0, 0, 1));
//        System.out.println("Начальный вектор: " + lrp1.getU0());
//        List<Integer> lrpSequence1 = lrp1.getSequence(100);
//        System.out.println("Последовательность размера " + lrpSequence1.size() + ": " + lrpSequence1);
//        System.out.println("Умножим на генератор: " + lrp.getGenerator());
//        LRP multiplyedBy3x3x = lrp1.multiply(lrp.getGenerator());
//        System.out.println("Получим последовательность: " + multiplyedBy3x3x.getSequence(20));
//        System.out.println("Генератор новой последовательности: " + multiplyedBy3x3x.getGenerator());

        RecurrentRelation recurrentRelation = new RecurrentRelation(5, 3, Arrays.asList(1, 0, 2));
        System.out.println("Рекуррентное соотношение: " + recurrentRelation);
        Polynomial polynomial = new Polynomial(recurrentRelation);
        System.out.println("Характеристический многочлен: " + polynomial);
        System.out.println("Коэффициенты характеристического многочлена: " + polynomial.getCoefficients());
        System.out.println("============================================");
        LRP lrp = new LRP(recurrentRelation, Arrays.asList(3, 1, 1));
        System.out.println("Начальный вектор: " + lrp.getU0());
        List<Integer> lrpSequence = lrp.getSequence(30);
        System.out.println("Последовательность размера " + lrpSequence.size() + ": " + lrpSequence);
        System.out.println("Генератор данной последовательности: " + lrp.getGenerator());
        System.out.println("============================================");
        LRP lrp1 = new LRP(recurrentRelation, Arrays.asList(0, 0, 1));
        System.out.println("Начальный вектор: " + lrp1.getU0());
        List<Integer> lrpSequence1 = lrp1.getSequence(100);
        System.out.println("Последовательность размера " + lrpSequence1.size() + ": " + lrpSequence1);
        System.out.println("Умножим на генератор: " + lrp.getGenerator());
        LRP multiplyedBy3x3x = lrp1.multiply(lrp.getGenerator());
        System.out.println("Получим последовательность: " + multiplyedBy3x3x.getSequence(20));
        System.out.println("Генератор новой последовательности: " + multiplyedBy3x3x.getGenerator());
        Polynomial newPolynomial2 = new Polynomial(Arrays.asList(2, 1, 3));
        Polynomial newPolynomial1 = new Polynomial(Arrays.asList(1, 1));
        System.out.println(newPolynomial2 + "\n / \n" + "X^2" + "\n============\n" + newPolynomial2.divide(1, 2));
    }
}