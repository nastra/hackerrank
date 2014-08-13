package com.nastra.codechef.august;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/AUG14/problems/CLETAB
 * @author nastra
 * 
 */
public class CleaningTables {

    private static final int MAX_CUSTOMERS = 401;

    private static int solve(int[] customers, int m, int n) {
        IndexMaxPQ<Integer> q = new IndexMaxPQ<>(MAX_CUSTOMERS + 1);
        Customer[] c = new Customer[MAX_CUSTOMERS];
        for (int i = 1; i < MAX_CUSTOMERS; i++) {
            c[i] = new Customer(i);
        }
        for (int i = 0; i < customers.length; i++) {
            c[customers[i]].orders.addLast(i);
        }

        int result = 0;
        for (int i = 0; i < customers.length; i++) {
            if (q.contains(customers[i])) {
                // change priority
                c[customers[i]].orders.pollFirst();
                q.change(customers[i], c[customers[i]].nextOrder());
                continue;
            }

            if (q.size() == n) {
                q.delMax();
            }
            result++;
            c[customers[i]].orders.pollFirst();
            q.insert(customers[i], c[customers[i]].nextOrder());
        }
        return result;

    }

    private static class Customer {
        int id;
        LinkedList<Integer> orders;

        public Customer(int id) {
            super();
            this.id = id;
            orders = new LinkedList<>();
        }

        int nextOrder() {
            if (orders.isEmpty()) {
                return 1000000000;
            }
            return orders.peekFirst();
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = sc.nextInt();

        while (t > 0) {
            t--;
            int n = sc.nextInt();
            int m = sc.nextInt();
            int[] customers = sc.nextIntArray();
            out.println(solve(customers, m, n));
        }

        out.close();
    }

    private static class IndexMaxPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
        private int N; // number of elements on PQ
        private int[] pq; // binary heap using 1-based indexing
        private int[] qp; // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
        private Key[] keys; // keys[i] = priority of i

