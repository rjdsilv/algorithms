package ca.rjdsilv.assignments.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size = 0;

    public Deque() {
    }

    public static void main(String[] args) {
        final Deque<String> dq = new Deque<>();
        dq.addLast("AA");
        dq.addLast("BB");
        dq.addLast("CC");
        dq.addLast("DD");
        dq.addLast("EE");
        dq.addLast("FF");
        dq.addLast("GG");
        dq.addLast("HH");

        for (String aDq : dq) {
            System.out.print("IT = " + aDq + ", ");
        }
        System.out.println();

        while (!dq.isEmpty()) {
            System.out.print("RL = " + dq.removeLast() + ", ");
        }
    }

    private class Node<K> {
        private final K item;
        private Node<K> next;
        private Node<K> prev;

        private Node(K item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }

    private class FrontToEndIterator implements Iterator<Item> {
        private Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return !isEmpty() && current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("There are no more elements to be returned!");

            final Item item = current.item;
            current = current.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported!");
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (null == item) throw new IllegalArgumentException("Item can't be null!");

        if (isEmpty()) {
            first = new Node<>(item);
            last = first;
        }
        else {
            first.prev = new Node<>(item);
            first.prev.next = first;
            first = first.prev;
        }
        size++;
    }

    public void addLast(Item item) {
        if (null == item) throw new IllegalArgumentException("Item can't be null!");

        if (isEmpty()) {
            last = new Node<>(item);
            first = last;
        }
        else {
            last.next = new Node<>(item);
            last.next.prev = last;
            last = last.next;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("The queue is empty!");

        size--;
        final Item item = first.item;
        first = first.next;
        if (!isEmpty()) {
            first.prev.next = null;
            first.prev = null;
        } else {
            last = null;
        }

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("The queue is empty!");

        size--;
        final Item item = last.item;
        last = last.prev;
        if (!isEmpty()) {
            last.next.prev = null;
            last.next = null;
        } else {
            first = null;
        }

        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new FrontToEndIterator();
    }
}
