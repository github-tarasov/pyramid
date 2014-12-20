package my.pyramid;

public class WeightSimple extends WeightAbstract {

    public double getHumanEdgeWeight(int n, int m) {
        // use symmetry
        if (m > n - m) {
            m = n - m;
        }

        if (n > 0) {
            if (m == 0) {
                return ((Math.pow(2, n) - 1) / Math.pow(2, n)) * weightOfOneElement;
            }
            return (getHumanEdgeWeight(n - 1, m - 1) + getHumanEdgeWeight(n - 1, m)) / 2 + weightOfOneElement;
        } else {
            return 0;
        }
    }

}
