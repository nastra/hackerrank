package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LibraryQuery {

    private static final String SPACE = " ";
    private static final String ONE = "1";

    public static int solv(FenwickTree[] trees, int low, int high, int k) {
        int res = 0;
        do {
            res++;
            k -= trees[res].rangeQuery(low, high);
        } while (k > 0);
        return res;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int N = sc.nextInt();
            FenwickTree[] trees = new FenwickTree[1000 + 1];
            for (int j = 1; j < trees.length; j++) {
                trees[j] = new FenwickTree(10000);
            }

            int[] shelf = new int[N + 1];

            String[] line = sc.nextLine().split(SPACE);
            for (int j = 1; j <= N; j++) {
                Integer books = Integer.valueOf(line[j - 1]);
                shelf[j] = books;
                trees[shelf[j]].update(j, 1); // WHY 1?
            }

            int Q = sc.nextInt();
            for (int j = 0; j < Q; j++) {
                String[] query = sc.nextLine().split(SPACE);
                if (ONE.equals(query[0])) {
                    int x = Integer.valueOf(query[1]);
                    int k = Integer.valueOf(query[2]);
                    int pv = shelf[x];
                    trees[k].update(x, 1);
                    trees[pv].update(x, -1);
                    shelf[x] = k;
                } else {
                    int x = Integer.valueOf(query[1]);
                    int y = Integer.valueOf(query[2]);
                    int k = Integer.valueOf(query[3]);
                    int rank = solv(trees, x, y, k);
                    System.out.println(rank);
                }
            }
        }
    }

    static class FenwickTree {

        private int[] tree;

        public FenwickTree(int size) {
            tree = new int[size + 1];
        }

        public int query(int idx) {
            int sum = 0;
            while (idx > 0) {
                sum += tree[idx];
                idx -= (idx & -idx);
            }
            return sum;
        }

        public void update(int idx, int val) {
            while (idx < tree.length) {
                tree[idx] += val;
                idx += (idx & -idx);
            }
        }

        public int rangeQuery(int left, int right) {
            return query(right) - query(left - 1);
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
    }

}
