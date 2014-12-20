package my.pyramid;

public interface Weight {

    public void validate(int n, int m) throws IllegalArgumentException;

    /*
    @param n - level (0, 1, 2, 3 .. )
    @param m - index (0, 1 .. n)
     */
    public double getHumanEdgeWeight(int n, int m);

}
