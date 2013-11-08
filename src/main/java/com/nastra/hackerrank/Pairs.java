package com.nastra.hackerrank;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Given N integers [N<=10^5], count the total pairs of integers that have a difference of K. [K>0 and K< 1e9]. Each of the N integers will be greater
 * than 0 and at least K away from 2^31-1 (Everything can be done with 32 bit integers).
 *
 * Input Format
 *
 * 1st line contains N & K (integers). 2nd line contains N numbers of the set. All the N numbers are assured to be distinct.
 *
 * Output Format
 *
 * One integer saying the number of pairs of numbers that have a diff K. @author nastra
 */
public class Pairs {

    private static final Integer ONE = 1;

    /* Complete this function */
    static int pairs(int[] a, int k) {
        if (null == a || a.length == 0) {
            return 0;
        }
        int result = 0;
        Map<Integer, Integer> map = countOccurrences(a);
        for (int value : a) {
            int target = value + k;
            if (map.containsKey(target)) {
                result += map.get(target);
            }
        }
        return result;
    }

    private static Map<Integer, Integer> countOccurrences(int[] a) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(a.length);
        for (int value : a) {
            if (map.containsKey(value)) {
                map.put(value, map.get(value) + 1);
            } else {
                map.put(value, ONE);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int res;

        String n = in.nextLine();
        String[] n_split = n.split(" ");

        int _a_size = Integer.parseInt(n_split[0]);
        int _k = Integer.parseInt(n_split[1]);

        int[] _a = new int[_a_size];
        int _a_item;
        String next = in.nextLine();
        String[] next_split = next.split(" ");

        for (int _a_i = 0; _a_i < _a_size; _a_i++) {
            _a_item = Integer.parseInt(next_split[_a_i]);
            _a[_a_i] = _a_item;
        }

        res = pairs(_a, _k);
        System.out.println(res);
    }
}
