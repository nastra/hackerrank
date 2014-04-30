package com.nastra.hackerrank.weekly;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @see https://www.hackerrank.com/contests/w1/challenges/volleyball-match
 * @author nastra - Eduard Tudenhoefner
 */
public class VolleyballMatch {
    private static long[][] dp = new long[25][25];

    private static long MOD = 1000000007L;

    public static long solve(int a, int b) {
        if (a < 25 && b < 25) {
            return 0;
        }

        int winner = 0;
        int loser = 0;
        if (a < b) {
            winner = b;
            loser = a;
        } else {
            winner = a;
            loser = b;
        }
        int diff = winner - loser;
        if (diff == 1 || winner == loser) {
            return 0;
        }

        if (winner > 25 && loser < 24) {
            return 0;
        }

        if (loser == 24 && diff > 2) {
            return 0;
        }

        if (diff > 2 && winner >= 25 && loser >= 25) {
            return 0;
        }

        diff = Math.max(winner, loser) - 24;
        long result = recursive(Math.min(winner, 24), Math.min(loser, 24));
        if (winner <= 25) {
            return result;
        }
        diff -= 2;
        result = (result * modpow(2, diff, MOD)) % MOD;
        return result;
    }

    static long modpow(int a, int b, long mod) {
        if (b == 0)
            return 1;
        long ret = modpow(a, b / 2, mod);
        ret = (ret * ret) % mod;
        if (b % 2 == 1)
            ret = (ret * a) % mod;
        return ret;
    }

    private static long recursive(int a, int b) {
        if (dp[a][b] > 0) {
            return dp[a][b];
        }
        if (a == 0 || b == 0) {
            dp[a][b] = 1;
            return dp[a][b];
        }

        dp[a][b] = (recursive(a, b - 1) + recursive(a - 1, b)) % MOD;
        return dp[a][b];
    }

    public static void main(String[] args) throws Exception {
        recursive(24, 24);
        FastScanner sc = new FastScanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        System.out.println(solve(a, b));
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
