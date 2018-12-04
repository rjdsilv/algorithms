package ca.rjdsilv.datastructure.queue;

import ca.rjdsilv.datastructure.stack.LinkedListStack;
import ca.rjdsilv.datastructure.stack.Stack;
import edu.princeton.cs.algs4.Stopwatch;

public class LinkedListQueue<T> extends AbstractQueue<T> {
	private Node<T> first = null;
	private Node<T> last = null;

	public static <T> Queue<T> newInstance() {
		return new LinkedListQueue<T>();
	}

	public static void main(String[] args) {
		final int trials = 50;
		final Queue<Long> queue = LinkedListQueue.newInstance();
		double totalEnqueue  = 0.0;
		double totalDequeue   = 0.0;
		double totalTotal = 0.0;

		for (int n = 0; n < trials; n++) {
			final Stopwatch totalTime = new Stopwatch();

			final Stopwatch pushTime = new Stopwatch();
			for (long i = 0; i < 5_000_000L; i++) queue.enqueue(i);
			totalEnqueue += pushTime.elapsedTime();

			final Stopwatch popTime = new Stopwatch();
			for (long i = 0; i < 5_000_000L; i++) queue.dequeue();
			totalDequeue += popTime.elapsedTime();

			totalTotal += totalTime.elapsedTime();
		}

		System.out.println(String.format("Linked List Enqueue => Total time: %.5f, Average time: %.5f", totalEnqueue , totalEnqueue  / trials));
		System.out.println(String.format("Linked List Dequeue => Total time: %.5f, Average time: %.5f", totalDequeue  , totalDequeue   / trials));
		System.out.println(String.format("Linked List Total   => Total time: %.5f, Average time: %.5f", totalTotal, totalTotal / trials));
	}

	private LinkedListQueue() {
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
	public void enqueue(T item) {
		if (item == null) throw new IllegalArgumentException("Item can't be null!");

		final Node<T> oldLast = first;
		last = new Node<>(item);
		last.next = null;
		if (isEmpty()) first = last;
		else           oldLast.next = last;
	}

	@Override
	public T dequeue() {
		if (isEmpty()) throw new IllegalStateException("The stack is currently empty!");

		final T item = first.item;
		first = first.next;
		if(isEmpty()) last = null;
		return item;
	}

	@Override
	public boolean isEmpty() {
		return first == null;
	}
}
