package ca.rjdsilv.union_find;

public class QuickUnionWeightedPathCompressionUF extends QuickUnionWeightedUF {
	public QuickUnionWeightedPathCompressionUF(int n) {
		super(n);
	}

	@Override
	int root(int i) {
		while (id[i] != i) {
			id[i] = id[id[i]];
			i = id[i];
		}

		return i;
	}
}
