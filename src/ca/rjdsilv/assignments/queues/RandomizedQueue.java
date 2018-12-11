package ca.rjdsilv.assignments.queues;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size = 0;

    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }

    public static void main(String[] args) {
        final RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 8192; i++) {
            rq.enqueue(i);
        }

        int s = rq.size();
        for (int i = 0; i < s - 1; i++) {
            rq.dequeue();
        }

        System.out.println();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int current = 0;
        private int[] iterator;

        private RandomizedIterator() {
            if (!isEmpty()) {
                iterator = new int[size];
                for (int i = 0; i < size; i++) iterator[i] = i;
                shuffle();
            }
        }

        @Override
        public boolean hasNext() {
            return !isEmpty() && null != iterator && current < iterator.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("There are no more elements to be returned!");

            return array[iterator[current++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported!");
        }

        public void shuffle() {
            int n = iterator.length;
            while (n > 1) {
                int k = StdRandom.uniform(n--);
                int temp = iterator[n];
                iterator[n] = iterator[k];
                iterator[k] = temp;
            }
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (null == item) throw new IllegalArgumentException("Item can't be null!");

        resize();
        array[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("The queue is empty!");

        resize();
        final int i = StdRandom.uniform(size);
        final Item item = array[i];
        array[i] = array[--size];
        array[size] = null;
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("The queue is empty!");

        return array[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private void resize() {
        if (size == array.length) {
            final Item[] newArray = (Item[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        } else if (!isEmpty() && (size == array.length / 4)) {
            final Item[] newArray = (Item[]) new Object[array.length / 2];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }
}
