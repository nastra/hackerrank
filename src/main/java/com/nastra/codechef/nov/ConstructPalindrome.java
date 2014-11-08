package com.nastra.codechef.nov;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/NOV14/problems/PRPALN/
 * @author nastra
 * 
 */
public class ConstructPalindrome {

    public static boolean solve(String s) {
        return getEliminateForPalindromeIndex(s) != -1;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = sc.nextInt();
        while (t > 0) {
            t--;
            out.println(solve(sc.next()) ? "YES" : "NO");
        }
        out.close();
    }

    public static final int getEliminateForPalindromeIndex(String oneCharAway_fromPalindrome) {
        for (int i = 0; i < oneCharAway_fromPalindrome.length(); i++) {
            String strMinus1Char = oneCharAway_fromPalindrome.substring(0, i) + oneCharAway_fromPalindrome.substring(i + 1);

            String half1 = getFirstHalf(strMinus1Char);
            String half2Reversed = getSecondHalfReversed(strMinus1Char);

            if (half1.length() != half2Reversed.length()) {
                // One half is exactly one character longer
                if (half1.length() > half2Reversed.length()) {
                    half1 = half1.substring(0, (half1.length() - 1));
                } else {
                    half2Reversed = half2Reversed.substring(0, (half2Reversed.length() - 1));
                }
            }

            // System.out.println(i + " " + strMinus1Char + " --> " + half1 + " / " + half2Reversed +
            // "  (minus the singular [non-mirrored] character in the middle, if any)");

            if (half1.equals(half2Reversed)) {
                return i;
            }
        }
        return -1;
    }

    public static final String getFirstHalf(String whole_word) {
        return whole_word.substring(0, whole_word.length() / 2);
    }

    public static final String getSecondHalfReversed(String whole_word) {
        return new StringBuilder(whole_word.substring(whole_word.length() / 2)).reverse().toString();
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
