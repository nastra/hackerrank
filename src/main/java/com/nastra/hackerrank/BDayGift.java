package com.nastra.hackerrank;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Isaac has to buy a new HackerPhone for his girlfriend Amy. He is exploring
 * the shops in the town to compare the prices whereupon he finds a shop located
 * on the first floor of a building, that has a unique pricing policy. There are
 * N stairs leading to the shop. A numbered ball is placed on each of the steps.
 * The shopkeeper gives Isaac a fair coin and asks him to toss the coin before
 * climbing each step. If the result of the toss is a ‘Heads’, Isaac should pick
 * up the ball, else leave it and proceed to the next step.
 * 
 * The shopkeeper then asks Isaac to find the sum of all the numbers he has
 * picked up (let’s say S). The price of the HackerPhone is then the expected
 * value of S. Help Isaac find the price of the HackerPhone.
 * 
 * Input Format The first line of the input contains an integer N, the number of
 * steps. N lines follow, which are the numbers written on the ball on each
 * step.
 * 
 * Output Format A single line containing expected value.
 * 
 * Note : Expected value must be printed as a decimal number having exactly one
 * digit after decimal. It is guaranteed that the correct answer will have at
 * most one digit after decimal.
 * 
 * Constraints 1 <= N <= 40 1 <= number on any ball <=10^9
 * 
 * @author nastra - Eduard Tudenhoefner
 */
public class BDayGift {

	private static final BigDecimal HALF = new BigDecimal("0.5");

	public static BigDecimal priceOfPhone(BigInteger[] numberOnBalls) {
		BigDecimal sum = new BigDecimal("0.0");

		for (int i = 0; i < numberOnBalls.length; i++) {
			BigInteger number = numberOnBalls[i];
			BigDecimal num = new BigDecimal(number).multiply(HALF);
			sum = sum.add(num);
		}

		return sum;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		BigInteger[] numberOnBalls = new BigInteger[T];

		for (int i = 0; i < T; i++) {
			numberOnBalls[i] = in.nextBigInteger();
		}
		System.out.println("" + priceOfPhone(numberOnBalls));
		in.close();
	}
}
