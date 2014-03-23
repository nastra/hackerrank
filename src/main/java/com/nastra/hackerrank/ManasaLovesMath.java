package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 
 * Solution to the 20/20 Hack March problem "Manasa loves Math"
 * 
 * @see https://www.hackerrank.com/contests/mar14/challenges/manasa-loves-maths
 * @author nastra - Eduard Tudenhoefner
 * 
 */
public class ManasaLovesMath {

    private static final String[] numbers = {"000", "002", "004", "006", "008", "012", "014", "016", "023", "024", "025", "027", "028", "029", "034",
            "036", "044", "045", "046", "047", "048", "049", "056", "067", "068", "069", "088", "112", "123", "125", "126", "127", "128", "129",
            "136", "144", "146", "148", "166", "167", "168", "223", "224", "227", "234", "235", "236", "238", "239", "244", "246", "247", "248",
            "255", "256", "257", "258", "259", "267", "269", "278", "279", "288", "289", "299", "336", "344", "348", "356", "367", "368", "369",
            "445", "446", "447", "448", "449", "456", "458", "466", "468", "469", "478", "488", "489", "566", "567", "568", "669", "677", "678",
            "679", "688", "689", "888"};

    public static boolean solve(String num) {
        char[] x = num.toCharArray();
        if (x.length >= 3) {
            return findThreeNumbers(x);
        }
        return checkThree(x, 0, x.length - 1);
    }

    private static boolean findThreeNumbers(char[] in) {
        int[] originalCounts = new int[10];

        for (char c : in) {
            int x = Integer.valueOf(Character.toString(c));
            originalCounts[x]++;
        }
        for (String s : numbers) {
            char[] digits = s.toCharArray();
            int[] counts = Arrays.copyOf(originalCounts, 10);
            int found = 0;
            for (int i = 0; i < digits.length; i++) {
                int x = Integer.valueOf(Character.toString(digits[i]));
                if (counts[x] <= 0) {
                    break;
                } else {
                    counts[x]--;
                    found++;
                }
            }
            if (found == 3) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkThree(char[] x, int lo, int hi) {
        Permuter.permuteThree(x, lo, hi);
        return Permuter.foundNumber;
    }

    private static boolean divisibleByEight(int x) {
        return x << (32 - 3) == 0;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int T = sc.nextInt();
        while (T > 0) {
            T--;
            String num = sc.nextLine();
            if (solve(num)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    static class Permuter {

        private static int N;

        private static boolean foundNumber = false;

        public static void permuteThree(char[] input, int low, int high) {
            foundNumber = false;
            N = high - low;
            permute(input, low, high);
        }

        public static void swap(char[] str, int i, int j) {
            char temp;
            temp = str[j];
            str[j] = str[i];
            str[i] = temp;
        }

        public static void permute(char[] str, int start, int end) {
            if (start == end) {
                // TODO IMPROVE: new array creation
                char[] b = Arrays.copyOfRange(str, end - N, end + 1);
                if (b.length > 0) {
                    try {
                        Integer x = Integer.valueOf(String.valueOf(b));
                        if (divisibleByEight(x)) {
                            foundNumber = true;
                            return;
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            } else {
                for (int i = start; i <= end; i++) {
                    swap(str, start, i);
                    permute(str, start + 1, end);
                    swap(str, start, i);
                }
            }
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
