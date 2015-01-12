package com.nastra.facebook.hackercup2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @see https://www.facebook.com/hackercup/problems.php?pid=582062045257424&round=742632349177460
 * @author nastra
 * 
 */
public class CookingTheBooks {

    static int min = 0;
    static int max = 0;

    public static void solve(String x) {
        int v = Integer.valueOf(x);
        min = v;
        max = v;
        List<String> result = PermuterA.permute(x);
        for (String s : result) {
            if (s.charAt(0) == '0') {
                continue;
            }
            int val = Integer.valueOf(s);
            min = Math.min(min, val);
            max = Math.max(max, val);
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        int i = 1;
        while (i <= n) {
            String in = sc.next();
            solve(in);
            out.println("Case #" + i + ": " + min + " " + max);
            i++;
        }

        out.close();
    }

    static class PermuterA {

        private static Map<Character, List<String>> map = new HashMap<Character, List<String>>();

        public static List<String> permuteIterative(String input) {
            return Collections.emptyList();
        }

        /**
         * Generates all permutations of a given string. This solution uses recursion and runs in time O(n!). Due to its recursive nature, this
         * solution is quite memory-inefficient.
         * 
         * @param input
         *            The input to permute.
         * @return A list of all permutations of the given input.
         */
        public static List<String> permute(String input) {
            if (null == input) {
                return Collections.emptyList();
            }
            List<String> permutations = new ArrayList<String>();
            if (input.isEmpty()) {
                permutations.add("");
                return permutations;
            }

            char first = input.charAt(0);
            String remainder = input.substring(1);
            List<String> words = permute(remainder);
            List<String> perms = generatePermutations(first, words, permutations);
            return perms;
        }

        private static List<String> generatePermutations(char first, List<String> words, List<String> permutations) {
            for (String word : words) {
                for (int j = 0; j <= word.length(); j++) {
                    permutations.add(insertCharBetween(word, first, j));
                }
            }
            return permutations;
        }

        private static String insertCharBetween(String word, char c, int i) {
            String front = word.substring(0, i);
            String back = word.substring(i);
            return front + c + back;
        }

    }

    static class PermuterB {

        public static List<String> permutations = new ArrayList<String>();

        public static void permute(String input) {
            permute(input.toCharArray(), 0, input.length() - 1);
        }

        public static void swap(char[] str, int i, int j) {
            char temp;
            temp = str[j];
            str[j] = str[i];
            str[i] = temp;
        }

        public static void permute(char[] str, int start, int end) {
            if (start == end) {
                // System.out.println(str);
                permutations.add(new String(str));
            } else {
                for (int i = start; i <= end; i++) {
                    swap(str, start, i);
                    permute(str, start + 1, end);
                    swap(str, start, i);
                }
            }
        }
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
