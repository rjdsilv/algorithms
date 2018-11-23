package ca.rjdsilv.union_find;

public class QuickUnionUF extends AbstractQuickUnionUF {
	public QuickUnionUF(int n) {
		super(n);
	}

	@Override
	public void union(int p, int q) {
		id[root(p)] = root(q);
	}

	@Override
	public int find(int p) {
		return 0;
	}
}
