package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Mr. Dorsey Dawson recently stole X grams of gold from ACME Jewellers. He is now on a train back home. To avoid getting caught by the police, he has
 * to convert all the gold he has into paper money. He turns into a salesman and starts selling the gold in the train.
 * 
 * There are N passengers who have shown interest in buying the gold. The ith passenger agrees to buy ai grams of gold by paying $vi. Dawson wants to
 * escape from the police and also maximize the profit. Can you help him maximize the profit?
 * 
 * Note: The ith passenger would buy exactly ai grams if the transaction is successful.
 * 
 * Input Format The first line contains two space separated integers, N X, where N is the number of passengers who agreed to buy and X is the stolen
 * amount of gold (in grams). N lines follow. Each line contains two space separated integers - vi and ai, where vi is the the value which the ith
 * passenger has agreed to pay in exchange for ai grams of gold.
 * 
 * Output format: If it’s possible for Dorsey to escape, print the maximum profit he can enjoy, otherwise print “Got caught!” (quotes are for clarity)
 * 
 * Constraints 1 <= X <= 5000 1 <= N <= 106 all vi’s and ai’s are less than or equal to 106 and greater than 0.
 * 
 * 
 * @see https://www.hackerrank.com/contests/101nov13/challenges/dorsey-thief
 * @author nastra - Eduard Tudenhoefner
 */
public class DorseyThief {

    private static int[][] solution;

    public static int optimalValue(int numberOfItems, int capacity, int[] weight, int[] value) {
        int weights = 0;
        int prev = 0;
        solution = new int[numberOfItems + 1][capacity + 1];
        for (int i = 1; i <= numberOfItems; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (weight[i] <= w) {
                    int a = solution[i - 1][w];
                    int b = solution[i - 1][w - weight[i]] + value[i];
                    solution[i][w] = Math.max(a, b);
                } else {
                    solution[i][w] = solution[i - 1][w];
                }
            }
        }
        // if (weights > capacity) {
        // return Integer.MIN_VALUE;
        // }
        return solution[numberOfItems][capacity];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String n = in.readLine();
        String[] n_split = n.split(" ");

        int N = Integer.parseInt(n_split[0]);
        int X = Integer.parseInt(n_split[1]);

        int[] weight = new int[N + 1];
        int[] value = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            String nextLine = in.readLine();
            String[] split = nextLine.split(" ");
            int cash = Integer.parseInt(split[0]);
            int grams = Integer.parseInt(split[1]);
            value[i] = cash;
            weight[i] = grams;
        }

        int result = optimalValue(N, X, weight, value);
        if (Integer.MIN_VALUE == result) {
            System.out.println("Got caught!");
        } else {
            System.out.println(result);
        }
    }
}
