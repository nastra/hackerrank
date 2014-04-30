package com.nastra.hackerrank.weekly;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * 
 * @author nastra - Eduard Tudenhoefner
 */
public class BSTMaintenance {
    static BST<Integer, Integer> tree = new BST<Integer, Integer>();
    static int prev = 0;

    public static int solve(int[] values, int curr) {
        tree.put(values[curr], -1);
        if (tree.size() == 1) {
            return 0;
        }
        return tree.distanceOfAllPairs();
    }

    static int solv(int[] values, int curr) {
        tree.put(values[curr], -1);
        if (tree.size() == 1) {
            return 0;
        }
        int total = 0;
        Integer min = tree.min();
        for (Integer item : tree.keys()) {
            if (item.equals(min)) {
                continue;
            }
            total += tree.rank(item);
        }
        int tmp = prev;
        prev = total + tmp;
        return total + tmp;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int N = sc.nextInt();
        int[] values = sc.nextIntArray();
        for (int i = 0; i < N; i++) {
            System.out.println(solve(values, i));
            // System.out.println(solv(values, i));
        }
    }

    static class BST<Key extends Comparable<Key>, Value> {
        private Node root; // root of BST
        private int prevDistance = 0;
        private Node lastAdded = null;

        private class Node {
            private Key key; // sorted by key
            private Value val; // associated data
            private Node left, right; // left and right subtrees
            private int N; // number of nodes in subtree
            private int distFromRoot;

            public Node(Key key, Value val, int N) {
                this.key = key;
                this.val = val;
                this.N = N;
            }

            public Node(Key key, Value val, int N, int dist) {
                this.key = key;
                this.val = val;
                this.N = N;
                this.distFromRoot = dist;
            }

            @Override
            public String toString() {
                return "Node [key=" + key + ", N=" + N + ", distFromRoot=" + distFromRoot + "]";
            }
        }

        // is the symbol table empty?
        public boolean isEmpty() {
            return size() == 0;
        }

        // return number of key-value pairs in BST
        public int size() {
            return size(root);
        }

        // return number of key-value pairs in BST rooted at x
        private int size(Node x) {
            if (x == null)
                return 0;
            else
                return x.N;
        }

        public int distance(Node a, Node b) {
            Node lca = lca(root, a, b);
            if (null == lca) {
                return 0;
            }
            int lcaDist = lca.distFromRoot;
            int dist = (a.distFromRoot - lcaDist) + (b.distFromRoot - lcaDist);
            return dist;
        }

        public int distanceOfAllPairs() {
            int total = 0;
            Node x = lastAdded;
            for (Node item : nodeKeys()) {
                int dist = distance(x, item);
                total += dist;
            }
            int tmp = prevDistance;
            prevDistance = total + tmp;
            return total + tmp;
        }

        public Node lca(Key n1, Key n2) {
            return lca(root, n1, n2);
        }

        private Node lca(Node n, Key n1, Key n2) {
            while (n != null) {
                // If both n1 and n2 are smaller than root, then LCA lies in left
                int cmp1 = n.key.compareTo(n1);
                int cmp2 = n.key.compareTo(n2);
                if (cmp1 > 0 && cmp2 > 0)
                    n = n.left;

                // If both n1 and n2 are greater than root, then LCA lies in right
                else if (cmp1 < 0 && cmp2 < 0)
                    n = n.right;
                else
                    break;
            }
            return n;
        }

        private Node lca(Node n, Node n1, Node n2) {
            while (n != null) {
                // If both n1 and n2 are smaller than root, then LCA lies in left
                int cmp1 = n.key.compareTo(n1.key);
                int cmp2 = n.key.compareTo(n2.key);
                if (cmp1 > 0 && cmp2 > 0)
                    n = n.left;

                // If both n1 and n2 are greater than root, then LCA lies in right
                else if (cmp1 < 0 && cmp2 < 0)
                    n = n.right;
                else
                    break;
            }
            return n;
        }

        /***********************************************************************
         * Search BST for given key, and return associated value if found, return null if not found
         ***********************************************************************/
        // does there exist a key-value pair with given key?
        public boolean contains(Key key) {
            return get(key) != null;
        }

        // return value associated with the given key, or null if no such key exists
        public Value get(Key key) {
            return get(root, key);
        }

        private Value get(Node x, Key key) {
            if (x == null)
                return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                return get(x.left, key);
            else if (cmp > 0)
                return get(x.right, key);
            else
                return x.val;
        }

