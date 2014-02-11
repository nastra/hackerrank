package com.nastra.hackerrank;

import java.util.Scanner;

/**
 * The Utopian tree goes through 2 cycles of growth every year. The first growth cycle of the tree is during the monsoon season when it doubles in
 * height. The second growth cycle is during the summer when it increases in height by 1 meter. If a new Utopian tree sampling of height 1 meter is
 * planted just before the onset of the monsoon season, can you find the height of the tree after N cycles?
 * 
 * Input Format The first line of the input contains an integer T, the number of testcases. T lines follow each line containing the integer N,
 * indicating the number of cycles.
 * 
 * Constraints 1 <= T <= 10 0 <= N <= 60
 * 
 * Output Format Print the height of the Utopian tree after N cycles.
 * 
 * @author nastra - Eduard Tudenhoefner
 */
public class UtopianTree {

    public static int heightAfterCycles(int n) {
        if (n <= 0) {
            return 1;
        }
        int height = 1;
        for (int cycle = 1; cycle <= n; cycle++) {
            if (even(cycle)) {
                height++;
            } else {
                height = height * 2;
            }
        }
        return height;
    }

    private static boolean even(int n) {
        return (n & 1) == 0;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        int[] testcases = new int[T];

        for (int i = 0; i < T; i++) {
            testcases[i] = in.nextInt();
        }
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < T; i++) {
            Integer result = heightAfterCycles(testcases[i]);
            out.append(result);
            if (i < T - 1) {
                out.append("\n");
            }
        }
        System.out.println(out.toString());
        in.close();
    }
}
