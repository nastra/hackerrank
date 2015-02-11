package com.nastra.codechef.feb2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/FEB15/problems/RANKLIST/
 * @author Eduard Tudenhoefner - nastra
 *
 */
public class Ranklist {
	static BigInteger solv(BigInteger n, BigInteger s) {
		if (s.compareTo(n) <= 0) {
			return n.subtract(BigInteger.ONE);
		}
		BigInteger maxSum = sum(n);
		BigInteger diff = maxSum.subtract(s);

		if (diff.equals(BigInteger.ZERO)) {
			return BigInteger.ZERO;
		}
		if (s.compareTo(maxSum) > 0) {
			return BigInteger.ONE;
		}

		return BigInteger.valueOf(find(n.longValue(), s.longValue()));
	}

	static BigInteger solve(BigInteger n, BigInteger s) {
		if (s.compareTo(n) <= 0) {
			return n.subtract(BigInteger.ONE);
		}
		BigInteger maxSum = sum(n);
		BigInteger diff = maxSum.subtract(s);

		if (diff.equals(BigInteger.ZERO)) {
			return BigInteger.ZERO;
		}
		if (s.compareTo(maxSum) > 0) {
			return BigInteger.ONE;
		}

		return BigInteger.valueOf(findEff(n.longValue(), s.longValue()));
	}

	static long find(long n, long s) {
		int i = 1;
		int total = 0;
		while (((n - i) + (i + total)) <= s) {
			total += i;
			i++;
		}
		return n - i + 1;
	}

	static long find(long n, long s, long i, long total) {
		while (((n - i) + (i + total)) <= s) {
			total += i;
			i++;
		}
		return n - i + 1;
	}

	static long findEff(long n, long s) {
		long i = 1;
		long total = 0;

		long lo = 1;
		long hi = n / 2;
		long mid = lo + (hi - lo) / 2;
		while (lo < hi) {
			mid = lo + (hi - lo) / 2;
			long r = sum(mid);
			if (((n - mid) + (mid + r)) == s) {
				break;
			} else if ((n - mid) + (mid + r) < s) {
				lo = mid + 1;
			} else {
				hi = mid - 1;
			}
		}
		i = mid;
		total = i == 1 ? 0 : sum(i - 1);

		return find(n, s, i, total);
	}

	private static BigInteger sum(BigInteger n) {
		return n.multiply(n.add(BigInteger.ONE)).divide(BigInteger.valueOf(2L));
	}

	private static long sum(long n) {
		return n * (n + 1) / 2;
	}

	public static void main(String[] args) throws Exception {
		FastScanner sc = new FastScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();

		while (t > 0) {
			t--;
			BigInteger n = sc.nextBigInteger();
			BigInteger s = sc.nextBigInteger();
			// out.println(solv(n, s));
			out.println(solve(n, s));
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

		public BigInteger[] nextBigIntegerArray() throws Exception {
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
