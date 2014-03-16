package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Calvin was driving his favorite vehicle on the 101 freeway. He noticed the check engine light was on and wants to service it immediately to avoid
 * any risks. Luckily, a service lane runs parallel to the highway. The length of the highway and service lane is N units. The service lane
 * constitutes of N segments of unit length, where each segment can have different widths. Calvin can enter into and exit from any segment. Let’s call
 * the entry segment as index i and the exit segment as index j. Assume that the exit segment lies after the enter segment(j>i) and i ≥ 0.
 * 
 * Paradise Highway
 * 
 * Calvin has three types of vehicles - bike, car and truck, represented by 1, 2 and 3 respectively, also implying the width of the vehicle. We are
 * given an array width[] of length N, where width[k] represents the width of kth segment of our service lane. It is guaranteed that while servicing
 * he can pass through at most 1000 segments, including entry and exit segments.
 * 
 * If width[k] is 1, only the bike can pass through kth segment. If width[k] is 2, the bike and car can pass through kth segment. If width[k] is 3,
 * any of the bike, car or truck can pass through kth segment. Given the entry and exit point of the Calvin’s vehicle in service lane, output the type
 * of largest vehicle which can pass through the service lane (including the entry & exit segment)
 * 
 * Input Format The first line of input contains two integers - N & T, where N is the length of the freeway and T is the number of test cases. The
 * next line has N space separated integers which represents the width array.
 * 
 * T test cases follow. Each test case contains two integers - i & j, where i is the index of segment through which Calvin enters the service lane
 * and j is the index of the lane segment where he exits.
 * 
 * Output Format For each test case, print the vehicle type of the largest vehicle which can pass through.
 * 
 * Constraints 1 <= N <= 100000 1 <= T <= 1000 0 <= i < j < N 2 <= j-i+1 <= min(N,1000) 1 <= width[k] <= 3, where 0 <= k < N
 * 
 * 
 * @see https://www.hackerrank.com/contests/101nov13/challenges/service-lane
 * @author nastra - Eduard Tudenhoefner
 */
public class ServiceLane {

    public static int possibleVehicle(int[] width, int entry, int exit) {
        int min = 3;

        for (int i = entry; i <= exit; i++) {
            if (width[i] < min) {
                min = width[i];
            }
        }

        return min;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String n = in.readLine();
        String[] n_split = n.split(" ");

        int N = Integer.parseInt(n_split[0]);
        int T = Integer.parseInt(n_split[1]);

        int[] width = new int[N];
        String next = in.readLine();
        String[] next_split = next.split(" ");

        for (int i = 0; i < N; i++) {
            int item = Integer.parseInt(next_split[i]);
            width[i] = item;
        }

        for (int i = 0; i < T; i++) {
            String nextLine = in.readLine();
            String[] split = nextLine.split(" ");
            int entry = Integer.parseInt(split[0]);
            int exit = Integer.parseInt(split[1]);
            int result = possibleVehicle(width, entry, exit);
            System.out.println("" + result);
        }
    }
}
