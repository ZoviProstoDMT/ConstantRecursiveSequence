package tests;

import java.util.Map;

public class SequenceTests {

    public static double getX2CriteriaValue(Map<Integer, Integer> countOfOccurrences, int sequenceSize) {
        double v = 0;
        double ps = 1.0 / countOfOccurrences.size();
        for (Map.Entry<Integer, Integer> element : countOfOccurrences.entrySet()) {
            double nps = sequenceSize * ps;
            double valueFromMap = element.getValue();
            double valueFromMapMinusNps = valueFromMap - nps;
            double doubleValueFromMapMinusNps = Math.pow(valueFromMapMinusNps, 2);
            double statElement = (doubleValueFromMapMinusNps) / nps;
            v += statElement;
        }
        return v;
    }
}
