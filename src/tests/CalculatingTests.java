package tests;

import entities.LRP;
import entities.RecurrentRelation;

import java.util.Arrays;
import java.util.List;

public class CalculatingTests {

    public CalculatingTests() {
        if (calculateGeneratorTest()) {
            System.out.println("===== TEST 01 =====\nВычисление генератора прошло проверку\n===================");
        } else {
            System.err.println("===== TEST 01 =====\nВычисление генератора не прошло проверку\n===================");
        }
    }

    private boolean calculateGeneratorTest() {
        LRP lrp1 = new LRP(new RecurrentRelation(5, Arrays.asList(-1, 1, -1)), Arrays.asList(3, 3, 1));
        List<Integer> genSeq1 = lrp1.getImpulse().multiply(lrp1.getGenerator(), 20);
        List<Integer> seq1 = lrp1.getSequence(20);
        boolean test1 = genSeq1.equals(seq1);

        LRP lrp2 = new LRP(new RecurrentRelation(4, Arrays.asList(-1, 3, -1, 2)), Arrays.asList(3, -3, 1, 2));
        List<Integer> genSeq2 = lrp2.getImpulse().multiply(lrp2.getGenerator(), 20);
        List<Integer> seq2 = lrp2.getSequence(20);
        boolean test2 = genSeq2.equals(seq2);

        LRP lrp3 = new LRP(new RecurrentRelation(11, Arrays.asList(-1, 3, -1, 2, 11, 0, 2)), Arrays.asList(3, -3, 1, 2, 3, 4, 5));
        List<Integer> genSeq3 = lrp3.getImpulse().multiply(lrp3.getGenerator(), 20);
        List<Integer> seq3 = lrp3.getSequence(20);
        boolean test3 = genSeq3.equals(seq3);

        return test1 && test2 && test3;
    }
}
