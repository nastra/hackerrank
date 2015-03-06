package com.nastra.codechef.march2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/MARCH15/problems/CNOTE/
 * @author nastra
 *
 */
public class CNote {

	private static class Notebook {
		int price;
		int pages;

		public Notebook(int pages, int price) {
			super();
			this.price = price;
			this.pages = pages;
		}

	}

	public static boolean solve(int pagesRequired, int pages, int money, Notebook[] a) {
		if (pagesRequired <= pages) {
			return true;
		}
		pagesRequired -= pages;
		for (Notebook n : a) {
			if (n.price <= money && n.pages >= pagesRequired) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		FastScanner sc = new FastScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t > 0) {
			t--;
			int x = sc.nextInt();
			int y = sc.nextInt();
			int k = sc.nextInt();
			int n = sc.nextInt();
			Notebook[] notebooks = new Notebook[n];
			for (int i = 0; i < notebooks.length; i++) {
				notebooks[i] = new Notebook(sc.nextInt(), sc.nextInt());
			}

			out.println(solve(x, y, k, notebooks) ? "LuckyChef" : "UnluckyChef");
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
