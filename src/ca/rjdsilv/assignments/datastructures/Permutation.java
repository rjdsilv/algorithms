package ca.rjdsilv.assignments.datastructures;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        final RandomizedQueue<String> rq = new RandomizedQueue<>();
        final int k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        int i = 0;
        final Iterator<String> it = rq.iterator();
        while (it.hasNext() && i < k) {
            System.out.println(it.next());
            i++;
        }
    }
}
