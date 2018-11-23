package ca.rjdsilv.union_find;

abstract class AbstractQuickUnionUF extends AbstractUnionFind {
	AbstractQuickUnionUF(int n) {
		super(n);
	}

	@Override
	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}

	int root(int i) {
		while (id[i] != i) {
			i = id[i];
		}

		return i;
	}
}
