package com.nastra.codechef.oct;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/OCT14/problems/PRPOTION/
 * @author nastra
 * 
 */
public class MagicalGirlAndColoredLiquidPotions {

    public static Integer solve(int m, int[] r, int[] g, int[] b) {
        MaxPQ<Integer> heapR = insertElements(r);
        MaxPQ<Integer> heapG = insertElements(g);
        MaxPQ<Integer> heapB = insertElements(b);

        while (m > 0) {
            m--;
            MaxPQ<Integer> heap = max(heapR, heapG, heapB);
            List<Integer> list = new LinkedList<>();
            while (!heap.isEmpty()) {
                list.add(heap.delMax() / 2);
            }
            for (Integer val : list) {
                heap.insert(val);
            }
        }
        return max(heapR, heapG, heapB).max();
    }

    private static MaxPQ<Integer> max(MaxPQ<Integer> a, MaxPQ<Integer> b, MaxPQ<Integer> c) {
        Integer max = Math.max(a.max(), Math.max(b.max(), c.max()));
        if (max.equals(a.max())) {
            return a;
        } else if (max.equals(b.max())) {
            return b;
        }
        return c;
    }

    private static MaxPQ<Integer> insertElements(int[] x) {
        MaxPQ<Integer> heap = new MaxPQ<>();
        for (Integer val : x) {
            heap.insert(val);
        }
        return heap;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = sc.nextInt();
        while (t > 0) {
            t--;
            int red = sc.nextInt();
            int green = sc.nextInt();
            int blue = sc.nextInt();
            int m = sc.nextInt();
            int[] r = sc.nextIntArray();
            int[] g = sc.nextIntArray();
            int[] b = sc.nextIntArray();
            out.println(solve(m, r, g, b));
        }

        out.close();
    }

    private static class Item implements Comparable<Item> {
        Integer val;
        int[] a;

        @Override
        public int compareTo(Item o) {
            return Integer.compare(this.val, o.val);
        }

        @Override
        public String toString() {
            return "Item [val=" + val + ", a=" + Arrays.toString(a) + "]";
        }
    }

    private static class MaxPQ<Key> implements Iterable<Key> {
        private Key[] pq; // store items at indices 1 to N

        private int N; // number of items on priority queue

        private Comparator<Key> comparator; // optional Comparator

        /**
         * Initializes an empty priority queue with the given initial capacity.
         * 
         * @param initCapacity
         *            the initial capacity of the priority queue
         */
        public MaxPQ(int initCapacity) {
            pq = (Key[]) new Object[initCapacity + 1];
            N = 0;
        }

        /**
         * Initializes an empty priority queue.
         */
        public MaxPQ() {
            this(1);
        }

        /**
         * Initializes an empty priority queue with the given initial capacity, using the given comparator.
         * 
         * @param initCapacity
         *            the initial capacity of the priority queue
         * @param comparator
         *            the order in which to compare the keys
         */
        public MaxPQ(int initCapacity, Comparator<Key> comparator) {
            this.comparator = comparator;
            pq = (Key[]) new Object[initCapacity + 1];
            N = 0;
        }

        /**
         * Initializes an empty priority queue using the given comparator.
         * 
         * @param comparator
         *            the order in which to compare the keys
         */
        public MaxPQ(Comparator<Key> comparator) {
            this(1, comparator);
        }

        /**
         * Initializes a priority queue from the array of keys. Takes time proportional to the number of keys, using sink-based heap construction.
         * 
         * @param keys
         *            the array of keys
         */
        public MaxPQ(Key[] keys) {
            N = keys.length;
            pq = (Key[]) new Object[keys.length + 1];
            for (int i = 0; i < N; i++)
                pq[i + 1] = keys[i];
            for (int k = N / 2; k >= 1; k--)
                sink(k);
            assert isMaxHeap();
        }

        /**
         * Is the priority queue empty?
         * 
         * @return true if the priority queue is empty; false otherwise
         */
        public boolean isEmpty() {
            return N == 0;
        }

        /**
         * Returns the number of keys on the priority queue.
         * 
         * @return the number of keys on the priority queue
         */
        public int size() {
            return N;
        }

        /**
         * Returns a largest key on the priority queue.
         * 
         * @return a largest key on the priority queue
         * @throws java.util.NoSuchElementException
         *             if the priority queue is empty
         */
        public Key max() {
            if (isEmpty())
                throw new NoSuchElementException("Priority queue underflow");
            return pq[1];
        }

        // helper function to double the size of the heap array
        private void resize(int capacity) {
            assert capacity > N;
            Key[] temp = (Key[]) new Object[capacity];
            for (int i = 1; i <= N; i++)
                temp[i] = pq[i];
            pq = temp;
        }

        /**
         * Adds a new key to the priority queue.
         * 
         * @param x
         *            the new key to add to the priority queue
         */
        public void insert(Key x) {

            // double size of array if necessary
            if (N >= pq.length - 1)
                resize(2 * pq.length);

            // add x, and percolate it up to maintain heap invariant
            pq[++N] = x;
            swim(N);
            assert isMaxHeap();
        }

