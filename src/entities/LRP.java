package entities;

import java.util.ArrayList;
import java.util.List;

public class LRP {

    private RecurrentRelation recurrentRelation;
    private List<Integer> u0;
    private List<Integer> tempU0;
    private List<Integer> sequence;

    public LRP(RecurrentRelation recurrentRelation, List<Integer> u0) {
        this.recurrentRelation = recurrentRelation;
        this.u0 = u0;
    }

    public List<Integer> getU0() {
        return u0;
    }

    public void setU0(List<Integer> u0) {
        this.u0 = u0;
    }

    public RecurrentRelation getRecurrentRelation() {
        return recurrentRelation;
    }

    public void setRecurrentRelation(RecurrentRelation recurrentRelation) {
        this.recurrentRelation = recurrentRelation;
    }

    public List<Integer> getTempU0() {
        return tempU0;
    }

    public void setTempU0(List<Integer> tempU0) {
        this.tempU0 = tempU0;
    }

    public List<Integer> getSequence(int numberOfMembers) {
        calculateSequence(numberOfMembers);
        return sequence;
    }

    public void setSequence(List<Integer> sequence) {
        this.sequence = sequence;
    }

    private void calculateSequence(int numberOfMembers) {
        List<Integer> resultSequence = new ArrayList<>(u0);
        tempU0 = new ArrayList<>(u0);
        for (int i = 0; i < numberOfMembers - u0.size(); i++) {
            int nextMemberOfSequence = getNextMemberOfSequence(recurrentRelation, tempU0);
            makeALeftShiftForSequence(tempU0, nextMemberOfSequence);
            resultSequence.add(nextMemberOfSequence);
        }
        setSequence(resultSequence);
    }

    private int getNextMemberOfSequence(RecurrentRelation recurrentRelation, List<Integer> u0) {
        int result = 0;
        int modF = recurrentRelation.getModF();
        for (int i = 0; i < u0.size(); i++) {
            int member = recurrentRelation.getCoefficients().get(i) * u0.get(i);
            result += member % modF;
            result %= modF;
        }
        return result;
    }

    private void makeALeftShiftForSequence(List<Integer> tempU0, int nextMember) {
        tempU0.add(nextMember);
        List<Integer> newTempU0 = tempU0.subList(1, tempU0.size());
        setTempU0(newTempU0);
    }
}
