package com.nastra.hackerrank.weekly;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * 
 * @author nastra
 * 
 * @see https://www.hackerrank.com/contests/w4/challenges/crush
 * 
 */
public class AlgorithmicCrush {

    private static FenwickTree fw;

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int N = sc.nextInt();
        int Q = sc.nextInt();
        fw = new FenwickTree(N);

        while (Q > 0) {
            Q--;
            int[] query = sc.nextIntArray();
            int left = query[0];
            int right = query[1];
            int val = query[2];

            fw.update(left, right, val);
        }
        System.out.println(fw.max(N));
    }

    static class FenwickTree {
        long[] tree;

        public FenwickTree(int size) {
            tree = new long[size + 1];
        }

        public long query(int index) {
            long sum = 0;
            while (index > 0) {
                sum += tree[index];
                index -= index & -index;
            }

            return sum;
        }

        public void update(int index, long val) {
            while (index < tree.length) {
                tree[index] += val;
                index += index & (-index);
            }

        }

        public void update(int left, int right, long val) {
            update(left, val);
            update(right + 1, -val);
        }

        public long max(int index) {
            // long max = 0;
            // long sum = 0;
            // long max1 = 0;
            // while (index > 0) {
            // long val = tree[index];
            // if (val > 0) {
            // max1 += val;
            // }
            // sum += val;
            // index -= index & -index;
            // }
            // for (int i = 0; i < tree.length; i++) {
            // max = Math.max(max, tree[i]);
            // }
            // return max1;
            long max = 0;
            for (int i = tree.length - 1; i > 0; i--) {
                max = Math.max(max, query(i));
            }
            return max;
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
}
