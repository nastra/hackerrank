package com.nastra.hackerrank;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * You and your K-1 friends want to buy N flowers. Flower number i has minCosts ci. Unfortunately the seller does not like a customer to buy a lot of
 * flowers, so he tries to change the price of flowers for customer who had bought flowers before. More precisely if a customer has already bought x
 * flowers, he should pay (x+1)*ci dollars to buy flower number i. You and your K-1 friends want to buy all N flowers in such a way that you spend the
 * least amount of money possible. You can buy the flowers in any order.
 * 
 * Input:
 * 
 * The first line of input contains two integers N and K (K <= N) next line contains N positive integers c1,c2,…,cN respectively.
 * 
 * Output:
 * 
 * Print the minimum amount of money you (and your friends) have to pay in order to buy all N flowers. @author nastra - Eduard Tudenhoefner
 */
public class Flowers {

    public static int minCosts(Integer[] flowerCosts, int[] personsPurchase, int[] buys) {
        int sum = 0;
        // sort the flower costs in reverse order so that we start to purchase
        // the most expensive ones first
        Arrays.sort(flowerCosts, Collections.reverseOrder());
        int flowers = flowerCosts.length;
        int i = 0;

        while (i < flowers) {
            for (int j = 0; j < personsPurchase.length; j++) {
                if (i >= flowers) {
                    break;
                }
                int cost = (buys[j] + 1) * flowerCosts[i];
                personsPurchase[j] = personsPurchase[j] + cost;
                buys[j] = buys[j] + 1;
                i++;
            }
        }

        for (int price : personsPurchase) {
            sum += price;
        }

        return sum;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        int flowersToBuy, persons;
        flowersToBuy = in.nextInt();
        persons = in.nextInt();

        Integer flowerCosts[] = new Integer[flowersToBuy];
        int personsPurchase[] = new int[persons];
        int[] buys = new int[persons];
        for (int i = 0; i < flowersToBuy; i++) {
            flowerCosts[i] = in.nextInt();
        }

        int result = minCosts(flowerCosts, personsPurchase, buys);
        System.out.println(result);
        in.close();
    }
}
