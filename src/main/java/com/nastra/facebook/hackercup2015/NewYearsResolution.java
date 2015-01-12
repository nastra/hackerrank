package com.nastra.facebook.hackercup2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @see https://www.facebook.com/hackercup/problems.php?pid=1036037553088752&round=742632349177460
 * @author nastra
 * 
 */
public class NewYearsResolution {
    public static void solve() {
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = sc.nextInt();
        int i = 1;
        while (i <= t) {
            int p = sc.nextInt();
            int c = sc.nextInt();
            int f = sc.nextInt();
            int n = sc.nextInt();
            int[][] x = new int[n][3];
            for (int j = 0; j < x.length; j++) {
                for (int k = 0; k < x[j].length; k++) {
                    x[j][k] = sc.nextInt();
                }
            }
            boolean result = SubsetSum.isSubsetSum(x, x.length, x.length, x.length, p, c, f);
            out.println("Case #" + i + ": " + (result ? "yes" : "no"));
            i++;
        }

        out.close();
    }

    static class SubsetSum {

        /**
         * Uses a naive recursive algorithm an runs in non-polynomial time.
         * 
         * @param s
         * @param i
         * @param p
         * @return
         */
        public static boolean isSubsetSum(int[][] s, int i, int j, int k, int p, int c, int f) {
            if (p == 0 && c == 0 && f == 0) {
                return true;
            }
            if ((i <= 0 && p != 0) || (j <= 0 && c != 0) || (k <= 0 && f != 0)) {
                return false;
            }

            // include the last element in the search - exclude last element from search
            return isSubsetSum(s, i - 1, j - 1, k - 1, p, c, f)
                    || isSubsetSum(s, i - 1, j - 1, k - 1, p - s[i - 1][0], c - s[j - 1][1], f - s[k - 1][2]);
        }

        /**
         * Solves the subset sum problem using a dynamic programming approach. The time complexity is O(sum*n).
         * 
         * @param s
         * @param n
         * @param sum
         * @return
         */
        public static boolean isSubsetSumDP(int[] s, int n, int sum) {
            boolean[][] dp = new boolean[n + 1][sum + 1];
            for (int i = 0; i <= n; i++) {
                dp[i][0] = true;
            }
            for (int i = 1; i <= sum; i++) {
                dp[0][i] = false;
            }

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= sum; j++) {
                    dp[i][j] = dp[i - 1][j];
                    if (j >= s[i - 1]) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j - s[i - 1]];
                    }
                }
            }

            return dp[n][sum];
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
