package com.nastra.hackerrank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Median {
	private static final String SPACE = " ";

	private static RedBlackBST<Integer, List<Integer>> tree = new RedBlackBST<Integer, List<Integer>>();

	private static final String WRONG = "Wrong!";

	private static final String ADD = "a";

	private static final String RM = "r";

	public static void main(String args[]) {

		Scanner in = new Scanner(System.in);

		int N = in.nextInt();

		String operation[] = new String[N];
		int value[] = new int[N];

		for (int i = 0; i < N; i++) {
			operation[i] = in.next();
			value[i] = in.nextInt();
		}
		in.close();

		StringBuilder out = new StringBuilder();
		for (int i = 0; i < operation.length; i++) {
			String op = operation[i];
			if (ADD.equals(op)) {
				out.append(add(value[i]));
			} else if (RM.equals(op)) {
				out.append(remove(value[i]));
			}
			out.append(SPACE);
		}
		System.out.println(out.toString());
	}

	private static String add(Integer x) {
		List<Integer> values = tree.get(x);
		if (null == values) {
			values = new ArrayList<Integer>();
		}
		values.add(x);
		tree.put(x, values);

		return printMedian();
	}

	private static String remove(Integer x) {
		List<Integer> values = tree.get(x);
		if (null == values || values.isEmpty()) {
			return WRONG;
		} else {
			return printMedian();
		}
	}

	private static String printMedian() {
		int size = tree.size();
		Integer key = tree.select(size / 2);
		List<Integer> values = tree.get(key);
		if (null == values || values.isEmpty()) {
			return WRONG;
		}

		Integer median = values.get(0);

		if (size % 2 == 0) {
			Integer succ = null;
			if (values.size() > 1) {
				succ = values.get(1);
			} else {
				succ = tree.successor(key);
			}
			Double med = (median + succ) * 0.5;
			return med.toString();
		} else {
			return median.toString();
		}
	}

	private static class RedBlackBST<Key extends Comparable<Key>, Value> {

		private static final boolean RED = true;

		private static final boolean BLACK = false;

		private Node root; // root of the BST

		// BST helper node data type
		private class Node {
			private Key key; // key

			private Value val; // associated data

			private Node left, right; // links to left and right subtrees

			private boolean color; // color of parent link

			private int N; // subtree count

			public Node(Key key, Value val, boolean color, int N) {
				this.key = key;
				this.val = val;
				this.color = color;
				this.N = N;
			}
		}

		/*************************************************************************
		 * Node helper methods
		 *************************************************************************/
		// is node x red; false if x is null ?
		private boolean isRed(Node x) {
			if (x == null)
				return false;
			return (x.color == RED);
		}

		// number of node in subtree rooted at x; 0 if x is null
		private int size(Node x) {
			if (x == null)
				return 0;
			return x.N;
		}

		/*************************************************************************
		 * Size methods
		 *************************************************************************/

		// return number of key-value pairs in this symbol table
		public int size() {
			return size(root);
		}

		// is this symbol table empty?
		public boolean isEmpty() {
			return root == null;
		}

		/*************************************************************************
		 * Standard BST search
		 *************************************************************************/

		// value associated with the given key; null if no such key
		public Value get(Key key) {
			return get(root, key);
		}

		// value associated with the given key in subtree rooted at x; null if
		// no such key
		private Value get(Node x, Key key) {
			while (x != null) {
				int cmp = key.compareTo(x.key);
				if (cmp < 0)
					x = x.left;
				else if (cmp > 0)
					x = x.right;
				else
					return x.val;
			}
			return null;
		}

		// is there a key-value pair with the given key?
		public boolean contains(Key key) {
			return (get(key) != null);
		}

		// is there a key-value pair with the given key in the subtree rooted at
		// x?
		private boolean contains(Node x, Key key) {
			return (get(x, key) != null);
		}

		/*************************************************************************
		 * Red-black insertion
		 *************************************************************************/

		// insert the key-value pair; overwrite the old value with the new value
		// if the key is already present
		public void put(Key key, Value val) {
			root = put(root, key, val);
			root.color = BLACK;
			assert check();
		}

		// insert the key-value pair in the subtree rooted at h
		private Node put(Node h, Key key, Value val) {
			if (h == null)
				return new Node(key, val, RED, 1);

			int cmp = key.compareTo(h.key);
			if (cmp < 0)
				h.left = put(h.left, key, val);
			else if (cmp > 0)
				h.right = put(h.right, key, val);
			else
				h.val = val;

			// fix-up any right-leaning links
			if (isRed(h.right) && !isRed(h.left))
				h = rotateLeft(h);
			if (isRed(h.left) && isRed(h.left.left))
				h = rotateRight(h);
			if (isRed(h.left) && isRed(h.right))
				flipColors(h);
			h.N = size(h.left) + size(h.right) + 1;

			return h;
		}

		/*************************************************************************
		 * Red-black deletion
		 *************************************************************************/

		// delete the key-value pair with the minimum key
		public void deleteMin() {
			if (isEmpty())
				throw new NoSuchElementException("BST underflow");

			// if both children of root are black, set root to red
			if (!isRed(root.left) && !isRed(root.right))
				root.color = RED;

			root = deleteMin(root);
			if (!isEmpty())
				root.color = BLACK;
			assert check();
		}

		// delete the key-value pair with the minimum key rooted at h
		private Node deleteMin(Node h) {
			if (h.left == null)
				return null;

			if (!isRed(h.left) && !isRed(h.left.left))
				h = moveRedLeft(h);

			h.left = deleteMin(h.left);
			return balance(h);
		}

		// delete the key-value pair with the maximum key
		public void deleteMax() {
			if (isEmpty())
				throw new NoSuchElementException("BST underflow");

			// if both children of root are black, set root to red
			if (!isRed(root.left) && !isRed(root.right))
				root.color = RED;

			root = deleteMax(root);
			if (!isEmpty())
				root.color = BLACK;
			assert check();
		}

		// delete the key-value pair with the maximum key rooted at h
		private Node deleteMax(Node h) {
			if (isRed(h.left))
				h = rotateRight(h);

			if (h.right == null)
				return null;

			if (!isRed(h.right) && !isRed(h.right.left))
				h = moveRedRight(h);

			h.right = deleteMax(h.right);

			return balance(h);
		}

		// delete the key-value pair with the given key
		public void delete(Key key) {
			if (!contains(key)) {
				System.err.println("symbol table does not contain " + key);
				return;
			}

			// if both children of root are black, set root to red
			if (!isRed(root.left) && !isRed(root.right))
				root.color = RED;

			root = delete(root, key);
			if (!isEmpty())
				root.color = BLACK;
			assert check();
		}

		// delete the key-value pair with the given key rooted at h
		private Node delete(Node h, Key key) {
			assert contains(h, key);

			if (key.compareTo(h.key) < 0) {
				if (!isRed(h.left) && !isRed(h.left.left))
					h = moveRedLeft(h);
				h.left = delete(h.left, key);
			} else {
				if (isRed(h.left))
					h = rotateRight(h);
				if (key.compareTo(h.key) == 0 && (h.right == null))
					return null;
				if (!isRed(h.right) && !isRed(h.right.left))
					h = moveRedRight(h);
				if (key.compareTo(h.key) == 0) {
					Node x = min(h.right);
					h.key = x.key;
					h.val = x.val;
					// h.val = get(h.right, min(h.right).key);
					// h.key = min(h.right).key;
					h.right = deleteMin(h.right);
				} else
					h.right = delete(h.right, key);
			}
			return balance(h);
		}

		/*************************************************************************
		 * red-black tree helper functions
		 *************************************************************************/

		// make a left-leaning link lean to the right
		private Node rotateRight(Node h) {
			assert (h != null) && isRed(h.left);
			Node x = h.left;
			h.left = x.right;
			x.right = h;
			x.color = x.right.color;
			x.right.color = RED;
			x.N = h.N;
			h.N = size(h.left) + size(h.right) + 1;
			return x;
		}

		// make a right-leaning link lean to the left
		private Node rotateLeft(Node h) {
			assert (h != null) && isRed(h.right);
			Node x = h.right;
			h.right = x.left;
			x.left = h;
			x.color = x.left.color;
			x.left.color = RED;
			x.N = h.N;
			h.N = size(h.left) + size(h.right) + 1;
			return x;
		}

		// flip the colors of a node and its two children
		private void flipColors(Node h) {
			// h must have opposite color of its two children
			assert (h != null) && (h.left != null) && (h.right != null);
			assert (!isRed(h) && isRed(h.left) && isRed(h.right)) || (isRed(h) && !isRed(h.left) && !isRed(h.right));
			h.color = !h.color;
			h.left.color = !h.left.color;
			h.right.color = !h.right.color;
		}

		// Assuming that h is red and both h.left and h.left.left
		// are black, make h.left or one of its children red.
		private Node moveRedLeft(Node h) {
			assert (h != null);
			assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

			flipColors(h);
			if (isRed(h.right.left)) {
				h.right = rotateRight(h.right);
				h = rotateLeft(h);
			}
			return h;
		}

		// Assuming that h is red and both h.right and h.right.left
		// are black, make h.right or one of its children red.
		private Node moveRedRight(Node h) {
			assert (h != null);
			assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
			flipColors(h);
			if (isRed(h.left.left)) {
				h = rotateRight(h);
			}
			return h;
		}

		// restore red-black tree invariant
		private Node balance(Node h) {
			assert (h != null);

			if (isRed(h.right))
				h = rotateLeft(h);
			if (isRed(h.left) && isRed(h.left.left))
				h = rotateRight(h);
			if (isRed(h.left) && isRed(h.right))
				flipColors(h);

			h.N = size(h.left) + size(h.right) + 1;
			return h;
		}

		/*************************************************************************
		 * Utility functions
		 *************************************************************************/

		// height of tree (1-node tree has height 0)
		public int height() {
			return height(root);
		}

		private int height(Node x) {
			if (x == null)
				return -1;
			return 1 + Math.max(height(x.left), height(x.right));
		}

		/*************************************************************************
		 * Ordered symbol table methods.
		 *************************************************************************/

		// the smallest key; null if no such key
		public Key min() {
			if (isEmpty())
				return null;
			return min(root).key;
		}

		// the smallest key in subtree rooted at x; null if no such key
		private Node min(Node x) {
			assert x != null;
			if (x.left == null)
				return x;
			else
				return min(x.left);
		}

		// the largest key; null if no such key
		public Key max() {
			if (isEmpty())
				return null;
			return max(root).key;
		}

		// the largest key in the subtree rooted at x; null if no such key
		private Node max(Node x) {
			assert x != null;
			if (x.right == null)
				return x;
			else
				return max(x.right);
		}

		// the largest key less than or equal to the given key
		public Key floor(Key key) {
			Node x = floor(root, key);
			if (x == null)
				return null;
			else
				return x.key;
		}

		// the largest key in the subtree rooted at x less than or equal to the
		// given key
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

		// the smallest key greater than or equal to the given key
		public Key ceiling(Key key) {
			Node x = ceiling(root, key);
			if (x == null)
				return null;
			else
				return x.key;
		}

		public Key successor(Key key) {
			Node x = successor(root, key, null);
			if (null != x) {
				return x.key;
			}
			return null;
		}

		// the smallest key in the subtree rooted at x greater than or equal to
		// the given key
		private Node ceiling(Node x, Key key) {
			if (x == null)
				return null;
			int cmp = key.compareTo(x.key);
			if (cmp == 0)
				return x;
			if (cmp > 0)
				return ceiling(x.right, key);
			Node t = ceiling(x.left, key);
			if (t != null)
				return t;
			else
				return x;
		}

		private Node successor(Node n, Key key, Node prev) {
			if (null == n) {
				return prev;
			}
			int cmp = key.compareTo(n.key);
			if (cmp == 0 && null != n.right) {
				return min(n.right);
			}
			if (cmp < 0) {
				return successor(n.left, key, n);
			} else if (cmp > 0) {
				return successor(n.right, key, prev);
			}
			return prev;
			// if (cmp > 0)
			// return successor(x.right, key, prev);
			// Node t = ceiling(x.left, key);
			// if (t != null)
			// return t;
			// else
			// return x;
		}

		// the key of rank k
		public Key select(int k) {
			if (k < 0 || k >= size())
				return null;
			Node x = select(root, k);
			return x.key;
		}

		// the key of rank k in the subtree rooted at x
		private Node select(Node x, int k) {
			assert x != null;
			assert k >= 0 && k < size(x);
			int t = size(x.left);
			if (t > k)
				return select(x.left, k);
			else if (t < k)
				return select(x.right, k - t - 1);
			else
				return x;
		}

		// number of keys less than key
		public int rank(Key key) {
			return rank(key, root);
		}

		// number of keys less than key in the subtree rooted at x
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

		// all of the keys, as an Iterable
		public Iterable<Key> keys() {
			return keys(min(), max());
		}

		// the keys between lo and hi, as an Iterable
		public Iterable<Key> keys(Key lo, Key hi) {
			Queue<Key> queue = new Queue<Key>();
			// if (isEmpty() || lo.compareTo(hi) > 0) return queue;
			keys(root, queue, lo, hi);
			return queue;
		}

		// add the keys between lo and hi in the subtree rooted at x
		// to the queue
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

		// number keys between lo and hi
		public int size(Key lo, Key hi) {
			if (lo.compareTo(hi) > 0)
				return 0;
			if (contains(hi))
				return rank(hi) - rank(lo) + 1;
			else
				return rank(hi) - rank(lo);
		}

		/*************************************************************************
		 * Check integrity of red-black BST data structure
		 *************************************************************************/
		private boolean check() {
			return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
		}

		// does this binary tree satisfy symmetric order?
		// Note: this test also ensures that data structure is a binary tree
		// since order is strict
		private boolean isBST() {
			return isBST(root, null, null);
		}

		// is the tree rooted at x a BST with all keys strictly between min and
		// max
		// (if min or max is null, treat as empty constraint)
		// Credit: Bob Dondero's elegant solution
		private boolean isBST(Node x, Key min, Key max) {
			if (x == null)
				return true;
			if (min != null && x.key.compareTo(min) <= 0)
				return false;
			if (max != null && x.key.compareTo(max) >= 0)
				return false;
			return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
		}

		// are the size fields correct?
		private boolean isSizeConsistent() {
			return isSizeConsistent(root);
		}

		private boolean isSizeConsistent(Node x) {
			if (x == null)
				return true;
			if (x.N != size(x.left) + size(x.right) + 1)
				return false;
			return isSizeConsistent(x.left) && isSizeConsistent(x.right);
		}

		// check that ranks are consistent
		private boolean isRankConsistent() {
			for (int i = 0; i < size(); i++)
				if (i != rank(select(i)))
					return false;
			for (Key key : keys())
				if (key.compareTo(select(rank(key))) != 0)
					return false;
			return true;
		}

		// Does the tree have no red right links, and at most one (left)
		// red links in a row on any path?
		private boolean is23() {
			return is23(root);
		}

		private boolean is23(Node x) {
			if (x == null)
				return true;
			if (isRed(x.right))
				return false;
			if (x != root && isRed(x) && isRed(x.left))
				return false;
			return is23(x.left) && is23(x.right);
		}

		// do all paths from root to leaf have same number of black edges?
		private boolean isBalanced() {
			int black = 0; // number of black links on path from root to min
			Node x = root;
			while (x != null) {
				if (!isRed(x))
					black++;
				x = x.left;
			}
			return isBalanced(root, black);
		}

		// does every path from the root to a leaf have the given number of
		// black links?
		private boolean isBalanced(Node x, int black) {
			if (x == null)
				return black == 0;
			if (!isRed(x))
				black--;
			return isBalanced(x.left, black) && isBalanced(x.right, black);
		}
	}

	private static class Queue<Item> implements Iterable<Item> {
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
		 * @throws java.util.NoSuchElementException if this queue is empty
		 */
		public Item peek() {
			if (isEmpty())
				throw new NoSuchElementException("Queue underflow");
			return first.item;
		}

		/**
		 * Adds the item to this queue.
		 * 
		 * @param item the item to add
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
		 * Removes and returns the item on this queue that was least recently
		 * added.
		 * 
		 * @return the item on this queue that was least recently added
		 * @throws java.util.NoSuchElementException if this queue is empty
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
				s.append(item + SPACE);
			return s.toString();
		}

		/**
		 * Returns an iterator that iterates over the items in this queue in
		 * FIFO order.
		 * 
		 * @return an iterator that iterates over the items in this queue in
		 *         FIFO order
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
