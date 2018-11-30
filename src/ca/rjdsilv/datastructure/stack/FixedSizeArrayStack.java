package ca.rjdsilv.datastructure.stack;

import edu.princeton.cs.algs4.Stopwatch;

import java.lang.reflect.Array;

public final class FixedSizeArrayStack<T> extends AbstractStack<T> {
	private int n = 0;
	private T[] array;

	public static <T> Stack<T> newInstance(Class<T> clazz, int capacity) {
		return new FixedSizeArrayStack<>(clazz, capacity);
	}

	public static void main(String[] args) {
		final int trials = 50;
		final Stack<Long> stack = FixedSizeArrayStack.newInstance(Long.class, 5_000_000);
		double totalPush  = 0.0;
		double totalPop   = 0.0;
		double totalTotal = 0.0;

		for (int n = 0; n < trials; n++) {
			final Stopwatch totalTime = new Stopwatch();

			final Stopwatch pushTime = new Stopwatch();
			for (long i = 0; i < 5_000_000L; i++) stack.push(i);
			totalPush += pushTime.elapsedTime();

			final Stopwatch popTime = new Stopwatch();
			for (long i = 0; i < 5_000_000L; i++) stack.pop();
			totalPop += popTime.elapsedTime();

			totalTotal += totalTime.elapsedTime();
		}

		System.out.println(String.format("Fixed Array Push  => Total time: %.5f, Average time: %.5f", totalPush , totalPush  / trials));
		System.out.println(String.format("Fixed Array Pop   => Total time: %.5f, Average time: %.5f", totalPop  , totalPop   / trials));
		System.out.println(String.format("Fixed Array Total => Total time: %.5f, Average time: %.5f", totalTotal, totalTotal / trials));
	}

	@SuppressWarnings("unchecked")
	private FixedSizeArrayStack(Class<T> clazz, int capacity) {
		this.array = (T[]) Array.newInstance(clazz, capacity);
	}

	@Override
	public void push(T item) {
		if (item == null) throw new IllegalArgumentException("Item can't be null!");
		if (n == array.length) throw new IllegalStateException("Stack is full!");

		array[n++] = item;
	}

	@Override
	public T pop() {
		if (isEmpty()) {
			throw new IllegalStateException("Stack is empty!");
		}

		T item = array[--n];
		array[n] = null;
		return item;
	}

	@Override
	public boolean isEmpty() {
		return n == 0;
	}
}
