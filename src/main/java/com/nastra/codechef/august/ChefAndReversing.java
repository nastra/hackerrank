package com.nastra.codechef.august;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/AUG14/problems/REVERSE
 * @author nastra
 * 
 */
public class ChefAndReversing {

    private static int solve(EdgeWeightedDigraph graph, int source, int target) {
        Dijkstra d = new Dijkstra(graph, source);
        double dist = d.distanceTo(target);
        if (Double.MAX_VALUE == dist) {
            return -1;
        }
        return (int) dist;
    }

    static class Dijkstra {
        private DirectedEdge edgeTo[];
        private double distanceTo[];
        private IndexMinPQ<Double> heap;

        public Dijkstra(EdgeWeightedDigraph graph, int sourceVertex) {
            edgeTo = new DirectedEdge[graph.verticesCount()];
            distanceTo = new double[graph.verticesCount()];
            heap = new IndexMinPQ<Double>(graph.verticesCount());
            for (int v = 0; v < graph.verticesCount(); v++) {
                distanceTo[v] = Double.MAX_VALUE;
            }
            distanceTo[sourceVertex] = 0.0;
            heap.insert(sourceVertex, 0.0);

            while (!heap.isEmpty()) {
                relaxEdges(graph, heap.delMin());
            }
        }

        private void relaxEdges(EdgeWeightedDigraph graph, int vertex) {
            for (DirectedEdge e : graph.adjacent(vertex)) {
                int w = e.to();
                if (distanceTo[w] > distanceTo[vertex] + e.weight()) {
                    distanceTo[w] = distanceTo[vertex] + e.weight();
                    edgeTo[w] = e;
                    if (!heap.contains(w)) {
                        heap.insert(w, distanceTo[w]);
                    } else {
                        heap.change(w, distanceTo[w]);
                    }
                }
            }
        }

        public double distanceTo(int vertex) {
            return distanceTo[vertex];
        }

        public boolean hasPathTo(int vertex) {
            return Double.compare(distanceTo[vertex], Double.POSITIVE_INFINITY) < 0;
        }

        public Iterable<DirectedEdge> pathTo(int vertex) {
            Stack<DirectedEdge> path = new Stack<DirectedEdge>();
            DirectedEdge e = edgeTo[vertex];
            while (null != e) {
                path.push(e);
                e = edgeTo[e.from()];
            }
            return path;
        }
    }

    static class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
        private int NMAX; // maximum number of elements on PQ
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
        public IndexMinPQ(int NMAX) {
            if (NMAX < 0)
                throw new IllegalArgumentException();
            this.NMAX = NMAX;
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
            if (i < 0 || i >= NMAX)
                throw new IndexOutOfBoundsException();
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
         * Associates key with index i.
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
            if (i < 0 || i >= NMAX)
                throw new IndexOutOfBoundsException();
            if (contains(i))
                throw new IllegalArgumentException("index is already in the priority queue");
            N++;
            qp[i] = N;
            pq[N] = i;
            keys[i] = key;
            swim(N);
        }

        /**
         * Returns an index associated with a minimum key.
         * 
         * @return an index associated with a minimum key
         * @throws java.util.NoSuchElementException
         *             if priority queue is empty
         */
        public int minIndex() {
            if (N == 0)
                throw new NoSuchElementException("Priority queue underflow");
            return pq[1];
        }

        /**
         * Returns a minimum key.
         * 
         * @return a minimum key
         * @throws java.util.NoSuchElementException
         *             if priority queue is empty
         */
        public Key minKey() {
            if (N == 0)
                throw new NoSuchElementException("Priority queue underflow");
            return keys[pq[1]];
        }

