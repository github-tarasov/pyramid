package my.pyramid;

public abstract class WeightAbstract implements Weight {

    protected int weightOfOneElement;

    public int getWeightOfOneElement() {
        return weightOfOneElement;
    }

    public void setWeightOfOneElement(int weightOfOneElement) {
        if (weightOfOneElement > 0) {
            this.weightOfOneElement = weightOfOneElement;
        } else {
            throw new IllegalArgumentException("Illegal weight");
        }
    }

    public void validate(int n, int m) throws IllegalArgumentException {
        if (n < 0 || m < 0 || m > n) {
            throw new IllegalArgumentException("Illegal parameters");
        }
    }

    public abstract double getHumanEdgeWeight(int n, int m);

}
