package ca.rjdsilv.union_find;

abstract class AbstractUnionFind implements UnionFind {
	int[] id;

	AbstractUnionFind(int n) {
		id = new int[n];

		for (int i = 0; i < n; i++) {
			id[i] = i;
		}
	}

	@Override
	public int count() {
		return id.length;
	}
}
