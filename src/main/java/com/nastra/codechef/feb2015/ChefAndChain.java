package com.nastra.codechef.feb2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/FEB15/problems/CHEFCH
 * @author Eduard Tudenhoefner - nastra
 *
 */
public class ChefAndChain {
	static void swap(char[] c, int i) {
		c[i] = c[i] == '+' ? '-' : '+';
	}

	static int solve(String s) {
		char[] in = s.toCharArray();
		int min = Integer.MAX_VALUE;
		if (s.length() <= 1500) {
			for (int i = 0; i < in.length; i++) {
				min = Math.min(min, calc(in));
				swap(in, i);
				min = Math.min(min, 1 + calc(in));
				swap(in, i);
			}
		} else {
			int a = calc(in);
			swap(in, 0);
			int b = calc(in);
			return Math.min(a, 1 + b);
		}
		return min;
	}

	static int find(char[] a) {
		for (int i = 1; i < a.length - 1; i++) {
			if (a[i] == a[i - 1] && a[i] == a[i + 1]) {
				return i;
			}
		}
		return -1;
	}

	private static int calc(char[] in) {
		int res = 0;
		Stack<Character> st = new Stack<>();
		for (int i = 0; i < in.length; i++) {
			char c = in[i];
			if (!st.isEmpty() && st.peek().equals(c)) {
				if (c == '+') {
					c = '-';
				} else {
					c = '+';
				}
				res++;
			}
			st.add(c);
		}

		return res;

	}

	public static void main(String[] args) throws Exception {
		FastScanner sc = new FastScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t > 0) {
			t--;
			String s = sc.next();
			out.println(solve(s));

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
