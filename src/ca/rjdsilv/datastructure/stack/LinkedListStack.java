package ca.rjdsilv.datastructure.stack;

public final class LinkedListStack<T> extends AbstractStack<T> {
	private Node<T> first = null;

	public static <T> Stack<T> newInstance() {
		return new LinkedListStack<T>();
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
