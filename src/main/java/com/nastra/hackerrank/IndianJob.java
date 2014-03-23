package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class IndianJob {

    static int MAX_N = 100;

    static int findMax(int A[], int n, int k) {
        int[][] M = new int[MAX_N + 1][MAX_N + 1];
        int[] p = new int[MAX_N + 1];
        for (int i = 1; i <= n; i++)
            p[i] = p[i - 1] + A[i - 1];

        for (int i = 1; i <= n; i++)
            M[i][1] = p[i];
        for (int j = 1; j <= k; j++)
            M[1][j] = A[0];

        for (int i = 2; i <= k; i++) {
            for (int j = 2; j <= n; j++) {
                int best = Integer.MAX_VALUE;
                for (int x = 1; x <= j; x++) {
                    int cost = Math.max(M[x][i - 1], p[j] - p[x]);
                    best = Math.min(best, cost);
                }
                M[j][i] = best;
            }
        }
        return M[n][k];
    }

    static int findMaxTwo(int A[], int n, int k) {
        int[][] M = new int[MAX_N + 2][MAX_N + 2];
        int[] p = new int[MAX_N + 2];
        for (int i = 1; i <= n; i++)
            p[i] = p[i - 1] + A[i - 1];

        for (int i = 1; i <= n; i++)
            M[i][1] = p[i];
        for (int j = 1; j <= k; j++)
            M[1][j] = A[0];

        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= k; j++) {
                int best = Integer.MAX_VALUE;
                for (int x = 1; x <= (i - 1); x++) {
                    int cost = Math.max(M[x][j - 1], p[i] - p[x]);
                    best = Math.min(best, cost);
                }
                M[i][j] = best;
            }
        }
        return Math.max(M[n][k], M[n][k + 1]);
    }

    private static boolean solve(Integer[] times, int N, int G) {
        if (times.length >= 2) {
            MaxPQ<Integer> heap = new MaxPQ<Integer>(times);
            while (heap.size() > 1) {
                int one = heap.delMax();
                int two = heap.delMax();
                heap.insert(one - two);
            }
            System.out.println(heap.delMax());
            return true;
        }
        return times[0] <= G;
    }

    public static boolean possible(Integer[] times, int N, int G) {
        if (times.length >= 2) {
            MaxPQ<Integer> heap = new MaxPQ<Integer>(times);
            int a = heap.delMax();
            int b = heap.delMax();
            int max = a;
            // int min = b;
            while (!heap.isEmpty()) {
                if (a == max) {
                    b += heap.delMax();
                } else if (b == max) {
                    a += heap.delMax();
                }
                // min = Math.min(a, b);
                max = Math.max(a, b);
            }
            return max <= G;
        }
        return times[0] <= G;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int T = sc.nextInt();
        while (T > 0) {
            T--;
            int[] line = sc.nextIntArray();
            int N = line[0];
            int G = line[1];
            // Integer[] times = sc.nextIntegerArray();
            int[] times = sc.nextIntArray();
            // int maxElement = max(times);
            // if (maxElement > G) {
            // System.out.println("NO");
            // // long res = max(times) * N / 2;
            // // if (res <= G) {
            // // System.out.println("YES");
            // continue;
            // }
            // } else
            // int max = findMax(times, 2, 2);
            // System.out.println(max);
            if (times.length == 1) {
                if (times[0] <= G) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
                continue;
            }
            // System.out.println(findMax(times, 2, 1));
            int max = 0;
            if (N % 2 != 0) {
                max = findMaxTwo(times, 2, 2);
            } else {
                max = findMaxTwo(times, 2, 1);
            }
            // System.out.println(max);
            if (max <= G) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
            // System.out.println(max);
            // if (solve(times, N, G)) {
            // // if (possible(times, N, G)) {
            // System.out.println("YES");
            // } else {
            // System.out.println("NO");
            // }
        }
    }

    private static int max(int[] a) {
        int max = Integer.MIN_VALUE;
        for (int val : a) {
            if (val > max) {
                max = val;
            }
        }
        return max;
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

        public Long[] nextLongArray() throws Exception {
            String[] line = reader.readLine().trim().split(" ");
            Long[] out = new Long[line.length];
            for (int i = 0; i < line.length; i++) {
                out[i] = Long.valueOf(line[i]);
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
    }

    private static class MaxPQ<Key> implements Iterable<Key> {

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
