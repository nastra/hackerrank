package com.nastra.hackerrank;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

class Solution {

    private static final List<Integer> elems = new ArrayList<Integer>();
    private static int elemsSize = 0;

    public static void main(String args[]) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numOfOps = Integer.parseInt(br.readLine().trim());

        String[] command = null;
        for (int i = 0; i < numOfOps; i++) {
            command = br.readLine().trim().split(" ");
            if (command[0].equals("a")) {
                addElem(Integer.parseInt(command[1]));
            } else {
                removeElem(Integer.parseInt(command[1]));
            }
        }
    }

    private static void addElem(int elem) {
        int pos = Collections.binarySearch(elems, elem);
        if (pos < 0) {
            pos = -pos - 1;
        }

        elems.add(pos, elem);
        elemsSize++;
        printMedian();
    }

    private static void removeElem(int elem) {
        int pos = Collections.binarySearch(elems, elem);
        if (pos < 0) {
            System.out.println("Wrong!");
        } else {
            elems.remove(pos);
            elemsSize--;
            printMedian();
        }
    }

    private static void printMedian() {
        if (elemsSize > 0) {
            if (elemsSize % 2 == 1) {
                System.out.println(elems.get(elemsSize / 2));
            } else {
                long median = (long) elems.get(elemsSize / 2) + (long) elems.get((elemsSize / 2) - 1);
                if (median % 2 == 0) {
                    System.out.format("%d%n", median / 2);
                } else {
                    System.out.format("%.1f%n", median / 2.0);
                }
            }
        } else {
            System.out.println("Wrong!");
            return;
        }
    }
}
