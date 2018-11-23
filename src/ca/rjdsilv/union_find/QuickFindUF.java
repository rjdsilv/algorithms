package ca.rjdsilv.union_find;

public class QuickFindUF extends AbstractUnionFind {
	public QuickFindUF(int n) {
		super(n);
	}

	@Override
	public void union(int p, int q) {
		if (id[p] != id[q]) {
			int pId = id[p];
			int qId = id[q];

			for (int i = 0; i < id.length; i++) {
				if (id[i] == pId) {
					id[i] = qId;
				}
			}
		}
	}

	@Override
	public boolean connected(int p, int q) {
		return id[p] == id[q];
	}

	@Override
	public int find(int p) {
		return 0;
	}
}
