package com.nastra.hackerrank.codesprint5;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * You are given an integer N. Can you find the least positive integer X made up of only 9’s and 0’s such that X is a multiple of N?
 *
 * Update X is made up of one or more occurences of 9 and zero or more occurences of 0.
 *
 * Input Format The first line contains an integer T which denotes the number of test cases. T lines follow. Each line contains the integer N for
 * which the solution has to be found.
 *
 * Output Format Print the answer X to STDOUT corresponding to each test case. The output should not contain any leading zeroes.
 *
 * Constraints 1 <= T <= 104 1 <= N <= 500 <br>
 *
 *
 * @author Eduard Tudenhoefner
 */
public class SpecialMultiple {

    private static long[] precompute() {
        long[] numbers = new long[501];
        for (int i = 1; i < 5000; i++) {
            long num = Long.parseLong(Long.toBinaryString(i));
            num *= 9;
            for (int j = 1; j <= 500; j++) {
                if (isDivisible(num, j) && numbers[j] == 0) {
                    numbers[j] = num;
                }
            }
        }
        return numbers;
    }

    private static boolean isDivisible(long a, long b) {
        return a % b == 0;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int T = sc.nextInt();
        long[] memo = precompute();
        while (T-- > 0) {
            System.out.println(memo[sc.nextInt()]);
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

        public String nextLine() throws Exception {
            return reader.readLine().trim();
        }
    }
}
