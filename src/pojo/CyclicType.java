package pojo;

import helper.Converter;
import pojo.polynomial.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class CyclicType implements Converter {

    private Polynomial polynomial;

    private CyclicType(Polynomial cyclicType) {
        this.polynomial = cyclicType;
    }

    public CyclicType(LRP lrp) {
        calculateCyclicType(lrp);
    }

    public static CyclicType compositionOf(CyclicType ct1, CyclicType ct2) {
        return compositionOf(ct1.getPolynomial(), ct2.getPolynomial());
    }

    public static CyclicType compositionOf(Polynomial p1, Polynomial p2) {
        List<Integer> a = p1.getCoefficients();
        List<Integer> b = p2.getCoefficients();
        List<Integer> resCoefficients = p1.getListWithNulls(a.size() * b.size());
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) == 0) {
                continue;
            }
            for (int j = 0; j < b.size(); j++) {
                if (b.get(j) == 0) {
                    continue;
                }
                resCoefficients.set(LeastCommonMultiple.get(i, j),
                        GreatestCommonDivisor.get(i, j) * a.get(i) * b.get(j));
            }
        }
        return new CyclicType(new Polynomial(resCoefficients, false));
    }

    public Polynomial getPolynomial() {
        return polynomial;
    }

    private void calculateCyclicType(LRP lrp) {
        if (!lrp.getCharacteristicPolynomial().isDecomposable()) {
            List<Integer> cyclicClassCoefficients;
            if (lrp.isPeriodLargest()) {
                int period = lrp.getPeriod();
                cyclicClassCoefficients = getListWithNulls(period + 1);
                cyclicClassCoefficients.set(1, 1);
                cyclicClassCoefficients.set(period, 1);
            } else {
                List<List<LRP>> cyclicClasses = lrp.getCyclicClasses();
                cyclicClassCoefficients = getListWithNulls(
                        cyclicClasses.stream().mapToInt(List::size).max().orElse(0) + 1
                );
                for (List<LRP> cyclicClass : cyclicClasses) {
                    int powerOfY = cyclicClass.size();
                    cyclicClassCoefficients.set(powerOfY, cyclicClassCoefficients.get(powerOfY) + 1);
                }
            }
            polynomial = new Polynomial(cyclicClassCoefficients, false);
        } else {
            CyclicType result;
            List<CyclicType> tempTypes = new ArrayList<>();
            lrp.getCharacteristicPolynomial().getDecomposeOfPolynomial().getDecompositionMap().forEach(
                    (polynomial, degree) -> tempTypes.add(new LRP(polynomial).getCyclicType())
            );
            result = tempTypes.get(0);
            for (int i = 1; i < tempTypes.size(); i++) {
                CyclicType tempType = tempTypes.get(i);
                result = CyclicType.compositionOf(result, tempType);
            }
            polynomial = result.getPolynomial();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        List<Integer> coefficients = polynomial.getCoefficients();

        for (int i = 1; i < coefficients.size(); i++) {
            Integer a = coefficients.get(i);
            if (a == 0) {
                continue;
            }
            result.append(a == 1 ? "" : a).append("y").append(i == 1 ? "" : "^" + i)
                    .append(i != coefficients.size() - 1 ? " + " : "");
        }
        return String.valueOf(result).isEmpty() ? "0" : String.valueOf(result);
    }
}