        private Node getNode(Node x, Key key) {
            if (x == null)
                return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                return getNode(x.left, key);
            else if (cmp > 0)
                return getNode(x.right, key);
            else
                return x;
        }

        /***********************************************************************
         * Insert key-value pair into BST If key already exists, update with new value
         ***********************************************************************/
        public void put(Key key, Value val) {
            if (val == null) {
                delete(key);
                return;
            }
            root = put(root, key, val, 0);
        }

        private Node put(Node x, Key key, Value val, int dist) {
            if (x == null) {
                lastAdded = new Node(key, val, 1, dist);
                return lastAdded;
            }
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x.left = put(x.left, key, val, dist + 1);
            else if (cmp > 0)
                x.right = put(x.right, key, val, dist + 1);
            else
                x.val = val;
            x.N = 1 + size(x.left) + size(x.right);
            x.distFromRoot = dist;
            return x;
        }

        /***********************************************************************
         * Delete
         ***********************************************************************/

        public void deleteMin() {
            if (isEmpty())
                throw new NoSuchElementException("Symbol table underflow");
            root = deleteMin(root);
        }

        private Node deleteMin(Node x) {
            if (x.left == null)
                return x.right;
            x.left = deleteMin(x.left);
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }

        public void deleteMax() {
            if (isEmpty())
                throw new NoSuchElementException("Symbol table underflow");
            root = deleteMax(root);
        }

        private Node deleteMax(Node x) {
            if (x.right == null)
                return x.left;
            x.right = deleteMax(x.right);
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }

        public void delete(Key key) {
            root = delete(root, key);
        }

        private Node delete(Node x, Key key) {
            if (x == null)
                return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x.left = delete(x.left, key);
            else if (cmp > 0)
                x.right = delete(x.right, key);
            else {
                if (x.right == null)
                    return x.left;
                if (x.left == null)
                    return x.right;
                Node t = x;
                x = min(t.right);
                x.right = deleteMin(t.right);
                x.left = t.left;
            }
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }

        /***********************************************************************
         * Min, max, floor, and ceiling
         ***********************************************************************/
        public Key min() {
            if (isEmpty())
                return null;
            return min(root).key;
        }

        private Node min(Node x) {
            if (x.left == null)
                return x;
            else
                return min(x.left);
        }

        private Node deepest(Node x) {
            if (null == x.left && null != x.right) {
                return x.right;
            }
            if (x.left == null)
                return x;
            else
                return deepest(x.left);
        }

        public Key max() {
            if (isEmpty())
                return null;
            return max(root).key;
        }

        private Node max(Node x) {
            if (x.right == null)
                return x;
            else
                return max(x.right);
        }

        public Key floor(Key key) {
            Node x = floor(root, key);
            if (x == null)
                return null;
            else
                return x.key;
        }

        private Node floor(Node x, Key key) {
            if (x == null)
                return null;
            int cmp = key.compareTo(x.key);
            if (cmp == 0)
                return x;
            if (cmp < 0)
                return floor(x.left, key);
            Node t = floor(x.right, key);
            if (t != null)
                return t;
            else
                return x;
        }

        public Key ceiling(Key key) {
            Node x = ceiling(root, key);
            if (x == null)
                return null;
            else
                return x.key;
        }

        private Node ceiling(Node x, Key key) {
            if (x == null)
                return null;
            int cmp = key.compareTo(x.key);
            if (cmp == 0)
                return x;
            if (cmp < 0) {
                Node t = ceiling(x.left, key);
                if (t != null)
                    return t;
                else
                    return x;
            }
            return ceiling(x.right, key);
        }

        /***********************************************************************
         * Rank and selection
         ***********************************************************************/
        public Key select(int k) {
            if (k < 0 || k >= size())
                return null;
            Node x = select(root, k);
            return x.key;
        }

        // Return key of rank k.
        private Node select(Node x, int k) {
            if (x == null)
                return null;
            int t = size(x.left);
            if (t > k)
                return select(x.left, k);
            else if (t < k)
                return select(x.right, k - t - 1);
            else
                return x;
        }

        public int rank(Key key) {
            return rank(key, root);
        }

        // Number of keys in the subtree less than key.
        private int rank(Key key, Node x) {
            if (x == null)
                return 0;
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                return rank(key, x.left);
            else if (cmp > 0)
                return 1 + size(x.left) + rank(key, x.right);
            else
                return size(x.left);
        }

        /***********************************************************************
         * Range count and range search.
         ***********************************************************************/
        public Iterable<Key> keys() {
            return keys(min(), max());
        }

