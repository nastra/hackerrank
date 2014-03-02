package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author nastra
 * @see https://www.hackerrank.com/contests/101feb14/challenges/sherlock-and-pairs
 * 
 */
public class SherlockAndPairs {

    private static final BigInteger TWO = BigInteger.valueOf(2);

    private static BigInteger solve(int[] in) {
        Map<BigInteger, BigInteger> map = new HashMap<BigInteger, BigInteger>();
        for (int i = 0; i < in.length; i++) {
            BigInteger item = BigInteger.valueOf(in[i]);
            if (map.containsKey(item)) {
                map.put(item, map.get(item).add(BigInteger.ONE));
            } else {
                map.put(item, BigInteger.ONE);
            }
        }

        BigInteger tot = BigInteger.ZERO;
        for (Map.Entry<BigInteger, BigInteger> e : map.entrySet()) {
            BigInteger freq = e.getValue();
            if (TWO.equals(freq)) {
                tot = tot.add(BigInteger.valueOf(2));
            } else if (freq.compareTo(TWO) > 0) {
                BigInteger res = freq.multiply((freq.subtract(BigInteger.ONE)));
                tot = tot.add(res);
            }
        }
        return tot;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int N = sc.nextInt();
            int[] in = sc.nextIntArray();
            System.out.println(solve(in));
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

        public String nextLine() throws Exception {
            return reader.readLine().trim();
        }

        public BigInteger nextBigInteger() throws Exception {
            return new BigInteger(next());
        }
    }
}
