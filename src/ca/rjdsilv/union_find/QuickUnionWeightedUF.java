package ca.rjdsilv.union_find;

public class QuickUnionWeightedUF extends AbstractQuickUnionUF {
	private int[] sz;

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
		int rp = root(p);
		int rq = root(q);

		if (rp != rq) {
			if (sz[rp] < sz[rq]) {
				id[rp] = rq;
				sz[rq] += sz[rp];
			} else {
				id[rq] = rp;
				sz[rp] += sz[rq];
			}
		}
	}

	@Override
	public int find(int p) {
		return root(p);
	}
}