        /**
         * Removes and returns a largest key on the priority queue.
         * 
         * @return a largest key on the priority queue
         * @throws java.util.NoSuchElementException
         *             if priority queue is empty.
         */
        public Key delMax() {
            if (isEmpty())
                throw new NoSuchElementException("Priority queue underflow");
            Key max = pq[1];
            exch(1, N--);
            sink(1);
            pq[N + 1] = null; // to avoid loiterig and help with garbage
            // collection
            if ((N > 0) && (N == (pq.length - 1) / 4))
                resize(pq.length / 2);
            assert isMaxHeap();
            return max;
        }

        /***********************************************************************
         * Helper functions to restore the heap invariant.
         **********************************************************************/

        private void swim(int k) {
            while (k > 1 && less(k / 2, k)) {
                exch(k, k / 2);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= N) {
                int j = 2 * k;
                if (j < N && less(j, j + 1))
                    j++;
                if (!less(k, j))
                    break;
                exch(k, j);
                k = j;
            }
        }

        /***********************************************************************
         * Helper functions for compares and swaps.
         **********************************************************************/
        private boolean less(int i, int j) {
            if (comparator == null) {
                return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
            } else {
                return comparator.compare(pq[i], pq[j]) < 0;
            }
        }

        private void exch(int i, int j) {
            Key swap = pq[i];
            pq[i] = pq[j];
            pq[j] = swap;
        }

        // is pq[1..N] a max heap?
        private boolean isMaxHeap() {
            return isMaxHeap(1);
        }

        // is subtree of pq[1..N] rooted at k a max heap?
        private boolean isMaxHeap(int k) {
            if (k > N)
                return true;
            int left = 2 * k, right = 2 * k + 1;
            if (left <= N && less(k, left))
                return false;
            if (right <= N && less(k, right))
                return false;
            return isMaxHeap(left) && isMaxHeap(right);
        }

        /***********************************************************************
         * Iterator
         **********************************************************************/

        /**
         * Returns an iterator that iterates over the keys on the priority queue in descending order. The iterator doesn't implement <tt>remove()</tt>
         * since it's optional.
         * 
         * @return an iterator that iterates over the keys in descending order
         */
        public Iterator<Key> iterator() {
            return new HeapIterator();
        }

        private class HeapIterator implements Iterator<Key> {

            // create a new pq
            private MaxPQ<Key> copy;

            // add all items to copy of heap
            // takes linear time since already in heap order so no keys move
            public HeapIterator() {
                if (comparator == null)
                    copy = new MaxPQ<Key>(size());
                else
                    copy = new MaxPQ<Key>(size(), comparator);
                for (int i = 1; i <= N; i++)
                    copy.insert(pq[i]);
            }

            public boolean hasNext() {
                return !copy.isEmpty();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Key next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return copy.delMax();
            }
        }
    }

    private static class FastScanner {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        public FastScanner(InputStream in) throws Exception {
            reader = new BufferedReader(new InputStreamReader(in));
            tokenizer = new StringTokenizer(reader.readLine().trim());
        }

        public int numTokens() throws Exception {
            if (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine().trim());
                return numTokens();
            }
            return tokenizer.countTokens();
        }

        public boolean hasNext() throws Exception {
            if (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine().trim());
                return hasNext();
            }
            return true;
        }

        public String next() throws Exception {
            if (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine().trim());
                return next();
            }
            return tokenizer.nextToken();
        }

        public double nextDouble() throws Exception {
            return Double.parseDouble(next());
        }

        public float nextFloat() throws Exception {
            return Float.parseFloat(next());
        }

        public long nextLong() throws Exception {
            return Long.parseLong(next());
        }

        public int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        public int[] nextIntArray() throws Exception {
            String[] line = reader.readLine().trim().split(" ");
            int[] out = new int[line.length];
            for (int i = 0; i < line.length; i++) {
                out[i] = Integer.valueOf(line[i]);
            }
            return out;
        }

        public double[] nextDoubleArray() throws Exception {
            String[] line = reader.readLine().trim().split(" ");
            double[] out = new double[line.length];
            for (int i = 0; i < line.length; i++) {
                out[i] = Double.valueOf(line[i]);
            }
            return out;
        }

        public Integer[] nextIntegerArray() throws Exception {
            String[] line = reader.readLine().trim().split(" ");
            Integer[] out = new Integer[line.length];
            for (int i = 0; i < line.length; i++) {
                out[i] = Integer.valueOf(line[i]);
            }
            return out;
        }

        public BigInteger[] nextBigIngtegerArray() throws Exception {
            String[] line = reader.readLine().trim().split(" ");
            BigInteger[] out = new BigInteger[line.length];
            for (int i = 0; i < line.length; i++) {
                out[i] = new BigInteger(line[i]);
            }
            return out;
        }

        public BigInteger nextBigInteger() throws Exception {
            return new BigInteger(next());
        }

        public String nextLine() throws Exception {
            return reader.readLine().trim();
        }

        public long[] nextLongArray() throws Exception {
            String[] line = reader.readLine().trim().split(" ");
            long[] out = new long[line.length];
            for (int i = 0; i < line.length; i++) {
                out[i] = Long.valueOf(line[i]);
            }
            return out;
        }
    }
}
