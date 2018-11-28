package ca.rjdsilv.assignments.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private static final byte OPEN_MASK = (byte) 0b00000001;
	private static final byte FULL_MASK = (byte) 0b00000010;
	private static final byte CNTT_MASK = (byte) 0b00000100;
	private static final byte CNTB_MASK = (byte) 0b00001000;

	private final int nn;
	private final WeightedQuickUnionUF wquf;
	private int openSites = 0;
	private boolean percolates;
	private byte[][] statusMatrix;

	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("The size n must be greater than 0!");
		}

		// Create the quick union find with the 2 dummy nodes.
		nn = n;
		wquf = new WeightedQuickUnionUF(nn * nn);

		// Creates and initializes the status matrix.
		statusMatrix = new byte[n][n];
	}

	public static void main(String[] args) {
		final Percolation p = new Percolation(2);
		int count = 1;
		p.open(1, 1);
		System.out.println(String.format("%03d) PERCOLATES = %b", count++, p.percolates()));
		p.open(2, 2);
		System.out.println(String.format("%03d) PERCOLATES = %b", count, p.percolates()));
	}

	public void open(int row, int col) {
		if (!isOpen(row, col)) {
			final int r = row - 1; // Rearrange the row index.
			final int c = col - 1; // Rearrange the col index.

			// The current node is marked as opened.
			statusMatrix[r][c] |= OPEN_MASK;
			openSites++;

			if (r == 0) {
				// The current node is connected to the top. Mark it and as full.
				statusMatrix[r][c] |= CNTT_MASK | FULL_MASK;
			}
			if (r == nn - 1) {
				// The current node is connected to the bottom. Mark it.
				statusMatrix[r][c] |= CNTB_MASK;
			}

			int p = ufIdx(r, c);
			int tr = -1;
			int br = -1;
			int rr = -1;
			int lr = -1;

			// Get all the existing neighbours.
			if (hasTopNbr(r)) {
				statusMatrix[r][c] |= statusMatrix[r - 1][c];
				if (isOpen((row - 1), col)) {
					final int q = ufIdx((r - 1), c);
					tr = wquf.find(q);
					wquf.union(p, q);
				}
			}
			if (hasBtmNbr(r)) {
				statusMatrix[r][c] |= statusMatrix[r + 1][c];
				if (isOpen((row + 1), col)) {
					final int q = ufIdx((r + 1), c);
					br = wquf.find(q);
					wquf.union(p, q);
				}
			}
			if (hasRgtNbr(c)) {
				statusMatrix[r][c] |= statusMatrix[r][c + 1];
				if (isOpen(row, (col + 1))) {
					final int q = ufIdx(r, (c + 1));
					rr = wquf.find(q);
					wquf.union(p, q);
				}
			}
			if (hasLftNbr(c)) {
				statusMatrix[r][c] |= statusMatrix[r][c - 1];
				if (isOpen(row, (col - 1))) {
					final int q = ufIdx(r, (c - 1));
					lr = wquf.find(q);
					wquf.union(p, q);
				}
			}

			// Gets the current root for the current node after the unions. Update its root.
			final int pRoot = wquf.find(p);
			updateRootStatus(pRoot, tr, br, rr, lr, r, c);
			if (!percolates) {
				percolates = percolates(statusMatrix[pRoot / nn][pRoot % nn]);
			}
		}
	}

	public boolean isOpen(int row, int col) {
		validateIndexes(row, col);
		return (OPEN_MASK & statusMatrix[row - 1][col - 1]) == OPEN_MASK;
	}

	public boolean isFull(int row, int col) {
		if (isOpen(row, col)) {
			final int p = wquf.find(ufIdx(row - 1, col - 1));
			return (statusMatrix[p / nn][p % nn] & FULL_MASK) == FULL_MASK;
		}

		return false;
	}

	public int numberOfOpenSites() {
		return openSites;
	}

	public boolean percolates() {
		return percolates;
	}

	private boolean hasTopNbr(int row) {
		return row > 0;
	}

	private boolean hasBtmNbr(int row) {
		return row < (nn - 1);
	}

	private boolean hasRgtNbr(int col) {
		return col < (nn - 1);
	}

	private boolean hasLftNbr(int col) {
		return col > 0;
	}

	private int ufIdx(int row, int col) {
		return row * nn + col;
	}

	private void validateIndexes(int row, int col) {
		if ((row < 1) || (row > nn) || (col < 1) || (col > nn)) {
			throw new IllegalArgumentException(
					String.format("Row and Column must be between 1 and %d", nn));
		}
	}

	private void updateRootStatus(int pRoot, int tr, int br, int rr, int lr, int row, int col) {
		statusMatrix[pRoot / nn][pRoot % nn] |= statusMatrix[row][col];
		if (tr >= 0) statusMatrix[pRoot / nn][pRoot % nn] |= statusMatrix[tr / nn][tr % nn];
		if (br >= 0) statusMatrix[pRoot / nn][pRoot % nn] |= statusMatrix[br / nn][br % nn];
		if (rr >= 0) statusMatrix[pRoot / nn][pRoot % nn] |= statusMatrix[rr / nn][rr % nn];
		if (lr >= 0) statusMatrix[pRoot / nn][pRoot % nn] |= statusMatrix[lr / nn][lr % nn];
	}

	private boolean isCntt(byte status) {
		return ((status & CNTT_MASK) == CNTT_MASK);
	}

	private boolean isCntb(byte status) {
		return ((status & CNTB_MASK) == CNTB_MASK);
	}

	private boolean percolates(byte status) {
		return isCntb(status) && isCntt(status);
	}
}