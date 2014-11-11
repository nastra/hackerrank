package com.nastra.codechef.nov;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/NOV14/problems/CHEFSEG
 * @author nastra
 * 
 */
public class ChefAndSegmentGame {

    public static double solve(double x, BigInteger k) {
        MaxPQ<Interval> heap = new MaxPQ<>(Interval.LENGTH_ORDER);
        BigInteger count = BigInteger.ZERO;
        heap.insert(new Interval(0.0, x));
        Interval one = null;
        Interval two = null;
        while (count.compareTo(k) < 0) {
            count = count.add(BigInteger.ONE);
            Interval in = heap.delMax();
            double mid = in.left + ((in.right - in.left) / 2.0);
            one = new Interval(in.left, mid);
            heap.insert(one);
            two = new Interval(mid, in.right);
            heap.insert(two);
        }
        if (k.mod(BigInteger.valueOf(2L)).compareTo(BigInteger.ZERO) == 0) {
            return two.left;
        }
        return one.right;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = sc.nextInt();
        while (t > 0) {
            t--;
            double x = sc.nextDouble();
            BigInteger k = sc.nextBigInteger();
            out.println(String.format("%.9f", solve(x, k)).replace(',', '.'));
        }

        out.close();
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

    private static class Interval {

        /**
         * Compares two intervals by left endpoint.
         */
        public static final Comparator<Interval> LEFT_ENDPOINT_ORDER = new LeftComparator();

        /**
         * Compares two intervals by right endpoint.
         */
        public static final Comparator<Interval> RIGHT_ENDPOINT_ORDER = new RightComparator();

        /**
         * Compares two intervals by length.
         */
        public static final Comparator<Interval> LENGTH_ORDER = new LengthComparator();

        private final double left;
        private final double right;

        /**
         * Initializes an interval [left, right].
         * 
         * @param left
         *            the left endpoint
         * @param right
         *            the right endpoint
         * @throws IllegalArgumentException
         *             if the left endpoint is greater than the right endpoint
         * @throws IllegalArgumentException
         *             if either <tt>left</tt> or <tt>right</tt> is <tt>Double.NaN</tt>, <tt>Double.POSITIVE_INFINITY</tt> or
         *             <tt>Double.NEGATIVE_INFINITY</tt>
         */
        public Interval(double left, double right) {
            if (Double.isInfinite(left) || Double.isInfinite(right))
                throw new IllegalArgumentException("Endpoints must be finite");
            if (Double.isNaN(left) || Double.isNaN(right))
                throw new IllegalArgumentException("Endpoints cannot be NaN");

            if (left <= right) {
                this.left = left;
                this.right = right;
            } else
                throw new IllegalArgumentException("Illegal interval");
        }

        /**
         * Returns the left endpoint.
         * 
         * @return the left endpoint
         */
        public double left() {
            return left;
        }

        /**
         * Returns the right endpoint.
         * 
         * @return the right endpoint
         */
        public double right() {
            return right;
        }

        /**
         * Does this interval intersect that interval?
         * 
         * @param that
         *            the other interval
         * @return true if this interval intersects that interval; false otherwise
         */
        public boolean intersects(Interval that) {
            if (this.right < that.left)
                return false;
            if (that.right < this.left)
                return false;
            return true;
        }

        /**
         * Does this interval contain the value x?
         * 
         * @param x
         *            the value
         * @return true if this interval contains the value x; false otherwise
         */
        public boolean contains(double x) {
            return (left <= x) && (x <= right);
        }

        /**
         * Returns the length of this interval.
         * 
         * @return the length of this interval (right - left)
         */
        public double length() {
            return right - left;
        }

        /**
         * Returns a string representation of this interval.
         * 
         * @return a string representation of this interval in the form [left, right]
         */
        public String toString() {
            return "[" + left + ", " + right + "]";
        }

        // ascending order of left endpoint, breaking ties by right endpoint
        private static class LeftComparator implements Comparator<Interval> {
            public int compare(Interval a, Interval b) {
                if (a.left < b.left)
                    return -1;
                else if (a.left > b.left)
                    return +1;
                else if (a.right < b.right)
                    return -1;
                else if (a.right > b.right)
                    return +1;
                else
                    return 0;
            }
        }

        // ascending order of right endpoint, breaking ties by left endpoint
        private static class RightComparator implements Comparator<Interval> {
            public int compare(Interval a, Interval b) {
                if (a.right < b.right)
                    return -1;
                else if (a.right > b.right)
                    return +1;
                else if (a.left < b.left)
                    return -1;
                else if (a.left > b.left)
                    return +1;
                else
                    return 0;
            }
        }

        // ascending order of length
        private static class LengthComparator implements Comparator<Interval> {
            public int compare(Interval a, Interval b) {
                double alen = a.length();
                double blen = b.length();
                if (alen < blen)
                    return -1;
                else if (alen > blen)
                    return +1;
                else {
                    return Double.compare(b.left, a.left);
                    // return 0;
                }
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
