package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 
 * Alice recently started learning about cryptography and found that anagrams are very useful. Two strings are anagrams of each other if they have
 * same character set. For example strings "bacdc" and "dcbac" are anagrams, while strings "bacdc" and "dcbad" are not.
 * 
 * Alice decides on an encryption scheme involving 2 large strings where encryption is dependent on the minimum number of character deletions required
 * to make the two strings anagrams. She need your help in finding out this number.
 * 
 * Given two strings (they can be of same or different length) help her in finding out the minimum number of character deletions required to make two
 * strings anagrams. Any characters can be deleted from any of the strings.
 * 
 * Input Format Two lines each containing a string.
 * 
 * Constraints 1 <= Length of A,B <= 10000 A and B will only consist of lowercase latin letter.
 * 
 * Output Format A single integer which is the number of character deletions.
 * 
 * @see https://www.hackerrank.com/contests/feb14/challenges/make-it-anagram
 * @author nastra - Eduard Tudenhoefner
 */
public class MakeItAnagram {

    private static int binarySearch(char[] source, int low, int high, int needle) {
        int firstOccurrence = Integer.MIN_VALUE;

        while (low <= high) {
            int middle = low + ((high - low) >>> 1);

            if (source[middle] == needle) {
                // key found and we want to search an earlier occurrence
                firstOccurrence = middle;
                high = middle - 1;
            } else if (source[middle] < needle) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }

        if (firstOccurrence != Integer.MIN_VALUE) {
            return firstOccurrence;
        }

        return -(low + 1); // key not found
    }

    private static int solve(String one, String two) {
        char[] left;
        char[] right;
        if (one.length() <= two.length()) {
            left = one.toCharArray();
            right = two.toCharArray();
        } else {
            left = two.toCharArray();
            right = one.toCharArray();
        }
        Arrays.sort(left);
        Arrays.sort(right);

        int j = 0;
        int deletes = 0;
        for (int i = 0; i < left.length; i++) {
            if (j < right.length) {
                if (left[i] != right[j]) {
                    int index = binarySearch(right, j + 1, right.length - 1, left[i]);
                    if (index >= 0) {
                        int oldj = j;
                        j = index + 1;
                        deletes += j - oldj - 1;
                    } else if (index < 0) {
                        deletes++;
                    } else if (deletes > 0) {
                        deletes--;
                    }
                } else {
                    j++;
                }
            } else {
                deletes++;
            }
        }
        deletes += right.length - j;
        return Math.max(0, deletes);

    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        String one = sc.next();
        String two = sc.nextLine();
        System.out.println(solve(one, two));
    }

    static class FastScanner {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        public FastScanner(InputStream in) throws Exception {
            reader = new BufferedReader(new InputStreamReader(in));
            tokenizer = new StringTokenizer(reader.readLine().trim());
        }

        public int numTokens() throws Exception {
            if (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine().trim());
                return numTokens();
            }
            return tokenizer.countTokens();
        }

        public boolean hasNext() throws Exception {
            if (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine().trim());
                return hasNext();
            }
            return true;
        }

        public String next() throws Exception {
            if (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine().trim());
                return next();
            }
            return tokenizer.nextToken();
        }

        public double nextDouble() throws Exception {
            return Double.parseDouble(next());
        }

        public float nextFloat() throws Exception {
            return Float.parseFloat(next());
        }

        public long nextLong() throws Exception {
            return Long.parseLong(next());
        }

        public int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        public String nextLine() throws Exception {
            return reader.readLine().trim();
        }
    }
}
