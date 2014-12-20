package my.pyramid;

public class Weight5PlusRecursion extends WeightAbstract {

    public double getHumanEdgeWeight(int n, int m) {
        // use symmetry
        if (m > n - m) {
            m = n - m;
        }

        if (n > 0) {
            if (m == 0) {
                return ((Math.pow(2, n) - 1) / Math.pow(2, n)) * weightOfOneElement;
            } else {
                if (m == 1) {
                    return (((3 * Math.pow(2, n)) - (n + 4)) / Math.pow(2, n)) * weightOfOneElement;
                } else {
                    if (m == 2) {
                        return (((5 * Math.pow(2, n)) - (double) (Math.pow(n, 2) + n * 7 + 16) / 2) / Math.pow(2, n)) * weightOfOneElement;
                    } else {
                        if (m == 3) {
                            return (((7 * Math.pow(2, n)) - (double) (Math.pow(n, 3) + Math.pow(n, 2) * 9 + n * 38 + 72) / 6) / Math.pow(2, n)) * weightOfOneElement;
                        } else {
                            if (m == 4) {
                                return (((9 * Math.pow(2, n)) - (double) (Math.pow(n, 4) + Math.pow(n, 3) * 10 + Math.pow(n, 2) * 59 + n * 218 + 384) / 24) / Math.pow(2, n)) * weightOfOneElement;
                            } else {
                                if (m == 5) {
                                    return (((11 * Math.pow(2, n)) - (double) (Math.pow(n, 5) + Math.pow(n, 4) * 10 + Math.pow(n, 3) * 75 + Math.pow(n, 2) * 410 + n * 1424 + 2400) / 120) / Math.pow(2, n)) * weightOfOneElement;
                                } else {
                                    return (getHumanEdgeWeight(n - 1, m - 1) + getHumanEdgeWeight(n - 1, m)) / 2 + weightOfOneElement;
                                }
                            }
                        }
                    }
                }

            }
        } else {
            return 0;
        }
    }

}
