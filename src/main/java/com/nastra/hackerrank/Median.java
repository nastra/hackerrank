package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Median {

    private static final String SPACE = " ";
    private static List<Integer> list = new ArrayList<Integer>(100000);
    private static int size = 0;
    private static final String WRONG = "Wrong!";
    private static final String ADD = "a";
    private static final BigDecimal HALF = new BigDecimal("0.5");

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        for (int i = 0; i < N; i++) {
            String[] command = br.readLine().split(SPACE);
            if (ADD.equals(command[0])) {
                System.out.println(add(Integer.parseInt(command[1])));
            } else {
                System.out.println(remove(Integer.parseInt(command[1])));
            }
        }
    }

    private static String add(Integer x) {
        int pos = Collections.binarySearch(list, x);
        if (pos < 0) {
            pos = -pos - 1;
        }
        list.add(pos, x);
        size++;

        return printMedian();
    }

    private static String remove(Integer x) {
        if (size == 0) {
            return WRONG;
        }
        int pos = Collections.binarySearch(list, x);
        if (pos < 0) {
            return WRONG;
        } else {
            list.remove(pos);
            size--;
            if (size < 0) {
                size = 0;
            }
            return printMedian();
        }
    }

    private static String printMedian() {
        if (size > 0) {
            if (size % 2 == 0) {
                Integer a = list.get(size / 2 - 1);
                Integer b = list.get(size / 2);

                BigInteger one = new BigInteger(a.toString());
                BigInteger two = new BigInteger(b.toString());
                BigInteger three = one.add(two);
                BigDecimal dec = new BigDecimal(three);
                BigDecimal out = dec.multiply(HALF);
                String result = out.toString();
                if (result.endsWith(".0")) {
                    return result.substring(0, result.lastIndexOf(".0"));
                }
                return result;
            } else {
                return list.get(size / 2).toString();
            }
        }
        return WRONG;
    }
}
