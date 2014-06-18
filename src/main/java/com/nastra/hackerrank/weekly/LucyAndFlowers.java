package com.nastra.hackerrank.weekly;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @see https://www.hackerrank.com/contests/w4/challenges/lucy-and-flowers
 * @author nastra - Eduard Tudenhoefner
 */
public class LucyAndFlowers {
    private static int MAX = 5001;
    private static long[][] C;
    private static long MOD = 1000000009;

    private static void generateBinomialCoefficients(int n) {
        C = new long[n + 1][n + 1];
        int i, j;

        for (i = 0; i <= n; i++) {
            C[i][0] = 1;
            for (j = 1; j <= i; j++) {
                C[i][j] = C[i - 1][j - 1] + C[i - 1][j];
                if (C[i][j] >= MOD) {
                    C[i][j] -= MOD;
                }
            }
        }
    }

    public static long[] generateCatalan(int n, long module) {
        long[] inv = generateReverse(n + 2, module);
        long[] Catalan = new long[n];
        Catalan[1] = 1;
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
        generateBinomialCoefficients(MAX);
        long[] catalan = generateCatalan(MAX, MOD);
        long[] ans = new long[MAX];
        for (int i = 0; i < MAX; i++) {
            long res = 0;
            for (int j = 0; j <= i; j++) {
                res += (C[i][j] * catalan[j]) % MOD;
                if (res >= MOD) {
                    res -= MOD;
                }
            }
            ans[i] = res;
        }

        FastScanner sc = new FastScanner(System.in);
        int T = sc.nextInt();
        while (T > 0) {
            T--;
            int x = sc.nextInt();
            System.out.println(ans[x]);
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
