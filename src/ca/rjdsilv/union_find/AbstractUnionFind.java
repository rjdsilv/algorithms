package ca.rjdsilv.union_find;

abstract class AbstractUnionFind implements UnionFind {
	int[] id;
	int count;

	AbstractUnionFind(int n) {
		id = new int[n];

		for (int i = 0; i < n; i++) {
			id[i] = i;
		}

		count = n;
	}

	@Override
	public int count() {
		return count;
	}
}