        /**
         * Removes a minimum key and returns its associated index.
         * 
         * @return an index associated with a minimum key
         * @throws java.util.NoSuchElementException
         *             if priority queue is empty
         */
        public int delMin() {
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
            if (i < 0 || i >= NMAX)
                throw new IndexOutOfBoundsException();
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
         * @throws java.util.NoSuchElementException
         *             no key is associated with index i
         */
        public void changeKey(int i, Key key) {
            if (i < 0 || i >= NMAX)
                throw new IndexOutOfBoundsException();
            if (!contains(i))
                throw new NoSuchElementException("index is not in the priority queue");
            keys[i] = key;
            swim(qp[i]);
            sink(qp[i]);
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
            if (i < 0 || i >= NMAX)
                throw new IndexOutOfBoundsException();
            if (!contains(i))
                throw new NoSuchElementException("index is not in the priority queue");
            if (keys[i].compareTo(key) <= 0)
                throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
            keys[i] = key;
            swim(qp[i]);
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
            if (i < 0 || i >= NMAX)
                throw new IndexOutOfBoundsException();
            if (!contains(i))
                throw new NoSuchElementException("index is not in the priority queue");
            if (keys[i].compareTo(key) >= 0)
                throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");
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
            if (i < 0 || i >= NMAX)
                throw new IndexOutOfBoundsException();
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
        private boolean greater(int i, int j) {
            return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
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
            while (k > 1 && greater(k / 2, k)) {
                exch(k, k / 2);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= N) {
                int j = 2 * k;
                if (j < N && greater(j, j + 1))
                    j++;
                if (!greater(k, j))
                    break;
                exch(k, j);
                k = j;
            }
        }

        /***********************************************************************
         * Iterators
         **********************************************************************/

        /**
         * Returns an iterator that iterates over the keys on the priority queue in ascending order. The iterator doesn't implement <tt>remove()</tt>
         * since it's optional.
         * 
         * @return an iterator that iterates over the keys in ascending order
         */
        public Iterator<Integer> iterator() {
            return new HeapIterator();
        }

        private class HeapIterator implements Iterator<Integer> {
            // create a new pq
            private IndexMinPQ<Key> copy;

            // add all elements to copy of heap
            // takes linear time since already in heap order so no keys move
            public HeapIterator() {
                copy = new IndexMinPQ<Key>(pq.length - 1);
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
                return copy.delMin();
            }
        }
    }

    static class DirectedEdge implements Comparable<DirectedEdge> {

        private final int v;
        private final int w;
        private final double weight;

        public DirectedEdge(int v, int w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        public double weight() {
            return weight;
        }

        public int from() {
            return v;
        }

        public int to() {
            return w;
        }

        public int compareTo(DirectedEdge that) {
            if (this.weight() < that.weight()) {
                return -1;
            }
            if (this.weight() > that.weight()) {
                return 1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return String.format("%d-%d %.5f", v, w, weight);
        }
    }

    static class EdgeWeightedDigraph {

        private final int vertices;
        private int edges;
        private Set<DirectedEdge>[] adjacent;

        @SuppressWarnings("unchecked")
        public EdgeWeightedDigraph(int vertices) {
            this.vertices = vertices;
            this.edges = 0;
            adjacent = (Set<DirectedEdge>[]) new Set[vertices];
            for (int v = 0; v < vertices; v++) {
                adjacent[v] = new HashSet<DirectedEdge>();
            }
        }

        public int verticesCount() {
            return vertices;
        }

        public int edgeCount() {
            return edges;
        }

        public void addEdge(DirectedEdge e) {
            adjacent[e.from()].add(e);
            edges++;
        }

        public Iterable<DirectedEdge> adjacent(int vertex) {
            return adjacent[vertex];
        }

        public Iterable<DirectedEdge> edges() {
            Set<DirectedEdge> set = new HashSet<DirectedEdge>();
            for (int v = 0; v < vertices; v++) {
                for (DirectedEdge edge : adjacent[v]) {
                    set.add(edge);
                }
            }
            return set;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        int m = sc.nextInt();
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(n + 1);

        int i = 0;
        while (i < m) {
            i++;
            int x = sc.nextInt();
            int y = sc.nextInt();
            if (x == y) {
                continue;
            }
            graph.addEdge(new DirectedEdge(x, y, 0));
            graph.addEdge(new DirectedEdge(y, x, 1d));

        }
        out.println(solve(graph, 1, n));
        out.close();
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
