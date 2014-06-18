package com.nastra.codechef.june;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/JUNE14/problems/FORGETPW
 * @author nastra
 */
public class ForgotPassword {
    public static String decryptPassword(Map<String, String> map, String pw) {
        if (map.isEmpty()) {
            return shortenPassword(pw);
        }

        char[] in = pw.toCharArray();
        for (int i = 0; i < in.length; i++) {
            String s = new String(Character.toString(in[i]));
            if (map.containsKey(s)) {
                in[i] = map.get(s).toCharArray()[0];
            }
        }
        return shortenPassword(new String(in));
    }

    private static String shortenPassword(String pw) {
        if (pw.length() > 1) {

            pw = removeLeadingZeros(pw);
            pw = removeTrailingZeros(pw);
            if (!pw.startsWith(".") && pw.endsWith(".")) {
                pw = pw.substring(0, pw.length() - 1);
            }
        }
        if (".".equals(pw) || ".0".equals(pw) || "".equals(pw)) {
            return "0";
        }
        return pw;
    }

    static java.lang.String removeTrailingZeros(java.lang.String str) {
        if (str == null) {
            return null;
        }
        if ("".equals(str)) {
            return str;
        }
        if (!str.contains(".")) {
            return str;
        }
        char[] chars = str.toCharArray();
        int length, index;
        length = str.length();
        index = length - 1;
        for (; index >= 0; index--) {
            if (chars[index] != '0') {
                break;
            }
        }
        if (index == length - 1) {
            return str;
        }
        return str.substring(0, index + 1);
    }

    static String removeLeadingZeros(String str) {
        if (str == null) {
            return null;
        }
        if ("".equals(str)) {
            return str;
        }
        char[] chars = str.toCharArray();
        int length, index;
        length = str.length();
        index = 0;
        for (; index < length; index++) {
            if (chars[index] != '0') {
                break;
            }
        }
        return (index == 0) ? str : str.substring(index);
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int testcases = sc.nextInt();
        while (testcases > 0) {
            testcases--;

            int N = sc.nextInt();
            Map<String, String> map = new HashMap<String, String>();
            while (N > 0) {
                N--;
                map.put(sc.next(), sc.next());
            }
            String pw = sc.next();
            System.out.println(decryptPassword(map, pw));
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
