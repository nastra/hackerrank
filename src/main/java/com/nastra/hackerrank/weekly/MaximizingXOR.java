package com.nastra.hackerrank.weekly;

import java.util.Scanner;

/**
 * @see https://www.hackerrank.com/contests/w1/challenges/maximizing-xor
 * @author nastra
 * 
 */
public class MaximizingXOR {
    static int maxXor(int l, int r) {
        int max = Integer.MIN_VALUE;
        for (int i = l; i <= r; i++) {
            for (int j = i + 1; j <= r; j++) {
                max = Math.max(max, i ^ j);
            }
        }
        return max;

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int res;
        int _l;
        _l = Integer.parseInt(in.nextLine());

        int _r;
        _r = Integer.parseInt(in.nextLine());

        res = maxXor(_l, _r);
        System.out.println(res);
        in.close();
    }

}
