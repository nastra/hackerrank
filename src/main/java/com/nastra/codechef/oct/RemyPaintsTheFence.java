package com.nastra.codechef.oct;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/problems/FATCHEF
 * @author nastra
 * 
 */
public class RemyPaintsTheFence {

    private static final long MOD = 1000000009;

    private static long solve(char[] paint) {
        long result = 1;
        int prevIndex = 0;

        char prevColor = '\u0000';
        for (int i = 0; i < paint.length; i++) {
            if (paint[i] != '\u0000') {
                if (paint[i] != prevColor && prevColor != '\u0000') {
                    result = (result * (i - prevIndex)) % MOD;
                    prevIndex = i;
                    prevColor = paint[i];
                } else {
                    prevIndex = i;
                    prevColor = paint[i];
                }
            }
        }
        return result % MOD;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = sc.nextInt();
        while (t > 0) {
            t--;
            int n = sc.nextInt();
            int m = sc.nextInt();
            char[] paint = new char[n];
            while (m > 0) {
                m--;
                char c = sc.next().charAt(0);
                paint[sc.nextInt() - 1] = c;
            }
            out.println(solve(paint));
        }

        out.close();
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

        public long[] nextIntArray() throws Exception {
            String[] line = reader.readLine().trim().split(" ");
            long[] out = new long[line.length];
            for (int i = 0; i < line.length; i++) {
                out[i] = Long.valueOf(line[i]);
            }
            return out;
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

        public BigInteger nextBigInteger() throws Exception {
            return new BigInteger(next());
        }

        public String nextLine() throws Exception {
            return reader.readLine().trim();
        }
    }
}