package com.nastra.codechef;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	public static List<Integer> solve(int low, int high) {
		List<Integer> primes = SieveOfEratosthenes.calculatePrimes(low, high);
		// int startIndex = findStartIndex(low);
		// int endIndex = findEndIndex(high);
		// return primes.subList(startIndex, endIndex + 1);
		return primes;
	}

	private static int findStartIndex(List<Integer> primes, int x) {
		int low = 0;
		int high = primes.size() - 1;
		int prevIndex = 0;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (primes.get(mid).equals(x)) {
				return mid;
			} else if (x < primes.get(mid)) {
				prevIndex = mid;
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return prevIndex;
	}

	private static int findEndIndex(List<Integer> primes, int x) {
		int low = 0;
		int high = primes.size() - 1;
		int prevIndex = high;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (primes.get(mid).equals(x)) {
				return mid;
			} else if (x < primes.get(mid)) {
				prevIndex = mid;
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return prevIndex;
	}

	public static void main(String[] args) throws Exception {

		FastScanner sc = new FastScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t > 0) {
			t--;
			int low = sc.nextInt();
			int high = sc.nextInt();
			List<Integer> result = solve(low, high);
			for (Integer prime : result) {
				out.println(prime);
			}
			out.println();
		}
		out.close();
	}

	static class SieveOfEratosthenes {

		public static List<Integer> calculatePrimes(int low, int high) {
			BitSet set = new BitSet(1000000000 + 2);
			int sqrt = (int) Math.sqrt(1000000000 + 2);
			List<Integer> primes = new ArrayList<Integer>();
			BigInteger x = BigInteger.valueOf(low);
			int first = x.isProbablePrime(1) ? x.intValue() : x.nextProbablePrime().intValue();

			for (int j = 2; j <= sqrt; j++) {
				if (!set.get(j)) {
					for (int k = j * j; k <= high; k += j) {
						set.set(k);
					}
				}
			}

			for (int i = first; i <= high; i++) {
				if (!set.get(i)) {
					primes.add(i);
				}
			}

			return primes;
		}
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
