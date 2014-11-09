package com.nastra.codechef.nov;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/NOV14/problems/RBTREE/
 * @author nastra
 * 
 */
public class ChefAndRedBlackTree {

    private static int blackNodes = 0;
    private static int redNodes = 0;
    private static boolean rev = false;

    public static void solve(int x, int y, boolean reversed) {
        blackNodes = 0;
        redNodes = 0;
        long lca = lca(1L, x, y);
        findParentAndCount(lca, x, reversed);
        findParentAndCount(lca, y, reversed);
        if (isBlack(lca, reversed)) {
            blackNodes++;
        } else {
            redNodes++;
        }
    }

    private static Stack<Integer> pathToRoot(int x) {
        Stack<Integer> s = new Stack<>();
        while (x != 1) {
            s.push(x);
            x = parent(x);
        }
        s.push(1);
        return s;
    }

    private static void findParentAndCount(long lca, int val, boolean reversed) {
        while (true) {
            if (val == lca) {
                break;
            }
            if (isBlack(val, reversed)) {
                blackNodes++;
            } else {
                redNodes++;
            }
            val = parent(val);
        }
    }

    private static int parent(int x) {
        return (int) (Math.ceil(x / 2));
    }

    private static long lca(long root, int x, int y) {
        Stack<Integer> one = pathToRoot(x);
        Stack<Integer> two = pathToRoot(y);
        Integer last = null;
        while (!one.isEmpty() && !two.isEmpty()) {
            if (!one.peek().equals(two.peek())) {
                break;
            }
            last = one.pop();
            two.pop();
        }
        return last.longValue();
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int q = sc.nextInt();
        while (q > 0) {
            q--;
            String[] line = sc.nextLine().split(" ");
            if (line[0].startsWith("Qi")) {
                rev = !rev;
            } else if (line[0].startsWith("Qb")) {
                solve(Integer.valueOf(line[1]), Integer.valueOf(line[2]), rev);
                out.println(blackNodes);
            } else {
                solve(Integer.valueOf(line[1]), Integer.valueOf(line[2]), rev);
                out.println(redNodes);
            }
        }

        out.close();
    }

    private static boolean isBlack(long x, boolean reversed) {
        if (!reversed) {
            return binlog(lowerPowerOf2(x)) % 2 == 0;
        }
        return binlog(lowerPowerOf2(x)) % 2 == 1;
    }

    public static long binlog(long bits) // returns 0 for bits=0
    {
        long log = 0;
        if ((bits & 0xffff0000) != 0) {
            bits >>>= 16;
            log = 16;
        }
        if (bits >= 256) {
            bits >>>= 8;
            log += 8;
        }
        if (bits >= 16) {
            bits >>>= 4;
            log += 4;
        }
        if (bits >= 4) {
            bits >>>= 2;
            log += 2;
        }
        return log + (bits >>> 1);
    }

    private static long upperPowerOf2(long v) {
        v--;
        v |= v >> 1;
        v |= v >> 2;
        v |= v >> 4;
        v |= v >> 8;
        v |= v >> 16;
        v++;
        return v;
    }

    private static long lowerPowerOf2(long x) {
        if (x == 0) {
            return 0;
        }
        // x--; Uncomment this, if you want a strictly less than 'x' result.
        x |= (x >> 1);
        x |= (x >> 2);
        x |= (x >> 4);
        x |= (x >> 8);
        x |= (x >> 16);
        return x - (x >> 1);
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
