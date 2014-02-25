package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * @see https://www.hackerrank.com/contests/feb14/challenges/board-cutting
 * @author nastra - Eduard Tudenhoefner
 * 
 */
public class CuttingBoards {
    private static BigInteger MOD = new BigInteger("1000000007");

    public static BigInteger solve(MaxPQ<BigInteger> xHeap, MaxPQ<BigInteger> yHeap) {
        BigInteger usedX = BigInteger.ONE;
        BigInteger usedY = BigInteger.ONE;
        BigInteger total = BigInteger.ZERO;
        while (true) {
            if (yHeap.isEmpty() && xHeap.isEmpty()) {
                break;
            }
            if (yHeap.isEmpty()) {
                BigInteger cost = xHeap.delMax();
                total = total.add((cost.multiply(usedY)));
                usedX = usedX.add(BigInteger.ONE);
            } else if (xHeap.isEmpty()) {
                BigInteger cost = yHeap.delMax();
                total = total.add((cost.multiply(usedX)));
                usedY = usedY.add(BigInteger.ONE);
            } else if (largerOrEquals(yHeap.max(), xHeap.max())) {
                BigInteger cost = yHeap.delMax();
                total = total.add((cost.multiply(usedX)));
                usedY = usedY.add(BigInteger.ONE);
            } else {
                BigInteger cost = xHeap.delMax();
                total = total.add((cost.multiply(usedY)));
                usedX = usedX.add(BigInteger.ONE);
            }
            total = total.mod(MOD);
        }
        return total;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int M = sc.nextInt();
            int N = sc.nextInt();
            String[] line = sc.nextLine().split(" ");
            String[] line2 = sc.nextLine().split(" ");
            MaxPQ<BigInteger> yHeap = null;
            MaxPQ<BigInteger> xHeap = null;
            yHeap = new MaxPQ<BigInteger>(M - 1);
            xHeap = new MaxPQ<BigInteger>(N - 1);
            for (String cost : line) {
                yHeap.insert(new BigInteger(cost));
            }

            for (String cost : line2) {
                xHeap.insert(new BigInteger(cost));
            }
            BigInteger result = solve(xHeap, yHeap);
            System.out.println(result.toString());
        }
    }

    static class FastScanner {

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

        public String nextLine() throws Exception {
            return reader.readLine().trim();
        }

        public BigInteger nextBigInteger() throws Exception {
            return new BigInteger(next());
        }
    }

    private static boolean largerOrEquals(BigInteger one, BigInteger two) {
        return one.compareTo(two) >= 0;
    }

    static class MaxPQ<Key> implements Iterable<Key> {

        private Key[] pq; // store items at indices 1 to N
        private int N; // number of items on priority queue
        private Comparator<Key> comparator; // optional Comparator

        /**
         * Create an empty priority queue with the given initial capacity.
         */
        public MaxPQ(int capacity) {
            pq = (Key[]) new Object[capacity + 1];
            N = 0;
        }

        /**
         * Create an empty priority queue.
         */
        public MaxPQ() {
            this(1);
        }

        /**
         * Create an empty priority queue with the given initial capacity, using the given comparator.
         */
        public MaxPQ(int initCapacity, Comparator<Key> comparator) {
            this.comparator = comparator;
            pq = (Key[]) new Object[initCapacity + 1];
            N = 0;
        }

        /**
         * Create an empty priority queue using the given comparator.
         */
        public MaxPQ(Comparator<Key> comparator) {
            this(1, comparator);
        }

        /**
         * Create a priority queue with the given items. Takes time proportional to the number of items using sink-based heap construction.
         */
        public MaxPQ(Key[] keys) {
            N = keys.length;
            pq = (Key[]) new Object[keys.length + 1];
            for (int i = 0; i < N; i++) {
                pq[i + 1] = keys[i];
            }
            for (int k = N / 2; k >= 1; k--) {
                sink(k);
            }
            assert isMaxHeap();
        }

        /**
         * Is the priority queue empty?
         */
        public boolean isEmpty() {
            return N == 0;
        }

        /**
         * Return the number of items on the priority queue.
         */
        public int size() {
            return N;
        }

        /**
         * Return the largest key on the priority queue.
         * 
         * @throws java.util.NoSuchElementException
         *             if priority queue is empty.
         */
        public Key max() {
            if (isEmpty()) {
                throw new NoSuchElementException("Priority queue underflow");
            }
            return pq[1];
        }

        // helper function to double the size of the heap array
        private void resize(int capacity) {
            assert capacity > N;
            Key[] temp = (Key[]) new Object[capacity];
            for (int i = 1; i <= N; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
        }

        /**
         * Add a new key to the priority queue.
         */
        public void insert(Key x) {

            // double size of array if necessary
            if (N >= pq.length - 1) {
                resize(2 * pq.length);
            }

            // add x, and percolate it up to maintain heap invariant
            pq[++N] = x;
            swim(N);
            assert isMaxHeap();
        }

        /**
         * Delete and return the largest key on the priority queue.
         * 
         * @throws java.util.NoSuchElementException
         *             if priority queue is empty.
         */
        public Key delMax() {
            if (isEmpty()) {
                throw new NoSuchElementException("Priority queue underflow");
            }
            Key max = pq[1];
            exch(1, N--);
            sink(1);
            pq[N + 1] = null; // to avoid loiterig and help with garbage collection
            if ((N > 0) && (N == (pq.length - 1) / 4)) {
                resize(pq.length / 2);
            }
            assert isMaxHeap();
            return max;
        }

        public Key deleteAt(int index) {
            if (isEmpty() || index < 1 || index > N) {
                throw new NoSuchElementException("Priority queue underflow");
            }
            Key tmp = pq[index];
            exch(index, N);
            N--;
            if (less(pq[index], tmp)) {
                sink(index);
            } else {
                swim(index);
            }
            assert isMaxHeap();
            return tmp;
        }

        /**
         * ********************************************************************* Helper functions to restore the heap invariant.
         * ********************************************************************
         */
        private void swim(int k) {
            while (k > 1 && less(k / 2, k)) {
                exch(k, k / 2);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= N) {
                int j = 2 * k;
                if (j < N && less(j, j + 1)) {
                    j++;
                }
                if (!less(k, j)) {
                    break;
                }
                exch(k, j);
                k = j;
            }
        }

        /**
         * ********************************************************************* Helper functions for compares and swaps.
         * ********************************************************************
         */
        private boolean less(int i, int j) {
            if (comparator == null) {
                return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
            } else {
                return comparator.compare(pq[i], pq[j]) < 0;
            }
        }

        private boolean less(Key i, Key j) {
            if (comparator == null) {
                return ((Comparable<Key>) i).compareTo(j) < 0;
            } else {
                return comparator.compare(i, j) < 0;
            }
        }

        private void exch(int i, int j) {
            Key swap = pq[i];
            pq[i] = pq[j];
            pq[j] = swap;
        }

        // is pq[1..N] a max heap?
        public boolean isMaxHeap() {
            return isMaxHeap(1);
        }

        // is subtree of pq[1..N] rooted at k a max heap?
        private boolean isMaxHeap(int k) {
            if (k > N) {
                return true;
            }
            int left = 2 * k, right = 2 * k + 1;
            if (left <= N && less(k, left)) {
                return false;
            }
            if (right <= N && less(k, right)) {
                return false;
            }
            return isMaxHeap(left) && isMaxHeap(right);
        }

        /**
         * ********************************************************************* Iterator
         * ********************************************************************
         */
        /**
         * Return an iterator that iterates over all of the keys on the priority queue in descending order.
         * <p>
         * The iterator doesn't implement <tt>remove()</tt> since it's optional.
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
                if (comparator == null) {
                    copy = new MaxPQ<Key>(size());
                } else {
                    copy = new MaxPQ<Key>(size(), comparator);
                }
                for (int i = 1; i <= N; i++) {
                    copy.insert(pq[i]);
                }
            }

            public boolean hasNext() {
                return !copy.isEmpty();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Key next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return copy.delMax();
            }
        }
    }

}
