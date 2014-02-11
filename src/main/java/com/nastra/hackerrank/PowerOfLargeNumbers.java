package com.nastra.hackerrank;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * 
 * @author nastra - Eduard Tudenhoefner
 */
public class PowerOfLargeNumbers {

    private static final BigInteger MOD = new BigInteger("1000000007");
    private static final BigInteger R = new BigInteger("1000000010");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger MOD_INV = (MOD.negate().pow(-1)).mod(R);
    private static final BigInteger TWO = new BigInteger("2");
    // private static final BigInteger LIMIT = new BigInteger("1000000008");
    // private static final BigInteger LIMIT = new BigInteger("1299827");
    private static final BigInteger LIMIT = new BigInteger("665381");

    public static BigInteger power(BigInteger base, BigInteger exp) {
        if (exp.compareTo(BigInteger.ZERO) <= 0) {
            return ONE;
        }
        BigInteger x = power(base, exp.divide(TWO));
        if (isEven(exp)) {
            x = x.multiply(x);
        } else {
            x = base.multiply(x.multiply(x));
        }
        if (x.compareTo(LIMIT) > 0) {
            x = x.mod(MOD);
        }
        return x;
    }

    public static BigInteger powerIterative(BigInteger a, BigInteger exp) {
        if (exp.compareTo(BigInteger.ZERO) <= 0) {
            return ONE;
        }

        BigInteger x = ONE;
        while (exp.compareTo(BigInteger.ZERO) > 0) {
            if (!isEven(exp)) {
                x = x.multiply(a).mod(MOD);
            }
            a = a.multiply(a).mod(MOD);
            // if (a.compareTo(LIMIT) > 0) {
            // a = a.mod(MOD);
            // }
            // if (x.compareTo(LIMIT) > 0) {
            // x = x.mod(MOD);
            // }
            exp = exp.divide(TWO);
        }
        return x;
    }

    public static BigInteger powerIterativeEff(BigInteger a, BigInteger exp) {
        if (exp.compareTo(BigInteger.ZERO) <= 0) {
            return ONE;
        }
        return a.modPow(exp, MOD);
    }

    public static BigInteger montgomery(BigInteger a, BigInteger exp) {
        BigInteger A, ui, temp, Ai;
        int n = MOD.bitLength();
        A = a;
        for (int i = 0; i < n; i++) {
            Ai = ithDigit(A, i);
            ui = Ai.multiply(MOD_INV).mod(TWO);
            temp = ui.multiply(MOD).multiply(TWO.pow(i));
            A = A.add(temp);
        }
        A = A.divide(TWO.pow(n));
        if (A.compareTo(MOD) >= 0) {
            A = A.subtract(MOD);
        }
        return A;
    }

    // public static BigInteger montgomery(BigInteger a, BigInteger exp) {
    private static BigInteger ithDigit(BigInteger n, int i) {
        /**
         * n = number whose digits we need to extract b = radix in which the number if represented i = the ith bit (ie, index of the bit that needs to
         * be extracted)
         * 
         */
        while (i > 0) {
            n = n.divide(TWO);
            i--;
        }
        return n.mod(TWO);
    }

    private static boolean isEven(BigInteger x) {
        return !x.testBit(0);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        // StringBuilder out = new StringBuilder();

        for (int i = 0; i < T; i++) {
            BigInteger val = powerIterativeEff(in.nextBigInteger(), in.nextBigInteger());
            System.out.println(val);
            // out.append(val);
            // if (i < T - 1) {
            // out.append("\n");
            // System.out.println();
            // }
        }
        // System.out.println(out.toString());
        in.close();
    }
}
