package com.nastra.codechef;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * http://www.codechef.com/problems/POUR1
 * 
 * @author nastra
 * 
 */
public class PouringWater {
    public static int solve(int a, int b, int c) {
        if (c == 0) {
            return 0;
        }
        if (a == c || b == c) {
            return 1;
        }

        if (c > a && c > b || c % gcd(a, b) != 0) {
            return -1;
        }

        int steps = 0;
        int x = 0;
        int y = 0;
        int sum = 0;
        while (x != c && y != c) {
            if (x == 0) {
                steps++;
                x = a;
            } else if (y == b) {
                steps++;
                y = 0;
            } else {
                sum = x + y;
                y = sum < b ? sum : b;
                x = sum - y;
                steps++;
            }
        }

        x = 0;
        y = 0;
        int steps1 = 0;
        sum = 0;

        while (x != c && y != c) {
            if (y == 0) {
                steps1++;
                y = b;
            } else if (a == x) {
                x = 0;
                steps1++;
            } else {
                sum = x + y;
                x = sum < a ? sum : a;
                y = sum - x;
                steps1++;
            }
        }
        if (steps < steps1) {
            return steps;
        }

        return steps1;
    }

    private static int gcd(int a, int b) {
        if (a == 0)
            return b;

        while (b != 0) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }

        return a;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = sc.nextInt();
        while (t > 0) {
            t--;
            out.println(solve(sc.nextInt(), sc.nextInt(), sc.nextInt()));
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
