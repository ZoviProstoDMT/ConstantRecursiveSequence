package entities;

public class Field {

    protected int modF = 5;

    public Field(int modF) {
        this.modF = modF;
    }

    public Field() {
    }

    public int getModF() {
        return modF;
    }

    public void setModF(int modF) {
        this.modF = modF;
    }
}
