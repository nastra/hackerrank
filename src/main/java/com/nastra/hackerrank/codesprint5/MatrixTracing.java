package com.nastra.hackerrank.codesprint5;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Given below is a word from the English dictionary arranged as a matrix
 * 
 * MATHE ATHEM THEMA HEMAT EMATI MATIC ATICS
 * 
 * Tracing the matrix is starting from the top left position and at each step move either RIGHT or DOWN, to reach the bottom right of the matrix. It
 * is assured that any such tracing generates the same word. How many such tracings can be possible for a given word of length m+n-1 written as a
 * matrix of size m * n?
 * 
 * Input Format The first line of input contains an integer T. T test cases follow in each line. Each line contains 2 space separated integers m & n
 * indicating that the matrix written has m rows and each row has n characters.
 * 
 * Constraints 1 <= T <= 10^3 1 ≤ m,n ≤ 10^6
 * 
 * @see https ://www.hackerrank.com/contests/codesprint5/challenges/matrix-tracing
 * @author nastra - Eduard Tudenhoefner
 */
public class MatrixTracing {

    private static final BigInteger TWO = BigInteger.valueOf(2L);
    private static final BigInteger MOD = BigInteger.valueOf(1000000000 + 7L);
    private static long[] factorials = new long[2000001];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        computeFactorials();
        int numberOfTestCases = in.nextInt();

        for (int i = 0; i < numberOfTestCases; i++) {
            BigInteger p = in.nextBigInteger();
            BigInteger q = in.nextBigInteger();
            BigInteger result = binomialCoefficient(q, p);
            System.out.println(result);
        }
    }

    private static void computeFactorials() {
        factorials[0] = 1;
        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1] * i;
            factorials[i] = factorials[i] % MOD.longValue();
        }
    }

    public static BigInteger binomialCoefficient(BigInteger p, BigInteger q) {
        BigInteger a = getFactorial(p.subtract(BigInteger.ONE).intValue());
        BigInteger b = getFactorial(q.subtract(BigInteger.ONE).intValue());
        BigInteger numerator = getFactorial(p.add(q).subtract(TWO).intValue());

        BigInteger result = numerator.multiply(a.modInverse(MOD));
        result = result.multiply(b.modInverse(MOD));
        return result.mod(MOD);
    }

    public static BigInteger getFactorial(int num) {
        return BigInteger.valueOf(factorials[num]);
    }
}
