
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.Math;
import java.lang.Integer;

public class PercolationStats {

    private int trials;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    private double[] est;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("n must > 1! or trials must > 1");
        }
        est = new double[trials];
        this.trials = trials;
        for (int k = 0; k < trials; k++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int i = StdRandom.uniform(n) + 1;
                int j = StdRandom.uniform(n) + 1;
                perc.open(i, j);
            }
            est[k] = 1.0 * perc.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(est);
        stddev = StdStats.stddev(est);
        confidenceLo = mean - (1.96 * stddev) / Math.sqrt(trials);
        confidenceHi = mean - (1.96 * stddev) / Math.sqrt(trials);
    }

    public double mean() {
        return mean;
    }
    public double stddev() {
        return stddev;
    }
    public double confidenceLo() {
        return confidenceLo;
    }
    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        System.out.println("mean                        = " + stats.mean());
        System.out.println("stddev                      = " + stats.stddev());
        System.out.println("95% confidence interval     = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}
