package ca.rjdsilv.datastructure.queue;

public interface Queue<T> {
	void enqueue(T itme);
	T dequeue();
	boolean isEmpty();
}
