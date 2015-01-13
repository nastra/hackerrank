package com.nastra.codechef.jan2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class QueriesOnTheString {
    public static int solve(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Integer.valueOf(s.charAt(i)) % 3 == 0) {
                result++;
            }
        }

        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                BigInteger val = new BigInteger(s.substring(i, j + 1));
                if (val.mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO)) {
                    result++;
                }
            }
        }

        return result;
    }

    /**
     * current complexity is = O(left*right)
     * 
     */
    public static int solveFast(int[] a, int left, int right) {
        int result = 0;
        for (int i = left; i <= right; i++) {
            int total = 0;
            for (int j = i; j <= right; j++) {
                total += a[j];
                if (total % 3 == 0) {
                    result++;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        int q = sc.nextInt();
        char[] a = sc.next().toCharArray();
        int[] in = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            in[i] = a[i] - '0';
        }
        while (q > 0) {
            q--;
            String[] line = sc.nextLine().split(" ");
            if (line[0].charAt(0) == '1') {
                // replace value at x by Y
                int index = Integer.valueOf(line[1]);
                char val = line[2].charAt(0);
                in[index - 1] = val - '0';
            } else {
                int left = Integer.valueOf(line[1]);
                int right = Integer.valueOf(line[2]);
                // out.println(solve(new String(in).substring(left - 1, right)));
                out.println(solveFast(in, left - 1, right - 1));
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
