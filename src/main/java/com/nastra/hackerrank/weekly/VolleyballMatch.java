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
    private static BigInteger MOD = new BigInteger("1000000007");

    public static BigInteger solve(BigInteger a, BigInteger b) {
        if (a.intValue() < 25 && b.intValue() < 25) {
            return BigInteger.ZERO;
        }

        BigInteger winner = null;
        BigInteger y = null;
        if (a.compareTo(b) < 0) {
            winner = b;
            y = a;
        } else {
            winner = a;
            y = b;
        }
        int diff = winner.intValue() - y.intValue();
        if (diff == 1 || winner.intValue() == y.intValue()) {
            return BigInteger.ZERO;
        }

        if (winner.intValue() > 25 && y.intValue() < 24) {
            return BigInteger.ZERO;
        }

        if (y.intValue() == 24 && diff > 2) {
            return BigInteger.ZERO;
        }

        if (diff > 2 && winner.intValue() >= 25 && y.intValue() >= 25) {
            return BigInteger.ZERO;
        }

        BigInteger x = winner.subtract(BigInteger.ONE);
        // return binomial(x.add(y), x);
         System.out.println(binomial(x.add(y), x));
        BigInteger[][] dp = new BigInteger[x.intValue() + 1][y.intValue() + 1];
        return recursive(x, y, dp);
    }

    private static BigInteger binomial(final BigInteger N, final BigInteger K) {
        BigInteger ret = BigInteger.ONE;
        for (int k = 0; k < K.intValue(); k++) {
            ret = ret.multiply(N.subtract(BigInteger.valueOf(k))).divide(BigInteger.valueOf(k + 1)).mod(MOD);
        }
        return ret.mod(MOD);
    }

    private static BigInteger recursive(BigInteger a, BigInteger b, BigInteger[][] dp) {
        if (null != dp[a.intValue()][b.intValue()]) {
            return dp[a.intValue()][b.intValue()];
        }
        if (BigInteger.ZERO.equals(a) || BigInteger.ZERO.equals(b)) {
            dp[a.intValue()][b.intValue()] = BigInteger.ONE;
            return dp[a.intValue()][b.intValue()];
        }
        BigInteger x = recursive(a, b.subtract(BigInteger.ONE), dp).mod(MOD);
        BigInteger y = recursive(a.subtract(BigInteger.ONE), b, dp).mod(MOD);

        dp[a.intValue()][b.intValue()] = x.add(y).mod(MOD);
        return dp[a.intValue()][b.intValue()];
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        BigInteger a = sc.nextBigInteger();
        BigInteger b = sc.nextBigInteger();
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
