package com.nastra.codechef.july;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/JULY14/problems/FROGV/
 * @author nastra
 * 
 */
public class ChefAndFrogs {
    private static Map<Integer, Integer> originalToSortedPos = new HashMap<Integer, Integer>();

    public static String solv(UnionFind uf, int x, int y) {
        int start = Math.min(originalToSortedPos.get(x), originalToSortedPos.get(y));
        int end = Math.max(originalToSortedPos.get(x), originalToSortedPos.get(y));

        if (!uf.connected(start, end)) {
            return "No";
        }
        return "Yes";
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int p = sc.nextInt();

        int[] frogs = sc.nextIntArray();
        int[] sorted = Arrays.copyOf(frogs, frogs.length);
        Arrays.sort(sorted);
        for (int i = 0; i < frogs.length; i++) {
            originalToSortedPos.put(i, Arrays.binarySearch(sorted, frogs[i]));
        }
        UnionFind uf = new UnionFind(sorted.length);
        for (int i = 1; i < sorted.length; i++) {
            int diff = sorted[i] - sorted[i - 1];
            if (diff <= k) {
                uf.union(i, i - 1);
            }
        }

        while (p > 0) {
            p--;
            int x = sc.nextInt();
            int y = sc.nextInt();
            System.out.println(solv(uf, x - 1, y - 1));
        }
    }

    static class UnionFind {

        private int[] parent; // id[i] = parent of i
        private int[] size; // sz[i] = number of objects in subtree rooted at i
        private int count; // number of components

        /**
         * Create an empty union find data structure with N isolated sets.
         * 
         * @throws java.lang.IllegalArgumentException
         *             if N < 0
         */
        public UnionFind(int N) {
            if (N < 0) {
                throw new IllegalArgumentException();
            }
            count = N;
            parent = new int[N];
            size = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        /**
         * Return the id of component corresponding to object p.
         * 
         * @throws java.lang.IndexOutOfBoundsException
         *             unless 0 <= p < N
         */
        public int find(int p) {
            if (p < 0 || p >= parent.length) {
                throw new IndexOutOfBoundsException();
            }
            while (p != parent[p]) {
                parent[p] = parent[parent[p]]; // implements path compression
                p = parent[p];
            }
            return p;
        }

        /**
         * Return the number of disjoint sets.
         */
        public int count() {
            return count;
        }

        /**
         * Are objects p and q in the same set?
         * 
         * @throws java.lang.IndexOutOfBoundsException
         *             unless both 0 <= p < N and 0 <= q < N
         */
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        /**
         * Replace sets containing p and q with their union.
         * 
         * @throws java.lang.IndexOutOfBoundsException
         *             unless both 0 <= p < N and 0 <= q < N
         */
        public void union(int p, int q) {
            int i = find(p);
            int j = find(q);
            if (i == j) {
                return;
            }

            // make smaller root point to larger one
            if (size[i] < size[j]) {
                parent[i] = j;
                size[j] += size[i];
            } else {
                parent[j] = i;
                size[i] += size[j];
            }
            count--;
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
