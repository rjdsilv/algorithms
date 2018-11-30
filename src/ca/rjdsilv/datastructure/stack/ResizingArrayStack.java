package ca.rjdsilv.datastructure.stack;

import edu.princeton.cs.algs4.Stopwatch;

import java.lang.reflect.Array;

public final class ResizingArrayStack<T> extends AbstractStack<T> {
	private static final int INIT_CAPACITY = 1;

	private int n = 0;
	private T[] array;
	private final Class<T> clazz;

	public static <T> Stack<T> newInstance(Class<T> clazz) {
		return new ResizingArrayStack<>(clazz);
	}

	public static void main(String[] args) {
		final int trials = 50;
		final Stack<Long> stack = ResizingArrayStack.newInstance(Long.class);
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

		System.out.println(String.format("Resizing Array Push  => Total time: %.5f, Average time: %.5f", totalPush , totalPush  / trials));
		System.out.println(String.format("Resizing Array Pop   => Total time: %.5f, Average time: %.5f", totalPop  , totalPop   / trials));
		System.out.println(String.format("Resizing Array Total => Total time: %.5f, Average time: %.5f", totalTotal, totalTotal / trials));
	}

	@SuppressWarnings("unchecked")
	private ResizingArrayStack(Class<T> clazz) {
		this.array = (T[]) Array.newInstance(clazz, INIT_CAPACITY);
		this.clazz = clazz;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void push(T item) {
		if (item == null) throw new IllegalArgumentException("Item can't be null!");

		resize();
		array[n++] = item;
	}

	@Override
	public T pop() {
		if (isEmpty()) throw new IllegalStateException("Stack is empty!");

		resize();
		T item = array[--n];
		array[n] = null;
		return item;
	}

	@Override
	public boolean isEmpty() {
		return n == 0;
	}

	@SuppressWarnings("unchecked")
	private void resize() {
		if (n == array.length) {
			final T[] newArray = (T[]) Array.newInstance(clazz, array.length * 2);
			System.arraycopy(array, 0, newArray, 0, n);
			array = newArray;
		} else if (!isEmpty() && (n == array.length / 4)) {
			final T[] newArray = (T[]) Array.newInstance(clazz, array.length / 2);
			System.arraycopy(array, 0, newArray, 0, n);
			array = newArray;
		}
	}
}
