package my.pyramid;

class WeightStrategy {

    private Weight weight;

    public WeightStrategy() {
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public void validate(int n, int m) {
        weight.validate(n, m);
    }

    public double getHumanEdgeWeight(int n, int m) {
        return weight.getHumanEdgeWeight(n, m);
    }

}