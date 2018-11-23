package ca.rjdsilv.union_find;

public class QuickUnionWeightedUF extends AbstractQuickUnionUF {
	int[] sz;

	public QuickUnionWeightedUF(int n) {
		super(n);
		sz = new int[n];

		// All trees have sz 1 on the beginning.
		for (int i = 0; i < n; i++) {
			sz[i] = 1;
		}
	}

	@Override
	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);

		if (i != j) {
			if (sz[i] < sz[j]) {
				id[i] = j;
				sz[j] += sz[i];
			} else {
				id[j] = i;
				sz[i] += sz[j];
			}
		}
	}

	@Override
	public int find(int p) {
		return 0;
	}
}
