package com.nastra.codechef;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/problems/FLIPCOIN
 * @author nastra
 * 
 */
public class FlippingCoins {

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        int q = sc.nextInt();
        int[] num = new int[n];
        SegmentTreeForRangeSum st = new SegmentTreeForRangeSum(num);
        while (q > 0) {
            q--;
            if (sc.nextInt() == 0) {
                // flip coins from a..b
                int a = sc.nextInt();
                int b = sc.nextInt();
                st.update(a, b);
            } else {
                // how many in a..b are heads up?
                int a = sc.nextInt();
                int b = sc.nextInt();
                out.println(st.getSum(a, b));
            }
        }

        out.close();
    }

    private static class SegmentTreeForRangeSum {
        private Node root;

        private class Node {
            int start;
            int end;
            Node left;
            Node right;
            int flips;
            int val;

            public Node(int start, int end) {
                super();
                this.start = start;
                this.end = end;
            }

        }

        public SegmentTreeForRangeSum(int[] a) {
            root = put(root, 0, a.length - 1, a);
        }

        public Node put(Node n, int start, int end, int[] a) {
            if (null == n) {
                n = new Node(start, end);
            }

            if (start < end) {
                int mid = mid(start, end);
                n.left = put(n.left, start, mid, a);
                n.right = put(n.right, mid + 1, end, a);
                n.val = valueOf(n.left) + valueOf(n.right);
            } else if (start == end) {
                // leaf node
                n.val = a[start];
            } else {
                return null;
            }
            return n;
        }

        private int valueOf(Node x) {
            if (null == x) {
                return 0;
            }
            return x.val;
        }

        private int mid(int startSegment, int endSegment) {
            return startSegment + (endSegment - startSegment) / 2;
        }

        public int getSum(int queryStart, int queryEnd) {
            return query(root, queryStart, queryEnd);
        }

        private int query(Node n, int start, int end) {
            if (null == n) {
                return 0;
            }
            if (n.flips != 0) {
                int flips = n.flips % 2;
                for (int i = 0; i < flips; i++) {
                    n.val = (n.end - n.start + 1) - n.val;
                }
                if (null != n.left) {
                    n.left.flips += flips;
                }
                if (null != n.right) {
                    n.right.flips += flips;
                }
                n.flips = 0;
            }

            if (n.start > end || n.end < start) {
                return 0;
            }
            if (n.start >= start && n.end <= end) {
                return n.val;
            }
            return query(n.left, start, end) + query(n.right, start, end);
        }

        public void update(int queryStart, int queryEnd) {
            updateTree(root, queryStart, queryEnd);
        }

        private void updateTree(Node n, int start, int end) {
            if (null == n) {
                return;
            }
            if (n.flips != 0) {
                // node is lazy
                int flips = n.flips % 2;
                for (int i = 0; i < flips; i++) {
                    n.val = (n.end - n.start + 1) - n.val;
                }

                if (null != n.left) {
                    n.left.flips += flips;
                }
                if (null != n.right) {
                    n.right.flips += flips;
                }
                n.flips = 0;
            }

            if (n.start > end || n.end < start) {
                return;
            }
            if (n.start >= start && n.end <= end) {
                n.val = (n.end - n.start + 1) - n.val;
                if (null != n.left) {
                    n.left.flips += 1;
                }
                if (null != n.right) {
                    n.right.flips += 1;
                }
                return;
            }
            updateTree(n.left, start, end);
            updateTree(n.right, start, end);
            n.val = valueOf(n.left) + valueOf(n.right);
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
