package com.nastra.spoj;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @see http://www.spoj.com/problems/EDIST/
 * @author nastra
 * 
 */
public class EditDistance {
    public static int editDistance(String a, String b) {
        return Dist.distance(a.toCharArray(), b.toCharArray());
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = sc.nextInt();
        while (t > 0) {
            t--;
            String a = sc.next();
            String b = sc.next();
            out.println(editDistance(a, b));
        }
        out.close();
    }

    private static class Dist {

        public static int distance(char[] a, char[] b) {
            if (a.length == 0) {
                return b.length;
            }
            if (b.length == 0) {
                return a.length;
            }
            int m = a.length;
            int n = b.length;
            int[][] dist = new int[a.length + 1][b.length + 1];

            for (int i = 0; i <= m; i++) {
                dist[i][0] = i;
            }
            for (int j = 0; j <= n; j++) {
                dist[0][j] = j;
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (a[i - 1] == b[j - 1]) {
                        // characters are the same, so distance doesn't change
                        dist[i][j] = dist[i - 1][j - 1];
                    } else {
                        int deletion = dist[i - 1][j] + 1;
                        int subst = dist[i][j - 1] + 1;
                        int insert = dist[i - 1][j - 1] + 1;
                        dist[i][j] = min(deletion, subst, insert);
                    }
                }
            }

            return dist[m][n];
        }

        private static int min(int a, int b, int c) {
            return Math.min(Math.min(a, b), c);
        }
    }

    private static class FastScanner {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        public FastScanner(InputStream in) throws Exception {
            reader = new BufferedReader(new InputStreamReader(in));
            tokenizer = new StringTokenizer(reader.readLine().trim());
        }

        public String next() throws Exception {
            if (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine().trim());
                return next();
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws Exception {
            return Integer.parseInt(next());
        }
    }
}
