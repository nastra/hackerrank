package com.nastra.codechef;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @see http://www.codechef.com/problems/MIXTURES
 * 
 * @author nastra
 * 
 */
public class Mixtures {
    public static int solve(int[] colors, int n) {
        int[][] sum = new int[n][n];
        for (int i = 0; i < n; i++) {
            sum[i][i] = colors[i] % 100;
            for (int j = i + 1; j < n; j++) {
                sum[i][j] = (sum[i][j - 1] + (colors[j] % 100)) % 100;
            }
        }

        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    dp[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                for (int k = j; k < j + i - 1; k++) {
                    dp[j][j + i - 1] = Math.min(dp[j][k] + dp[k + 1][j + i - 1] + sum[j][k] * sum[k + 1][j + i - 1], dp[j][j + i - 1]);
                }
            }
        }

        return dp[0][n - 1];
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        while (true) {
            String line = br.readLine();
            if (null == line || "".equals(line)) {
                break;
            }
            int n = Integer.parseInt(line);
            int[] colors = new int[n];
            String str1[] = br.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                colors[i] = Integer.valueOf(str1[i]);
            }
            out.println(solve(colors, n));
        }

        out.close();
    }
}
