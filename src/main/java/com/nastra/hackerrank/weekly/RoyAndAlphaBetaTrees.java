package com.nastra.hackerrank.weekly;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @see https://www.hackerrank.com/contests/w4/challenges/roy-and-alpha-beta-trees
 * @author nastra
 * 
 */
public class RoyAndAlphaBetaTrees {
    private static long MOD = 1000000009;
    static long[][][] dp;
    static long[] cnt;
    static long[] multipliers;
    static long[] tree;

    private static long calculate(int lo, int hi, int odd) {
        if (lo > hi) {
            return 0;
        }
        if (dp[lo][hi][odd] == -1) {
            long ans = 0;
            for (int i = lo; i <= hi; i++) {
                long leftQuantity = cnt[i - lo];
                long rightQuantity = cnt[hi - i];
                long left = calculate(lo, i - 1, 1 - odd) * rightQuantity % MOD;
                ans += left;
                ans = checkMod(ans);
                long right = calculate(i + 1, hi, 1 - odd) * leftQuantity % MOD;
                ans += right;
                ans = checkMod(ans);
                long totalTrees = (leftQuantity % MOD * rightQuantity % MOD) % MOD;
                long total = multipliers[odd] % MOD * tree[i] % MOD;
                ans += totalTrees % MOD * total % MOD;
                ans = checkMod(ans);
            }
            dp[lo][hi][odd] = ans;
        }
        return dp[lo][hi][odd];
    }

    private static long checkMod(long ans) {
        if (ans >= MOD)
            ans -= MOD;
        if (ans < 0)
            ans += MOD;
        return ans;
    }

    public static long[] generateCatalan(int n, long module) {
        long[] inv = generateReverse(n + 2, module);
        long[] Catalan = new long[n];
        Catalan[1] = 1;
        Catalan[0] = 1;
        for (int i = 1; i < n - 1; i++) {
            Catalan[i + 1] = (((2 * (2 * i + 1) * inv[i + 2]) % MOD) * Catalan[i]) % MOD;
        }
        return Catalan;
    }

    public static long[] generateReverse(int upTo, long module) {
        long[] result = new long[upTo];
        if (upTo > 1)
            result[1] = 1;
        for (int i = 2; i < upTo; i++)
            result[i] = (module - module / i * result[((int) (module % i))] % module) % module;
        return result;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int t = sc.nextInt();
        while (t > 0) {
            t--;
            int n = sc.nextInt();
            multipliers = sc.nextLongArray();
            tree = sc.nextLongArray();
            dp = new long[n][n][2];
            cnt = generateCatalan(n + 1, MOD);
            multipliers[1] = -multipliers[1];
            Arrays.sort(tree);
            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j < dp[i].length; j++) {
                    for (int k = 0; k < 2; k++) {
                        dp[i][j][k] = -1;
                    }
                }
            }
            System.out.println(calculate(0, n - 1, 0));
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
