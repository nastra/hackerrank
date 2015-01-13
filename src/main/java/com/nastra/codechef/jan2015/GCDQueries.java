package com.nastra.codechef.jan2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/JAN15/problems/GCDQ/
 * @author nastra
 * 
 */
public class GCDQueries {
    // TODO: improve GCD computation by preprocessing the array
    public static long solve(int[] a, int left, int right) {
        long result = 0;
        int j = 0;
        while (j < a.length) {
            if (j >= left && j <= right) {
                j++;
                continue;
            }
            result = a[j];
            break;
        }
        for (int i = 0; i < a.length; i++) {
            if (i >= left && i <= right) {
                continue;
            }
            result = gcd(result, a[i]);
        }
        return result;
    }

    static long binaryGcd(long a, long b) {
        while (b != 0)
            b ^= a ^= b ^= a %= b;
        return a;
    }

    static long gcd(long a, long b) {
        long r, i;
        while (b != 0) {
            r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = sc.nextInt();
        while (t > 0) {
            t--;
            int n = sc.nextInt();
            int q = sc.nextInt();
            int[] a = sc.nextIntArray();
            while (q > 0) {
                q--;
                int left = sc.nextInt();
                int right = sc.nextInt();
                out.println(solve(a, left - 1, right - 1));
            }
        }

        out.close();
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

        public BigInteger[] nextBigIntegerArray() throws Exception {
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