        public Iterable<Node> nodeKeys() {
            return keys(min(root), max(root));
        }

        public Iterable<Node> keys(Node lo, Node hi) {
            Queue<Node> queue = new Queue<Node>();
            keys(root, queue, lo, hi);
            return queue;
        }

        private void keys(Node x, Queue<Node> queue, Node lo, Node hi) {
            if (x == null)
                return;
            int cmplo = lo.key.compareTo(x.key);
            int cmphi = hi.key.compareTo(x.key);
            if (cmplo < 0)
                keys(x.left, queue, lo, hi);
            if (cmplo <= 0 && cmphi >= 0)
                queue.enqueue(x);
            if (cmphi > 0)
                keys(x.right, queue, lo, hi);
        }

        public Iterable<Key> keys(Key lo, Key hi) {
            Queue<Key> queue = new Queue<Key>();
            keys(root, queue, lo, hi);
            return queue;
        }

        private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
            if (x == null)
                return;
            int cmplo = lo.compareTo(x.key);
            int cmphi = hi.compareTo(x.key);
            if (cmplo < 0)
                keys(x.left, queue, lo, hi);
            if (cmplo <= 0 && cmphi >= 0)
                queue.enqueue(x.key);
            if (cmphi > 0)
                keys(x.right, queue, lo, hi);
        }

        public int size(Key lo, Key hi) {
            if (lo.compareTo(hi) > 0)
                return 0;
            if (contains(hi))
                return rank(hi) - rank(lo) + 1;
            else
                return rank(hi) - rank(lo);
        }

        // height of this BST (one-node tree has height 0)
        public int height() {
            return height(root);
        }

        private int height(Node x) {
            if (x == null)
                return -1;
            return 1 + Math.max(height(x.left), height(x.right));
        }

        // level order traversal
        public Iterable<Key> levelOrder() {
            Queue<Key> keys = new Queue<Key>();
            Queue<Node> queue = new Queue<Node>();
            queue.enqueue(root);
            while (!queue.isEmpty()) {
                Node x = queue.dequeue();
                if (x == null)
                    continue;
                keys.enqueue(x.key);
                queue.enqueue(x.left);
                queue.enqueue(x.right);
            }
            return keys;
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
    }

    static class Queue<Item> implements Iterable<Item> {
        private int N; // number of elements on queue
        private Node<Item> first; // beginning of queue
        private Node<Item> last; // end of queue

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty queue.
         */
        public Queue() {
            first = null;
            last = null;
            N = 0;
        }

        /**
         * Is this queue empty?
         * 
         * @return true if this queue is empty; false otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this queue.
         * 
         * @return the number of items in this queue
         */
        public int size() {
            return N;
        }

        /**
         * Returns the item least recently added to this queue.
         * 
         * @return the item least recently added to this queue
         * @throws java.util.NoSuchElementException
         *             if this queue is empty
         */
        public Item peek() {
            if (isEmpty())
                throw new NoSuchElementException("Queue underflow");
            return first.item;
        }

        /**
         * Adds the item to this queue.
         * 
         * @param item
         *            the item to add
         */
        public void enqueue(Item item) {
            Node<Item> oldlast = last;
            last = new Node<Item>();
            last.item = item;
            last.next = null;
            if (isEmpty())
                first = last;
            else
                oldlast.next = last;
            N++;
        }

        /**
         * Removes and returns the item on this queue that was least recently added.
         * 
         * @return the item on this queue that was least recently added
         * @throws java.util.NoSuchElementException
         *             if this queue is empty
         */
        public Item dequeue() {
            if (isEmpty())
                throw new NoSuchElementException("Queue underflow");
            Item item = first.item;
            first = first.next;
            N--;
            if (isEmpty())
                last = null; // to avoid loitering
            return item;
        }

        /**
         * Returns a string representation of this queue.
         * 
         * @return the sequence of items in FIFO order, separated by spaces
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (Item item : this)
                s.append(item + " ");
            return s.toString();
        }

        /**
         * Returns an iterator that iterates over the items in this queue in FIFO order.
         * 
         * @return an iterator that iterates over the items in this queue in FIFO order
         */
        public Iterator<Item> iterator() {
            return new ListIterator<Item>(first);
        }

        // an iterator, doesn't implement remove() since it's optional
        private class ListIterator<Item> implements Iterator<Item> {
            private Node<Item> current;

            public ListIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext() {
                return current != null;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Item next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }
}
