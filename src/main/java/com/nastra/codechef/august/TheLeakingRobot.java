package com.nastra.codechef.august;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/AUG14/problems/CRAWA/
 * @author nastra
 * 
 */
public class TheLeakingRobot {
    static int cnt = 0;

    private static List<Point> generatePoints(Point startingPoint, int cnt) {
        List<Point> points = new ArrayList<>();
        points.add(startingPoint);
        int x = startingPoint.x;
        int y = startingPoint.y;
        int i = 0;
        while (i <= 4) {
            int mod = cnt % 4;
            if (mod == 1) {
                // +x
                x = x + cnt;
            } else if (mod == 2) {
                // +y
                y = y + cnt;
            } else if (mod == 3) {
                // -x
                x = x - cnt;
            } else {
                // -y
                y = y - cnt;
            }
            Point p = new Point(x, y);
            points.add(p);
            cnt++;
            i++;
        }
        return points;
    }

    private static Point findStartingPointForSection(int x, int y) {
        int max = Math.max(Math.abs(x), Math.abs(y));
        int a, b;
        if (max % 2 == 0) {
            if (x < 0 && y < 0 && y < x && y % 2 == 0) {
                a = -(max);
                b = a;
            } else if (max > 0) {
                a = -(max - 2);
                b = a;
            } else {
                a = 0;
                b = 0;
            }
        } else {
            a = -(max - 1);
            b = a;
        }

        Point start = new Point(a, b);
        cnt = Math.abs(start.x) / 2 * 4 + 1;
        return start;
    }

    public static boolean isPointOnLeakingLine(Point p) {
        int x = p.x;
        int y = p.y;

        List<Point> points = generatePoints(findStartingPointForSection(x, y), cnt);
        for (int i = 1; i < points.size(); i++) {
            Point a = points.get(i - 1);
            Point b = points.get(i);
            if (p.equals(a) || p.equals(b) || onLine(a, b, p)) {
                return true;
            }
        }
        return false;
    }

    private static boolean onLine(Point a, Point b, Point c) {
        int x1 = a.x;
        int x2 = b.x;
        int y1 = a.y;
        int y2 = b.y;
        return (c.x >= x1 && c.x <= x2 && c.y >= y1 && c.y <= y2) || (c.x <= x1 && c.x >= x2 && c.y <= y1 && c.y >= y2);
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = sc.nextInt();
        while (t > 0) {
            t--;
            Point p = new Point(sc.nextInt(), sc.nextInt());
            boolean result = isPointOnLeakingLine(p);
            out.println(result ? "YES" : "NO");
        }

        out.close();
    }

    public static class Point implements Comparable<Point> {

        public final Comparator<Point> SLOPE_ORDER = new SlopeComparator();

        public int x, y;

        public Point(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        @Override
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

        public double slopeTo(Point that) {
            // degenerate line segments
            if (this.x == that.x && this.y == that.y) {
                return Double.NEGATIVE_INFINITY;
            }

            double yy = that.y - this.y;
            double xx = that.x - this.x;

            // horizontal line segments
            if (yy == 0.0) {
                return +0.0;
            }

            // vertical line segments
            if (xx == 0.0) {
                return Double.POSITIVE_INFINITY;
            }

            return yy / xx;
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

        private class SlopeComparator implements Comparator<Point> {

            @Override
            public int compare(Point one, Point two) {
                double firstSlope = slopeTo(one);
                double secondSlope = slopeTo(two);

                return Double.compare(firstSlope, secondSlope);
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
