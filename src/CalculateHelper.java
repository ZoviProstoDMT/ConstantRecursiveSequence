import entities.LRP;
import entities.RecurrentRelation;

import java.util.Arrays;
import java.util.List;

public class CalculateHelper {
    public static void main(String[] args) {
        RecurrentRelation recurrentRelation = new RecurrentRelation(3, 3, Arrays.asList(1, 0, 1));
        System.out.println("Рекуррентное соотношение: " + recurrentRelation);
        LRP lrp = new LRP(recurrentRelation, Arrays.asList(0, 0, 1));
        List<Integer> lrpSequence = lrp.getSequence(20);
        System.out.println("Последовательность размера " + lrpSequence.size() + ": " + lrpSequence);
    }
}
