package com.nastra.hackerrank;

import java.util.Scanner;

/**
 * There are N integers in an array A. All but one integer occurs in pairs. Your task is to find out that number that occurs only once.
 * 
 * Input Format
 * 
 * The first line of the input contains an integer N indicating N integers in the array A. Next line contains N integers each separated by a single
 * space.
 * 
 * Constraints
 * 
 * 1 <= N < 100 N % 2 = 1 ( N is an odd number ) 0 <= i <= 100, ∀ i ∈ A
 * 
 * Output Format
 * 
 * Output (S) which is the number that occurs only once. @author nastra
 */
public class LonelyInteger {

    static int lonelyinteger(int[] s) {
        if (null == s || s.length == 0) {
            return 0;
        }
        int result = 0;
        for (int value : s) {
            result ^= value;
        }
        return result;

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int res;

        int _a_size = Integer.parseInt(in.nextLine());
        int[] _a = new int[_a_size];
        int _a_item;
        String next = in.nextLine();
        String[] next_split = next.split(" ");

        for (int _a_i = 0; _a_i < _a_size; _a_i++) {
            _a_item = Integer.parseInt(next_split[_a_i]);
            _a[_a_i] = _a_item;
        }

        res = lonelyinteger(_a);
        System.out.println(res);
        in.close();
    }
}