        /**
         * Initializes an empty indexed priority queue with indices between 0 and NMAX-1.
         * 
         * @param NMAX
         *            the keys on the priority queue are index from 0 to NMAX-1
         * @throws java.lang.IllegalArgumentException
         *             if NMAX < 0
         */
        public IndexMaxPQ(int NMAX) {
            keys = (Key[]) new Comparable[NMAX + 1]; // make this of length NMAX??
            pq = new int[NMAX + 1];
            qp = new int[NMAX + 1]; // make this of length NMAX??
            for (int i = 0; i <= NMAX; i++)
                qp[i] = -1;
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
         * Is i an index on the priority queue?
         * 
         * @param i
         *            an index
         * @throws java.lang.IndexOutOfBoundsException
         *             unless (0 &le; i < NMAX)
         */
        public boolean contains(int i) {
            return qp[i] != -1;
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
         * Associate key with index i.
         * 
         * @param i
         *            an index
         * @param key
         *            the key to associate with index i
         * @throws java.lang.IndexOutOfBoundsException
         *             unless 0 &le; i < NMAX
         * @throws java.util.IllegalArgumentException
         *             if there already is an item associated with index i
         */
        public void insert(int i, Key key) {
            if (contains(i))
                throw new IllegalArgumentException("index is already in the priority queue");
            N++;
            qp[i] = N;
            pq[N] = i;
            keys[i] = key;
            swim(N);
        }

        /**
         * Returns an index associated with a maximum key.
         * 
         * @return an index associated with a maximum key
         * @throws java.util.NoSuchElementException
         *             if priority queue is empty
         */
        public int maxIndex() {
            if (N == 0)
                throw new NoSuchElementException("Priority queue underflow");
            return pq[1];
        }

        /**
         * Return a maximum key.
         * 
         * @return a maximum key
         * @throws java.util.NoSuchElementException
         *             if priority queue is empty
         */
        public Key maxKey() {
            if (N == 0)
                throw new NoSuchElementException("Priority queue underflow");
            return keys[pq[1]];
        }

        /**
         * Removes a maximum key and returns its associated index.
         * 
         * @return an index associated with a maximum key
         * @throws java.util.NoSuchElementException
         *             if priority queue is empty
         */
        public int delMax() {
            if (N == 0)
                throw new NoSuchElementException("Priority queue underflow");
            int min = pq[1];
            exch(1, N--);
            sink(1);
            qp[min] = -1; // delete
            keys[pq[N + 1]] = null; // to help with garbage collection
            pq[N + 1] = -1; // not needed
            return min;
        }

        /**
         * Returns the key associated with index i.
         * 
         * @param i
         *            the index of the key to return
         * @return the key associated with index i
         * @throws java.lang.IndexOutOfBoundsException
         *             unless 0 &le; i < NMAX
         * @throws java.util.NoSuchElementException
         *             no key is associated with index i
         */
        public Key keyOf(int i) {
            if (!contains(i))
                throw new NoSuchElementException("index is not in the priority queue");
            else
                return keys[i];
        }

        /**
         * Change the key associated with index i to the specified value.
         * 
         * @param i
         *            the index of the key to change
         * @param key
         *            change the key assocated with index i to this key
         * @throws java.lang.IndexOutOfBoundsException
         *             unless 0 &le; i < NMAX
         * @deprecated Replaced by changeKey()
         */
        @Deprecated
        public void change(int i, Key key) {
            changeKey(i, key);
        }

        /**
         * Change the key associated with index i to the specified value.
         * 
         * @param i
         *            the index of the key to change
         * @param key
         *            change the key assocated with index i to this key
         * @throws java.lang.IndexOutOfBoundsException
         *             unless 0 &le; i < NMAX
         */
        public void changeKey(int i, Key key) {
            if (!contains(i))
                throw new NoSuchElementException("index is not in the priority queue");
            keys[i] = key;
            swim(qp[i]);
            sink(qp[i]);
        }

        /**
         * Increase the key associated with index i to the specified value.
         * 
         * @param i
         *            the index of the key to increase
         * @param key
         *            increase the key assocated with index i to this key
         * @throws java.lang.IndexOutOfBoundsException
         *             unless 0 &le; i < NMAX
         * @throws java.lang.IllegalArgumentException
         *             if key &le; key associated with index i
         * @throws java.util.NoSuchElementException
         *             no key is associated with index i
         */
        public void increaseKey(int i, Key key) {
            if (!contains(i))
                throw new NoSuchElementException("index is not in the priority queue");
            if (keys[i].compareTo(key) >= 0)
                throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");

            keys[i] = key;
            swim(qp[i]);
        }

        /**
         * Decrease the key associated with index i to the specified value.
         * 
         * @param i
         *            the index of the key to decrease
         * @param key
         *            decrease the key assocated with index i to this key
         * @throws java.lang.IndexOutOfBoundsException
         *             unless 0 &le; i < NMAX
         * @throws java.lang.IllegalArgumentException
         *             if key &ge; key associated with index i
         * @throws java.util.NoSuchElementException
         *             no key is associated with index i
         */
        public void decreaseKey(int i, Key key) {
            if (!contains(i))
                throw new NoSuchElementException("index is not in the priority queue");
            if (keys[i].compareTo(key) <= 0)
                throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");

            keys[i] = key;
            sink(qp[i]);
        }

        /**
         * Remove the key associated with index i.
         * 
         * @param i
         *            the index of the key to remove
         * @throws java.lang.IndexOutOfBoundsException
         *             unless 0 &le; i < NMAX
         * @throws java.util.NoSuchElementException
         *             no key is associated with index i
         */
        public void delete(int i) {
            if (!contains(i))
                throw new NoSuchElementException("index is not in the priority queue");
            int index = qp[i];
            exch(index, N--);
            swim(index);
            sink(index);
            keys[i] = null;
            qp[i] = -1;
        }

        /**************************************************************
         * General helper functions
         **************************************************************/
        private boolean less(int i, int j) {
            return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
        }

        private void exch(int i, int j) {
            int swap = pq[i];
            pq[i] = pq[j];
            pq[j] = swap;
            qp[pq[i]] = i;
            qp[pq[j]] = j;
        }

        /**************************************************************
         * Heap helper functions
         **************************************************************/
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
         * Iterators
         **********************************************************************/

        /**
         * Returns an iterator that iterates over the keys on the priority queue in descending order. The iterator doesn't implement <tt>remove()</tt>
         * since it's optional.
         * 
         * @return an iterator that iterates over the keys in descending order
         */
        public Iterator<Integer> iterator() {
            return new HeapIterator();
        }

        private class HeapIterator implements Iterator<Integer> {
            // create a new pq
            private IndexMaxPQ<Key> copy;

            // add all elements to copy of heap
            // takes linear time since already in heap order so no keys move
            public HeapIterator() {
                copy = new IndexMaxPQ<Key>(pq.length - 1);
                for (int i = 1; i <= N; i++)
                    copy.insert(pq[i], keys[pq[i]]);
            }

            public boolean hasNext() {
                return !copy.isEmpty();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Integer next() {
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
