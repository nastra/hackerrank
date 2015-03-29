package com.nastra.codechef.feb2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

public class Str {
	static Map<Character, LinkedList<Integer>> map = new HashMap<>();

	private static void preprocess(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			Character ch = Character.valueOf(c);
			if (!map.containsKey(ch)) {
				map.put(ch, new LinkedList<Integer>());
			}
			map.get(ch).add(i);
		}
	}

	private static int eff(String s, char a, char b, int left, int right) {
		int res = 0;
		LinkedList<Integer> first = map.get(a);
		LinkedList<Integer> second = map.get(b);

		int start = findStart(first, 0, left);

		int prev = 0;
		// TODO: still not efficient enough

		while (start < first.size() && first.get(start) <= right) {
			int start2 = 0;
			int j = prev;
			while (j < second.size()) {
				if (second.get(j) >= first.get(start) && second.get(j) > left) {
					break;
				}
				j++;
			}
			start2 = j;
			prev = start2;

			while (start2 < second.size() && second.get(start2) <= right) {
				res++;
				start2++;
			}
			start++;
		}

		return res;
	}

	static int findStart(LinkedList<Integer> list, int i, int index) {
		// WRONG & still times out
		// int low = i;
		// int high = list.size() - 1;
		// int res = i;
		// while (low <= high) {
		// int mid = low + (high - low) / 2;
		// if (list.get(mid) <= index) {
		// res = mid;
		// low = mid + 1;
		// } else {
		// high = mid - 1;
		// }
		// }
		//
		// return res;

		while (i < list.size() && list.get(i) < index) {
			i++;
		}
		return i;

	}

	static int findStart(LinkedList<Integer> list, int i, int index, int start1) {
		while (i < list.size() && list.get(i) < index && list.get(i) < start1) {
			i++;
		}
		return i;

	}

	public static int solve(String s, char a, char b, int left, int right) {
		int res = 0;
		char[] in = s.toCharArray();

		for (int i = left; i <= right; i++) {
			if (in[i] == a) {
				for (int j = i + 1; j <= right; j++) {
					if (in[j] == b) {
						res++;
					}
				}
			}
		}
		return res;
	}

	public static int solveEff(String s, char a, char b, int left, int right) {
		// TODO: still too slow
		// what about preprocessing the entire string first so that we know
		// where all the start/end positions are?
		int res = 0;
		char[] in = s.toCharArray();
		int i = left;
		while (i <= right) {
			int prev = i;
			boolean found = false;
			if (in[i] == a) { // found start
				for (int j = i + 1; j <= right; j++) {
					if (in[j] == b) { // found end
						res++;
					} else if (in[j] == a) {
						found = true;
						if (i == prev) { // found next start
							i = j;
						}
					}
				}
				if (!found) {
					break;
				}
			}
			if (i == prev) {
				i++;
			}
		}
		return res;
	}

	public static void main(String[] args) throws Exception {
		FastScanner sc = new FastScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		String s = sc.next();
		preprocess(s);
		int q = sc.nextInt();
		while (q > 0) {
			q--;
			String a = sc.next();
			String b = sc.next();
			int left = sc.nextInt() - 1;
			int right = sc.nextInt() - 1;
			// out.println(solve(s, a.toCharArray()[0], b.toCharArray()[0],
			// left, right));
			// out.println(solveEff(s, a.toCharArray()[0], b.toCharArray()[0],
			// left, right));
			out.println(eff(s, a.toCharArray()[0], b.toCharArray()[0], left, right));
		}

		out.close();
	}

	private static class FastScanner {

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

		public int[] nextIntArray() throws Exception {
			String[] line = reader.readLine().trim().split(" ");
			int[] out = new int[line.length];
			for (int i = 0; i < line.length; i++) {
				out[i] = Integer.valueOf(line[i]);
			}
			return out;
		}

		public double[] nextDoubleArray() throws Exception {
			String[] line = reader.readLine().trim().split(" ");
			double[] out = new double[line.length];
			for (int i = 0; i < line.length; i++) {
				out[i] = Double.valueOf(line[i]);
			}
			return out;
		}

		public Integer[] nextIntegerArray() throws Exception {
			String[] line = reader.readLine().trim().split(" ");
			Integer[] out = new Integer[line.length];
			for (int i = 0; i < line.length; i++) {
				out[i] = Integer.valueOf(line[i]);
			}
			return out;
		}

		public BigInteger[] nextBigIngtegerArray() throws Exception {
			String[] line = reader.readLine().trim().split(" ");
			BigInteger[] out = new BigInteger[line.length];
			for (int i = 0; i < line.length; i++) {
				out[i] = new BigInteger(line[i]);
			}
			return out;
		}

		public BigInteger nextBigInteger() throws Exception {
			return new BigInteger(next());
		}

		public String nextLine() throws Exception {
			return reader.readLine().trim();
		}

		public long[] nextLongArray() throws Exception {
			String[] line = reader.readLine().trim().split(" ");
			long[] out = new long[line.length];
			for (int i = 0; i < line.length; i++) {
				out[i] = Long.valueOf(line[i]);
			}
			return out;
		}
	}
}
