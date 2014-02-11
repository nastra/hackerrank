package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author nastra
 */
public class MissingNumbers {

    public static Set<Integer> findMissingNumbers(int[] a, int[] b) {
        Set<Integer> set = new TreeSet<Integer>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int valueA : a) {
            if (map.containsKey(valueA)) {
                map.put(valueA, map.get(valueA) - valueA);
            } else {
                map.put(valueA, -valueA);
            }
        }

        for (int valueB : b) {
            if (map.containsKey(valueB)) {
                map.put(valueB, map.get(valueB) + valueB);
            } else {
                map.put(valueB, valueB);
            }
        }

        Iterator<Map.Entry<Integer, Integer>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = iter.next();
            if (entry.getValue() > 0) {
                set.add(entry.getKey());
            }
            iter.remove();
        }
        return set;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String n = in.readLine();
        String[] n_split = n.split(" ");

        int size = Integer.parseInt(n_split[0]);

        int[] a = new int[size];
        int item;
        String next = in.readLine();
        String[] next_split = next.split(" ");

        for (int i = 0; i < size; i++) {
            item = Integer.parseInt(next_split[i]);
            a[i] = item;
        }

        String nextLine = in.readLine();
        String[] b_split = nextLine.split(" ");
        int sizeB = Integer.parseInt(b_split[0]);

        int[] b = new int[sizeB];
        int itemB;
        nextLine = in.readLine();
        String[] split = nextLine.split(" ");

        for (int i = 0; i < sizeB; i++) {
            itemB = Integer.parseInt(split[i]);
            b[i] = itemB;
        }

        Set<Integer> set = findMissingNumbers(a, b);
        StringBuilder sb = new StringBuilder();

        for (Integer value : set) {
            sb.append(value);
            sb.append(" ");
        }
        sb = sb.deleteCharAt(sb.length() - 1);

        System.out.print(sb.toString());
    }
}
