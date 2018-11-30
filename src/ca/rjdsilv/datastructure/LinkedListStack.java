package ca.rjdsilv.datastructure;

import edu.princeton.cs.algs4.Stopwatch;

public final class LinkedListStack<T> extends AbstractStack<T> {
	private Node<T> first = null;

	public static <T> Stack<T> newInstance() {
		return new LinkedListStack<T>();
	}

	public static void main(String[] args) {
		final int trials = 50;
		final Stack<Long> stack = LinkedListStack.newInstance();
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

		System.out.println(String.format("Linked List Push  => Total time: %.5f, Average time: %.5f", totalPush , totalPush  / trials));
		System.out.println(String.format("Linked List Pop   => Total time: %.5f, Average time: %.5f", totalPop  , totalPop   / trials));
		System.out.println(String.format("Linked List Total => Total time: %.5f, Average time: %.5f", totalTotal, totalTotal / trials));
	}

	private LinkedListStack() {
	}

	private class Node<K> {
		K item;
		Node<K> next;

		private Node(K item) {
			this.item = item;
			next = null;
		}
	}

	@Override
	public void push(T item) {
		if (item == null) throw new IllegalArgumentException("Item can't be null!");

		final Node<T> oldFirst = first;
		first = new Node<>(item);
		first.next = oldFirst;
	}

	@Override
	public T pop() {
		if (isEmpty()) {
			throw new IllegalStateException("The stack is currently empty!");
		}

		final T item = first.item;
		first = first.next;
		return item;
	}

	@Override
	public boolean isEmpty() {
		return first == null;
	}
}
