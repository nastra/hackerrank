package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * PLEASE NOTE: algorithm is not working because I tried to use the closest pair algorithm for solving the problem, but the actual working algorithm
 * uses bipartite matching.
 * 
 * @see https://www.hackerrank.com/contests/feb14/challenges/bike-racers
 * @author nastra - Eduard Tudenhoefner
 * 
 */
public class BikeRacers {

    public static BigDecimal solve(KDTree tree, Point[] points, int k) {
        Arrays.sort(points, Point.X_ORDER);
        MaxPQ<Double> heap = new MaxPQ<Double>(k);
        for (int j = 0; j < points.length; j++) {
            Point p = points[j];
            double[] search = {p.x(), p.y()};
            Point q = (Point) tree.nearest(search);
            double dist = p.distanceTo(q);
            Double delete = null;
            if (q.count == 0) {
                // double[] del = {q.x(), q.y()};
                // tree.delete(del);
                if (!q.heap.isEmpty()) {
                    if (dist < q.heap.max()) {
                        delete = q.heap.delMax();
                        q.heap.insert(dist);
                    }
                }
            } else {
                q.count = q.count - 1;
                q.heap.insert(dist);
            }

            if (!heap.isEmpty() && null != delete) {
                heap.delete(delete);
            }

            if (heap.size() < k) {
                heap.insert(dist);
            } else if (dist < heap.max()) {
                heap.delMax();
                heap.insert(dist);
            }
        }

        double time = heap.max();
        return new BigDecimal(time * time);
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        KDTree tree = new KDTree(2);
        boolean nShorter = true;

        Point[] points;
        if (N < M) {
            points = new Point[N];
            nShorter = true;
        } else {
            points = new Point[M];
            nShorter = false;
        }

        for (int i = 0; i < N; i++) {
            Point p = new Point(sc.nextInt(), sc.nextInt());
            if (nShorter) {
                points[i] = p;
            } else {
                double[] curr = {p.x(), p.y()};
                Point node = (Point) tree.search(curr);
                if (null != node) {
                    node.count = node.count + 1;
                } else {
                    p.heap = new MaxPQ<Double>();
                    tree.insert(curr, p);
                }
            }
        }

        for (int i = 0; i < M; i++) {
            Point p = new Point(sc.nextInt(), sc.nextInt());
            if (nShorter) {
                double[] curr = {p.x(), p.y()};
                Point node = (Point) tree.search(curr);
                if (null != node) {
                    node.count = node.count + 1;
                } else {
                    p.heap = new MaxPQ<Double>();
                    tree.insert(curr, p);
                }
            } else {
                points[i] = p;
            }
        }

        BigDecimal result = solve(tree, points, K);
        System.out.println(result.longValue());
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

        public boolean delete(Key key) {
            if (isEmpty()) {
                return false;
            }
            for (int i = 1; i <= N; i++) {
                if (pq[i].equals(key)) {
                    exch(i, N);
                    pq[N] = null;
                    N--;
                    swim(N);
                    if ((N > 0) && (N == (pq.length - 1) / 4)) {
                        resize(pq.length / 2);
                    }
                    return true;
                }
            }
            return false;
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

    private static class Point {
        private final int x, y;
        private int count = 1;

        private MaxPQ<Double> heap;

        /**
         * Compares two points by y-coordinate.
         */
        static final Comparator<Point> Y_ORDER = new YOrder();

        static final Comparator<Point> X_ORDER = new XOrder();

        public Point(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

        /**
         * Is a->b->c a counterclockwise turn?
         * 
         * @param a
         *            first point
         * @param b
         *            second point
         * @param c
         *            third point
         * @return { -1, 0, +1 } if a->b->c is a { clockwise, collinear; counterclocwise } turn.
         */
        public static int ccw(Point a, Point b, Point c) {
            double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
            if (area2 < 0)
                return -1;
            else if (area2 > 0)
                return +1;
            else
                return 0;
        }

        @Override
        public String toString() {
            return "[" + x + ", " + y + "] - Count: " + count;
        }

        /**
         * Returns the Euclidean distance between this point and that point.
         * 
         * @param that
         *            the other point
         * @return the Euclidean distance between this point and that point
         */
        public double distanceTo(Point that) {
            double dx = this.x - that.x;
            double dy = this.y - that.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * Returns the square of the Euclidean distance between this point and that point.
         * 
         * @param that
         *            the other point
         * @return the square of the Euclidean distance between this point and that point
         */
        public double distanceSquaredTo(Point that) {
            double dx = this.x - that.x;
            double dy = this.y - that.y;
            return dx * dx + dy * dy;
        }

        public double x() {
            return (double) x;
        }

        public double y() {
            return (double) y;
        }

        /**
         * Compares this point to that point by y-coordinate, breaking ties by x-coordinate.
         * 
         * @param that
         *            the other point
         * @return { a negative integer, zero, a positive integer } if this point is { less than, equal to, greater than } that point
         */
        public int compareTo(Point that) {
            if (this.y < that.y)
                return -1;
            if (this.y > that.y)
                return +1;
            if (this.x < that.x)
                return -1;
            if (this.x > that.x)
                return +1;
            return 0;
        }

        /**
         * compare points according to their y-coordinate
         */
        private static class YOrder implements Comparator<Point> {
            public int compare(Point p, Point q) {
                if (p.y < q.y)
                    return -1;
                if (p.y > q.y)
                    return +1;
                return 0;
            }
        }

        // compare points according to their x-coordinate
        private static class XOrder implements Comparator<Point> {
            public int compare(Point p, Point q) {
                if (p.x < q.x)
                    return -1;
                if (p.x > q.x)
                    return +1;
                return 0;
            }
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point other = (Point) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

    }

    public static class KDTree {

        // K = number of dimensions
        private int m_K;

        // root of KD-tree
        private KDNode m_root;

        // count of nodes
        private int m_count;

        /**
         * Creates a KD-tree with specified number of dimensions.
         * 
         * @param k
         *            number of dimensions
         */
        public KDTree(int k) {

            m_K = k;
            m_root = null;
        }

        /**
         * Insert a node in a KD-tree. Uses algorithm translated from 352.ins.c of
         * 
         * <PRE>
         *   &#064;Book{GonnetBaezaYates1991,                                   
         *     author =    {G.H. Gonnet and R. Baeza-Yates},
         *     title =     {Handbook of Algorithms and Data Structures},
         *     publisher = {Addison-Wesley},
         *     year =      {1991}
         *   }
         * </PRE>
         * 
         * @param key
         *            key for KD-tree node
         * @param value
         *            value at that key
         * 
         * @throws KeySizeException
         *             if key.length mismatches K
         * @throws KeyDuplicateException
         *             if key already in tree
         */
        public void insert(double[] key, Object value) {

            if (key.length != m_K) {
                throw new RuntimeException("KDTree: wrong key size!");
            }

            else
                m_root = KDNode.ins(new HPoint(key), value, m_root, 0, m_K);

            m_count++;
        }

        /**
         * Find KD-tree node whose key is identical to key. Uses algorithm translated from 352.srch.c of Gonnet & Baeza-Yates.
         * 
         * @param key
         *            key for KD-tree node
         * 
         * @return object at key, or null if not found
         * 
         * @throws KeySizeException
         *             if key.length mismatches K
         */
        public Object search(double[] key) {

            if (key.length != m_K) {
                throw new RuntimeException("KDTree: wrong key size!");
            }

            KDNode kd = KDNode.srch(new HPoint(key), m_root, m_K);

            return (kd == null ? null : kd.v);
        }

        /**
         * Delete a node from a KD-tree. Instead of actually deleting node and rebuilding tree, marks node as deleted. Hence, it is up to the caller
         * to rebuild the tree as needed for efficiency.
         * 
         * @param key
         *            key for KD-tree node
         * 
         * @throws KeySizeException
         *             if key.length mismatches K
         * @throws KeyMissingException
         *             if no node in tree has key
         */
        public void delete(double[] key) {

            if (key.length != m_K) {
                throw new RuntimeException("KDTree: wrong key size!");
            }

            else {

                KDNode t = KDNode.srch(new HPoint(key), m_root, m_K);
                if (t == null) {
                    throw new RuntimeException("KDTree: key missing!");
                } else {
                    t.deleted = true;
                }

                m_count--;
            }
        }

        /**
         * Find KD-tree node whose key is nearest neighbor to key. Implements the Nearest Neighbor algorithm (Table 6.4) of
         * 
         * <PRE>
         * &#064;techreport{AndrewMooreNearestNeighbor,
         *   author  = {Andrew Moore},
         *   title   = {An introductory tutorial on kd-trees},
         *   institution = {Robotics Institute, Carnegie Mellon University},
         *   year    = {1991},
         *   number  = {Technical Report No. 209, Computer Laboratory, 
         *              University of Cambridge},
         *   address = {Pittsburgh, PA}
         * }
         * </PRE>
         * 
         * @param key
         *            key for KD-tree node
         * 
         * @return object at node nearest to key, or null on failure
         * 
         * @throws KeySizeException
         *             if key.length mismatches K
         */
        public Object nearest(double[] key) {

            Object[] nbrs = nearest(key, 1);
            return nbrs[0];
        }

        /**
         * Find KD-tree nodes whose keys are <I>n</I> nearest neighbors to key. Uses algorithm above. Neighbors are returned in ascending order of
         * distance to key.
         * 
         * @param key
         *            key for KD-tree node
         * @param n
         *            how many neighbors to find
         * 
         * @return objects at node nearest to key, or null on failure
         * 
         * @throws KeySizeException
         *             if key.length mismatches K
         * @throws IllegalArgumentException
         *             if <I>n</I> is negative or exceeds tree size
         */
        public Object[] nearest(double[] key, int n) {

            if (n < 0 || n > m_count) {
                throw new IllegalArgumentException("Number of neighbors (" + n + ") cannot" + " be negative or greater than number of nodes ("
                        + m_count + ").");
            }

            if (key.length != m_K) {
                throw new RuntimeException("KDTree: wrong key size!");
            }

            Object[] nbrs = new Object[n];
            NearestNeighborList nnl = new NearestNeighborList(n);

            // initial call is with infinite hyper-rectangle and max distance
            HRect hr = HRect.infiniteHRect(key.length);
            double max_dist_sqd = Double.MAX_VALUE;
            HPoint keyp = new HPoint(key);

            KDNode.nnbr(m_root, keyp, hr, max_dist_sqd, 0, m_K, nnl);

            for (int i = 0; i < n; ++i) {
                KDNode kd = (KDNode) nnl.removeHighest();
                nbrs[n - i - 1] = kd.v;
            }

            return nbrs;
        }

        /**
         * Range search in a KD-tree. Uses algorithm translated from 352.range.c of Gonnet & Baeza-Yates.
         * 
         * @param lowk
         *            lower-bounds for key
         * @param uppk
         *            upper-bounds for key
         * 
         * @return array of Objects whose keys fall in range [lowk,uppk]
         * 
         * @throws KeySizeException
         *             on mismatch among lowk.length, uppk.length, or K
         */
        public Object[] range(double[] lowk, double[] uppk) {

            if (lowk.length != uppk.length) {
                throw new RuntimeException("KDTree: wrong key size!");
            }

            else if (lowk.length != m_K) {
                throw new RuntimeException("KDTree: wrong key size!");
            }

            else {
                Vector<KDNode> v = new Vector<KDNode>();
                KDNode.rsearch(new HPoint(lowk), new HPoint(uppk), m_root, 0, m_K, v);
                Object[] o = new Object[v.size()];
                for (int i = 0; i < v.size(); ++i) {
                    KDNode n = v.elementAt(i);
                    o[i] = n.v;
                }
                return o;
            }
        }

        public String toString() {
            return m_root.toString(0);
        }
    }

    static class NearestNeighborList {

        public static int REMOVE_HIGHEST = 1;
        public static int REMOVE_LOWEST = 2;

        PriorityQueue m_Queue = null;
        int m_Capacity = 0;

        // constructor
        public NearestNeighborList(int capacity) {
            m_Capacity = capacity;
            m_Queue = new PriorityQueue(m_Capacity, Double.POSITIVE_INFINITY);
        }

        public double getMaxPriority() {
            if (m_Queue.length() == 0) {
                return Double.POSITIVE_INFINITY;
            }
            return m_Queue.getMaxPriority();
        }

        public boolean insert(Object object, double priority) {
            if (m_Queue.length() < m_Capacity) {
                // capacity not reached
                m_Queue.add(object, priority);
                return true;
            }
            if (priority > m_Queue.getMaxPriority()) {
                // do not insert - all elements in queue have lower priority
                return false;
            }
            // remove object with highest priority
            m_Queue.remove();
            // add new object
            m_Queue.add(object, priority);
            return true;
        }

        public boolean isCapacityReached() {
            return m_Queue.length() >= m_Capacity;
        }

        public Object getHighest() {
            return m_Queue.front();
        }

        public boolean isEmpty() {
            return m_Queue.length() == 0;
        }

        public int getSize() {
            return m_Queue.length();
        }

        public Object removeHighest() {
            // remove object with highest priority
            return m_Queue.remove();
        }
    }

    static class PriorityQueue implements java.io.Serializable {

        /**
         * This class implements a <code>PriorityQueue</code>. This class is implemented in such a way that objects are added using an
         * <code>add</code> function. The <code>add</code> function takes two parameters an object and a long.
         * <p>
         * The object represents an item in the queue, the long indicates its priority in the queue. The remove function in this class returns the
         * object first in the queue and that object is removed from the queue permanently.
         * 
         * @author Bjoern Heckel
         * @version %I%, %G%
         * @since JDK1.2
         */

        /**
         * The maximum priority possible in this priority queue.
         */
        private double maxPriority = Double.MAX_VALUE;

        /**
         * This contains the list of objects in the queue.
         */
        private Object[] data;

        /**
         * This contains the list of prioritys in the queue.
         */
        private double[] value;

        /**
         * Holds the number of elements currently in the queue.
         */
        private int count;

        /**
         * This holds the number elements this queue can have.
         */
        private int capacity;

        /**
         * Creates a new <code>PriorityQueue</code> object. The <code>PriorityQueue</code> object allows objects to be entered into the queue and to
         * leave in the order of priority i.e the highest priority get's to leave first.
         */
        public PriorityQueue() {
            init(20);
        }

        /**
         * Creates a new <code>PriorityQueue</code> object. The <code>PriorityQueue</code> object allows objects to be entered into the queue an to
         * leave in the order of priority i.e the highest priority get's to leave first.
         * 
         * @param capacity
         *            the initial capacity of the queue before a resize
         */
        public PriorityQueue(int capacity) {
            init(capacity);
        }

        /**
         * Creates a new <code>PriorityQueue</code> object. The <code>PriorityQueue</code> object allows objects to be entered into the queue an to
         * leave in the order of priority i.e the highest priority get's to leave first.
         * 
         * @param capacity
         *            the initial capacity of the queue before a resize
         * @param maxPriority
         *            is the maximum possible priority for an object
         */
        public PriorityQueue(int capacity, double maxPriority) {
            this.maxPriority = maxPriority;
            init(capacity);
        }

        /**
         * This is an initializer for the object. It basically initializes an array of long called value to represent the prioritys of the objects, it
         * also creates an array of objects to be used in parallel with the array of longs, to represent the objects entered, these can be used to
         * sequence the data.
         * 
         * @param size
         *            the initial capacity of the queue, it can be resized
         */
        private void init(int size) {
            capacity = size;
            data = new Object[capacity + 1];
            value = new double[capacity + 1];
            value[0] = maxPriority;
            data[0] = null;
        }

        /**
         * This function adds the given object into the <code>PriorityQueue</code>, its priority is the long priority. The way in which priority can
         * be associated with the elements of the queue is by keeping the priority and the elements array entrys parallel.
         * 
         * @param element
         *            is the object that is to be entered into this <code>PriorityQueue</code>
         * @param priority
         *            this is the priority that the object holds in the <code>PriorityQueue</code>
         */
        public void add(Object element, double priority) {
            if (count++ >= capacity) {
                expandCapacity();
            }
            /* put this as the last element */
            value[count] = priority;
            data[count] = element;
            bubbleUp(count);
        }

        /**
         * Remove is a function to remove the element in the queue with the maximum priority. Once the element is removed then it can never be
         * recovered from the queue with further calls. The lowest priority object will leave last.
         * 
         * @return the object with the highest priority or if it's empty null
         */
        public Object remove() {
            if (count == 0)
                return null;
            Object element = data[1];
            /* swap the last element into the first */
            data[1] = data[count];
            value[1] = value[count];
            /* let the GC clean up */
            data[count] = null;
            value[count] = 0L;
            count--;
            bubbleDown(1);
            return element;
        }

        public Object front() {
            return data[1];
        }

        public double getMaxPriority() {
            return value[1];
        }

        /**
         * Bubble down is used to put the element at subscript 'pos' into it's rightful place in the heap (i.e heap is another name for
         * <code>PriorityQueue</code>). If the priority of an element at subscript 'pos' is less than it's children then it must be put under one of
         * these children, i.e the ones with the maximum priority must come first.
         * 
         * @param pos
         *            is the position within the arrays of the element and priority
         */
        private void bubbleDown(int pos) {
            Object element = data[pos];
            double priority = value[pos];
            int child;
            /* hole is position '1' */
            for (; pos * 2 <= count; pos = child) {
                child = pos * 2;
                /*
                 * if 'child' equals 'count' then there is only one leaf for this parent
                 */
                if (child != count)

                    /* left_child > right_child */
                    if (value[child] < value[child + 1])
                        child++; /* choose the biggest child */
                /*
                 * percolate down the data at 'pos', one level i.e biggest child becomes the parent
                 */
                if (priority < value[child]) {
                    value[pos] = value[child];
                    data[pos] = data[child];
                } else {
                    break;
                }
            }
            value[pos] = priority;
            data[pos] = element;
        }

        /**
         * Bubble up is used to place an element relatively low in the queue to it's rightful place higher in the queue, but only if it's priority
         * allows it to do so, similar to bubbleDown only in the other direction this swaps out its parents.
         * 
         * @param pos
         *            the position in the arrays of the object to be bubbled up
         */
        private void bubbleUp(int pos) {
            Object element = data[pos];
            double priority = value[pos];
            /* when the parent is not less than the child, end */
            while (value[pos / 2] < priority) {
                /* overwrite the child with the parent */
                value[pos] = value[pos / 2];
                data[pos] = data[pos / 2];
                pos /= 2;
            }
            value[pos] = priority;
            data[pos] = element;
        }

        /**
         * This ensures that there is enough space to keep adding elements to the priority queue. It is however advised to make the capacity of the
         * queue large enough so that this will not be used as it is an expensive method. This will copy across from 0 as 'off' equals 0 is contains
         * some important data.
         */
        private void expandCapacity() {
            capacity = count * 2;
            Object[] elements = new Object[capacity + 1];
            double[] prioritys = new double[capacity + 1];
            System.arraycopy(data, 0, elements, 0, data.length);
            System.arraycopy(value, 0, prioritys, 0, data.length);
            data = elements;
            value = prioritys;
        }

        /**
         * This method will empty the queue. This also helps garbage collection by releasing any reference it has to the elements in the queue. This
         * starts from offset 1 as off equals 0 for the elements array.
         */
        public void clear() {
            for (int i = 1; i < count; i++) {
                data[i] = null; /* help gc */
            }
            count = 0;
        }

        /**
         * The number of elements in the queue. The length indicates the number of elements that are currently in the queue.
         * 
         * @return the number of elements in the queue
         */
        public int length() {
            return count;
        }

        // arbitrary; every serializable class has to have one of these
        public static final long serialVersionUID = 4L;

    }

    static class KDNode {

        // these are seen by KDTree
        protected HPoint k;

        Object v;

        protected KDNode left, right;

        protected boolean deleted;

        // Method ins translated from 352.ins.c of Gonnet & Baeza-Yates
        protected static KDNode ins(HPoint key, Object val, KDNode t, int lev, int K) {

            if (t == null) {
                t = new KDNode(key, val);
            }

            else if (key.equals(t.k)) {

                // "re-insert"
                if (t.deleted) {
                    t.deleted = false;
                    t.v = val;
                }

                // else {
                // throw new KeyDuplicateException();
                // }
            }

            else if (key.coord[lev] > t.k.coord[lev]) {
                t.right = ins(key, val, t.right, (lev + 1) % K, K);
            } else {
                t.left = ins(key, val, t.left, (lev + 1) % K, K);
            }

            return t;
        }

        // Method srch translated from 352.srch.c of Gonnet & Baeza-Yates
        protected static KDNode srch(HPoint key, KDNode t, int K) {

            for (int lev = 0; t != null; lev = (lev + 1) % K) {

                if (!t.deleted && key.equals(t.k)) {
                    return t;
                } else if (key.coord[lev] > t.k.coord[lev]) {
                    t = t.right;
                } else {
                    t = t.left;
                }
            }

            return null;
        }

        // Method rsearch translated from 352.range.c of Gonnet & Baeza-Yates
        protected static void rsearch(HPoint lowk, HPoint uppk, KDNode t, int lev, int K, Vector<KDNode> v) {

            if (t == null)
                return;
            if (lowk.coord[lev] <= t.k.coord[lev]) {
                rsearch(lowk, uppk, t.left, (lev + 1) % K, K, v);
            }
            int j;
            for (j = 0; j < K && lowk.coord[j] <= t.k.coord[j] && uppk.coord[j] >= t.k.coord[j]; j++)
                ;
            if (j == K)
                v.add(t);
            if (uppk.coord[lev] > t.k.coord[lev]) {
                rsearch(lowk, uppk, t.right, (lev + 1) % K, K, v);
            }
        }

        // Method Nearest Neighbor from Andrew Moore's thesis. Numbered
        // comments are direct quotes from there. Step "SDL" is added to
        // make the algorithm work correctly. NearestNeighborList solution
        // courtesy of Bjoern Heckel.
        protected static void nnbr(KDNode kd, HPoint target, HRect hr, double max_dist_sqd, int lev, int K, NearestNeighborList nnl) {

            // 1. if kd is empty then set dist-sqd to infinity and exit.
            if (kd == null) {
                return;
            }

            // 2. s := split field of kd
            int s = lev % K;

            // 3. pivot := dom-elt field of kd
            HPoint pivot = kd.k;
            double pivot_to_target = HPoint.sqrdist(pivot, target);

            // 4. Cut hr into to sub-hyperrectangles left-hr and right-hr.
            // The cut plane is through pivot and perpendicular to the s
            // dimension.
            HRect left_hr = hr; // optimize by not cloning
            HRect right_hr = (HRect) hr.clone();
            left_hr.max.coord[s] = pivot.coord[s];
            right_hr.min.coord[s] = pivot.coord[s];

            // 5. target-in-left := target_s <= pivot_s
            boolean target_in_left = target.coord[s] < pivot.coord[s];

            KDNode nearer_kd;
            HRect nearer_hr;
            KDNode further_kd;
            HRect further_hr;

            // 6. if target-in-left then
            // 6.1. nearer-kd := left field of kd and nearer-hr := left-hr
            // 6.2. further-kd := right field of kd and further-hr := right-hr
            if (target_in_left) {
                nearer_kd = kd.left;
                nearer_hr = left_hr;
                further_kd = kd.right;
                further_hr = right_hr;
            }
            //
            // 7. if not target-in-left then
            // 7.1. nearer-kd := right field of kd and nearer-hr := right-hr
            // 7.2. further-kd := left field of kd and further-hr := left-hr
            else {
                nearer_kd = kd.right;
                nearer_hr = right_hr;
                further_kd = kd.left;
                further_hr = left_hr;
            }

            // 8. Recursively call Nearest Neighbor with paramters
            // (nearer-kd, target, nearer-hr, max-dist-sqd), storing the
            // results in nearest and dist-sqd
            nnbr(nearer_kd, target, nearer_hr, max_dist_sqd, lev + 1, K, nnl);

            KDNode nearest = (KDNode) nnl.getHighest();
            double dist_sqd;

            if (!nnl.isCapacityReached()) {
                dist_sqd = Double.MAX_VALUE;
            } else {
                dist_sqd = nnl.getMaxPriority();
            }

            // 9. max-dist-sqd := minimum of max-dist-sqd and dist-sqd
            max_dist_sqd = Math.min(max_dist_sqd, dist_sqd);

            // 10. A nearer point could only lie in further-kd if there were some
            // part of further-hr within distance sqrt(max-dist-sqd) of
            // target. If this is the case then
            HPoint closest = further_hr.closest(target);
            if (HPoint.eucdist(closest, target) < Math.sqrt(max_dist_sqd)) {

                // 10.1 if (pivot-target)^2 < dist-sqd then
                if (pivot_to_target < dist_sqd) {

                    // 10.1.1 nearest := (pivot, range-elt field of kd)
                    nearest = kd;

                    // 10.1.2 dist-sqd = (pivot-target)^2
                    dist_sqd = pivot_to_target;

                    // add to nnl
                    if (!kd.deleted) {
                        nnl.insert(kd, dist_sqd);
                    }

                    // 10.1.3 max-dist-sqd = dist-sqd
                    // max_dist_sqd = dist_sqd;
                    if (nnl.isCapacityReached()) {
                        max_dist_sqd = nnl.getMaxPriority();
                    } else {
                        max_dist_sqd = Double.MAX_VALUE;
                    }
                }

                // 10.2 Recursively call Nearest Neighbor with parameters
                // (further-kd, target, further-hr, max-dist_sqd),
                // storing results in temp-nearest and temp-dist-sqd
                nnbr(further_kd, target, further_hr, max_dist_sqd, lev + 1, K, nnl);
                KDNode temp_nearest = (KDNode) nnl.getHighest();
                double temp_dist_sqd = nnl.getMaxPriority();

                // 10.3 If tmp-dist-sqd < dist-sqd then
                if (temp_dist_sqd < dist_sqd) {

                    // 10.3.1 nearest := temp_nearest and dist_sqd := temp_dist_sqd
                    nearest = temp_nearest;
                    dist_sqd = temp_dist_sqd;
                }
            }

            // SDL: otherwise, current point is nearest
            else if (pivot_to_target < max_dist_sqd) {
                nearest = kd;
                dist_sqd = pivot_to_target;
            }
        }

        // constructor is used only by class; other methods are static
        private KDNode(HPoint key, Object val) {

            k = key;
            v = val;
            left = null;
            right = null;
            deleted = false;
        }

        protected String toString(int depth) {
            String s = k + "  " + v + (deleted ? "*" : "");
            if (left != null) {
                s = s + "\n" + pad(depth) + "L " + left.toString(depth + 1);
            }
            if (right != null) {
                s = s + "\n" + pad(depth) + "R " + right.toString(depth + 1);
            }
            return s;
        }

        private static String pad(int n) {
            String s = "";
            for (int i = 0; i < n; ++i) {
                s += " ";
            }
            return s;
        }

        private static void hrcopy(HRect hr_src, HRect hr_dst) {
            hpcopy(hr_src.min, hr_dst.min);
            hpcopy(hr_src.max, hr_dst.max);
        }

        private static void hpcopy(HPoint hp_src, HPoint hp_dst) {
            for (int i = 0; i < hp_dst.coord.length; ++i) {
                hp_dst.coord[i] = hp_src.coord[i];
            }
        }
    }

    static class HRect {

        protected HPoint min;
        protected HPoint max;

        protected HRect(int ndims) {
            min = new HPoint(ndims);
            max = new HPoint(ndims);
        }

        protected HRect(HPoint vmin, HPoint vmax) {

            min = (HPoint) vmin.clone();
            max = (HPoint) vmax.clone();
        }

        protected Object clone() {

            return new HRect(min, max);
        }

        // from Moore's eqn. 6.6
        protected HPoint closest(HPoint t) {

            HPoint p = new HPoint(t.coord.length);

            for (int i = 0; i < t.coord.length; ++i) {
                if (t.coord[i] <= min.coord[i]) {
                    p.coord[i] = min.coord[i];
                } else if (t.coord[i] >= max.coord[i]) {
                    p.coord[i] = max.coord[i];
                } else {
                    p.coord[i] = t.coord[i];
                }
            }

            return p;
        }

        // used in initial conditions of KDTree.nearest()
        protected static HRect infiniteHRect(int d) {

            HPoint vmin = new HPoint(d);
            HPoint vmax = new HPoint(d);

            for (int i = 0; i < d; ++i) {
                vmin.coord[i] = Double.NEGATIVE_INFINITY;
                vmax.coord[i] = Double.POSITIVE_INFINITY;
            }

            return new HRect(vmin, vmax);
        }

        // currently unused
        protected HRect intersection(HRect r) {

            HPoint newmin = new HPoint(min.coord.length);
            HPoint newmax = new HPoint(min.coord.length);

            for (int i = 0; i < min.coord.length; ++i) {
                newmin.coord[i] = Math.max(min.coord[i], r.min.coord[i]);
                newmax.coord[i] = Math.min(max.coord[i], r.max.coord[i]);
                if (newmin.coord[i] >= newmax.coord[i])
                    return null;
            }

            return new HRect(newmin, newmax);
        }

        // currently unused
        protected double area() {

            double a = 1;

            for (int i = 0; i < min.coord.length; ++i) {
                a *= (max.coord[i] - min.coord[i]);
            }

            return a;
        }

        public String toString() {
            return min + "\n" + max + "\n";
        }
    }

    static class HPoint {

        protected double[] coord;

        protected HPoint(int n) {
            coord = new double[n];
        }

        protected HPoint(double[] x) {

            coord = new double[x.length];
            for (int i = 0; i < x.length; ++i)
                coord[i] = x[i];
        }

        protected Object clone() {

            return new HPoint(coord);
        }

        protected boolean equals(HPoint p) {

            // seems faster than java.util.Arrays.equals(), which is not
            // currently supported by Matlab anyway
            for (int i = 0; i < coord.length; ++i)
                if (coord[i] != p.coord[i])
                    return false;

            return true;
        }

        protected static double sqrdist(HPoint x, HPoint y) {

            double dist = 0;

            for (int i = 0; i < x.coord.length; ++i) {
                double diff = (x.coord[i] - y.coord[i]);
                dist += (diff * diff);
            }

            return dist;

        }

        protected static double eucdist(HPoint x, HPoint y) {

            return Math.sqrt(sqrdist(x, y));
        }

        public String toString() {
            String s = "";
            for (int i = 0; i < coord.length; ++i) {
                s = s + coord[i] + " ";
            }
            return s;
        }

    }

}
