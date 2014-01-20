package com.nastra.hackerrank.codesprint5;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author nastra - Eduard Tudenhoefner
 */
public class MatrixTracing {

    private static final BigInteger TWO = BigInteger.valueOf(2L);
    private static final BigInteger MOD = BigInteger.valueOf(1000000000 + 7L);

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numberOfTestCases = in.nextInt();

        for (int i = 0; i < numberOfTestCases; i++) {
            BigInteger p = in.nextBigInteger();
            BigInteger q = in.nextBigInteger();
//            BigInteger w = count(p, q);
//            System.out.println(w.toString());
//            System.out.println(binomial(q.intValue(), p.intValue()));
            BigInteger result = combinations(q.intValue(), p.intValue());
            System.out.println(result.mod(MOD));
        }
    }

    static BigInteger combinations(int n, int k) {
        BigInteger coeff = BigInteger.ONE;
        for (int i = n - k + 1; i <= n; i++) {
            coeff = coeff.multiply(BigInteger.valueOf(i));
        }
        for (int i = 1; i <= k; i++) {
            coeff = coeff.divide(BigInteger.valueOf(i));
        }
        return coeff;
    }

    public static BigInteger countWays(BigInteger p, BigInteger q) {
        BigInteger a = getFactorial(p.subtract(BigInteger.ONE).mod(MOD).longValue());
        BigInteger b = getFactorial(q.subtract(BigInteger.ONE).mod(MOD).longValue());
        return getFactorial(p.add(q).subtract(TWO).mod(MOD).longValue()).divide(a.multiply(b));
    }

    public static BigInteger getFactorial(long num) {
        BigInteger fact = BigInteger.ONE;
        for (long i = 1; i <= num; i++) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }
        return fact;
    }

    public static BigInteger count(BigInteger p, BigInteger q) {
        if (p.equals(BigInteger.ZERO) || q.equals(BigInteger.ZERO)) {
            return BigInteger.ZERO;
        }
        if (p.equals(BigInteger.ONE) || q.equals(BigInteger.ONE)) {
            return BigInteger.ONE;
        }
        long limit = ((p.intValue() - 1) < (q.intValue() - 1)) ? (p.intValue() - 1) : (q.intValue() - 1);
        BigInteger dividend = BigInteger.ONE;
        BigInteger divisor = BigInteger.ONE;
        long loop = 1;

        for (long i = p.intValue() - 1 + q.intValue() - 1; loop <= limit; loop++, i--) {
            dividend = dividend.multiply(BigInteger.valueOf(i));
            divisor = divisor.multiply(BigInteger.valueOf(loop));
        }
        return dividend.divide(divisor).mod(MOD);
    }

    public static int binomial(int n, int k) {
        int[][] table = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            table[i][0] = 1;
        }

        for (int i = 1; i <= k; i++) {
            table[i][i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i && j <= k; j++) {
                table[i][j] = table[i - 1][j] + table[i - 1][j - 1];
            }
        }
        return table[n][k];
    }
//    static long combinations(int n, int k) {
//        long coeff = 1;
//        for (int i = n - k + 1; i <= n; i++) {
//            coeff *= i;
//        }
//        for (int i = 1; i <= k; i++) {
//            coeff /= i;
//        }
//        return coeff;
//    }
}
