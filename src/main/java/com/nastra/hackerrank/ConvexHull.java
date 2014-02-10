package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.StringTokenizer;

public class ConvexHull {

	public static void main(String[] args) throws Exception {
		FastScanner sc = new FastScanner(System.in);
		int T = sc.nextInt();
		Point[] points = new Point[T];
		for (int i = 0; i < T; i++) {
			String[] line = sc.nextLine().split(" ");
			int x = Integer.parseInt(line[0]);
			int y = Integer.parseInt(line[1]);
			Point p = new Point(x, y);
			points[i] = p;
		}

		Stack<Point> hull = CH.convexHull(points);
		double dist = 0.0;
		Point start = hull.pop();
		Point p = start;
		while (!hull.isEmpty()) {
			dist += p.distanceTo(hull.peek());
			p = hull.pop();
		}
		dist += p.distanceTo(start);
		System.out.printf("%.1f", dist);
	}

	private static class CH {

		/**
		 * Algorithm runs in time O(N log N) and is dominated by sorting all the
		 * points.
		 * 
		 * @param points
		 *            All the points
		 * @return All points that lie on the convex hull
		 */
		public static Stack<Point> convexHull(Point[] points) {
			Stack<Point> hull = new Stack<Point>();

			Arrays.sort(points, Point.Y_ORDER);
			Arrays.sort(points, points[0].POLAR_ORDER);
			hull.push(points[0]);
			hull.push(points[1]);

			// find index k1 of first point not equal to points[0]
			int k1;
			for (k1 = 1; k1 < points.length; k1++)
				if (!points[0].equals(points[k1]))
					break;
			if (k1 == points.length)
				return hull; // all points equal

			// find index k2 of first point not collinear with points[0] and
			// points[k1]
			int k2;
			for (k2 = k1 + 1; k2 < points.length; k2++)
				if (Point.ccw(points[0], points[k1], points[k2]) != 0)
					break;

			hull.push(points[k2 - 1]); // points[k2-1] is second extreme point

			for (int i = 2; i < points.length; i++) {
				Point top = hull.pop();

				while (!hull.isEmpty()
						&& Point.ccw(hull.peek(), top, points[i]) <= 0) {
					// remove points that would otherwise create a clockwise
					// turn
					top = hull.pop();
				}
				hull.push(top);
				hull.push(points[i]);
			}

			return hull;
		}

	}

	public static class Point {
		private final int x, y;

		/**
		 * Compares two points by y-coordinate.
		 */
		static final Comparator<Point> Y_ORDER = new YOrder();

		/**
		 * Compares two points by polar angle (between 0 and 2pi) with respect
		 * to this point.
		 */
		final Comparator<Point> POLAR_ORDER = new PolarOrder();

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
		 * @return { -1, 0, +1 } if a->b->c is a { clockwise, collinear;
		 *         counterclocwise } turn.
		 */
		public static int ccw(Point a, Point b, Point c) {
			double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y)
					* (c.x - a.x);
			if (area2 < 0)
				return -1;
			else if (area2 > 0)
				return +1;
			else
				return 0;
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
		 * Returns the square of the Euclidean distance between this point and
		 * that point.
		 * 
		 * @param that
		 *            the other point
		 * @return the square of the Euclidean distance between this point and
		 *         that point
		 */
		public double distanceSquaredTo(Point that) {
			double dx = this.x - that.x;
			double dy = this.y - that.y;
			return dx * dx + dy * dy;
		}

		/**
		 * Compares this point to that point by y-coordinate, breaking ties by
		 * x-coordinate.
		 * 
		 * @param that
		 *            the other point
		 * @return { a negative integer, zero, a positive integer } if this
		 *         point is { less than, equal to, greater than } that point
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

		// compare other points relative to polar angle (between 0 and 2pi)
		// they
		// make with this Point
		private class PolarOrder implements Comparator<Point> {
			public int compare(Point q1, Point q2) {
				double dx1 = q1.x - x;
				double dy1 = q1.y - y;
				double dx2 = q2.x - x;
				double dy2 = q2.y - y;

				if (dy1 >= 0 && dy2 < 0)
					return -1; // q1 above; q2 below
				else if (dy2 >= 0 && dy1 < 0)
					return +1; // q1 below; q2 above
				else if (dy1 == 0 && dy2 == 0) { // 3-collinear and
													// horizontal
					if (dx1 >= 0 && dx2 < 0)
						return -1;
					else if (dx2 >= 0 && dx1 < 0)
						return +1;
					else
						return 0;
				} else
					return -ccw(Point.this, q1, q2); // both above or below

				// Note: ccw() recomputes dx1, dy1, dx2, and dy2
			}
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}

	}

	/**
	 * 
	 * @author nastra - Eduard Tudenhoefner
	 */
	public static class FastScanner {

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
	}

}
