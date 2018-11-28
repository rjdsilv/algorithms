package ca.rjdsilv.assignments.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private static final double CONFIDENCE_CONST = 1.96;

	private final double[] xt;
	private final int tt;

	private boolean calcMean = false;
	private boolean calcStddev = false;
	private double mean;
	private double stddev;

	public PercolationStats(int n, int t) {
		if (n <= 0 || t <= 0) {
			throw new IllegalArgumentException("Both n and t must be greater than 0");
		}

		tt = t;
		xt = new double[t];

		for (int ct = 0; ct < t; ct++) {
			final Percolation perc = new Percolation(n);
			while (!perc.percolates()) {
				int p = StdRandom.uniform(n * n);
				perc.open((p / n + 1), (p % n + 1));
			}
			xt[ct] = ((double) perc.numberOfOpenSites()) / ((double) n * n);
		}
	}

	public static void main(String[] args) {
		if (args == null || args.length < 2) {
			throw new IllegalArgumentException("At least 2 parameters must be provided!");
		}

		final PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
				Integer.parseInt(args[1]));
		System.out.println(String.format("mean                    = %f", ps.mean()));
		System.out.println(String.format("stddev                  = %f", ps.stddev()));
		System.out.println(String.format("95%% confidence interval = [%f, %f]", ps.confidenceLo(),
				ps.confidenceHi()));
	}

	public double mean() {
		if (!calcMean) {
			calcMean = true;
			mean = StdStats.mean(xt);
		}

		return mean;
	}

	public double stddev() {
		if (!calcStddev) {
			calcStddev = true;
			stddev = StdStats.stddev(xt);
		}
		return stddev;
	}

	public double confidenceLo() {
		return mean() - (CONFIDENCE_CONST * stddev() / Math.sqrt(tt));
	}

	public double confidenceHi() {
		return mean() + (CONFIDENCE_CONST * stddev() / Math.sqrt(tt));
	}
}
