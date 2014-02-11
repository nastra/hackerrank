/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nastra.hackerrank;

import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author nastra
 */
public class FindTheMedian {

    private static class OrderStatistic {

        private static Random random = new Random();

        public static Integer select(int[] a, int k) {
            if (null == a || a.length == 0) {
                return null;
            }

            if (k < 1 || k > a.length) {
                return null;
            }

            return select(a, k, 0, a.length - 1);
        }

        private static Integer select(int[] a, int k, int low, int high) {
            if (low > high) {
                return null;
            }
            if (low == high) {
                return a[low];
            }
            int pivotIndex = randomizedPartition(a, low, high);
            int leftSize = pivotIndex - low + 1;

            if (leftSize == k) {
                return a[pivotIndex];
            } else if (k < leftSize) {
                return select(a, k, low, pivotIndex - 1);
            } else {
                return select(a, k - leftSize, pivotIndex + 1, high);
            }
        }

        private static int randomizedPartition(int[] in, int low, int high) {
            int randomIndex = random(low, high);
            swap(in, low, randomIndex);
            return partition(in, low, high);
        }

        private static int random(int min, int max) {
            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int randomNum = random.nextInt((max - min) + 1) + min;

            return randomNum;
        }

        private static int partition(int[] a, int low, int high) {
            int p = low;
            int i = low + 1;
            for (int j = low + 1; j <= high; j++) {
                if (a[j] <= a[p]) {
                    swap(a, i, j);
                    i++;
                }
            }
            swap(a, low, i - 1);
            return i - 1;
        }

        private static void swap(int[] in, int i, int j) {
            int tmp = in[i];
            in[i] = in[j];
            in[j] = tmp;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String n = in.nextLine();
        String[] n_split = n.split(" ");

        int size = Integer.parseInt(n_split[0]);

        int[] a = new int[size];
        int item;
        String next = in.nextLine();
        String[] next_split = next.split(" ");

        for (int i = 0; i < size; i++) {
            item = Integer.parseInt(next_split[i]);
            a[i] = item;
        }

        int median = a.length / 2;
        Integer result = OrderStatistic.select(a, median + 1);
        System.out.println(result);
        in.close();
    }
}
