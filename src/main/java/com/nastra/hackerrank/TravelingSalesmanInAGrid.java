package com.nastra.hackerrank;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * PLEASE NOTE: algorithm did not get full score and is wrong!
 * 
 * @see https://www.hackerrank.com/contests/mar14/challenges/tsp-grid
 * @author nastra - Eduard Tudenhoefer
 * 
 */
public class TravelingSalesmanInAGrid {

    private static void initGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = 0;
            }
        }
    }

    private static void initGrid(boolean[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = false;
            }
        }
    }

    public static BigInteger solve(boolean[][] grid, int[] rows, int[] cols, int[][] rowCosts, int[][] colCosts, int M, int N) {
        int startRow = 1;
        int startCol = 1;
        int endRow = grid.length - 1;
        int endCol = grid[0].length - 1;
        BigInteger sum = null;
        if (odd(M)) {
            BigInteger a = solveForOddRowsA(grid, rowCosts, colCosts, startRow, endRow, startCol, endCol);
            sum = a;
        } else {
            BigInteger a = solveForEvenRowsA(grid, rowCosts, colCosts, startRow, endRow, startCol, endCol);
            sum = a;
            // initGrid(grid);
            // BigInteger c = solveForEvenRowsC(grid, rowCosts, colCosts, startRow, endRow, startCol, endCol);
            // if (a.compareTo(c) <= 0) {
            // return sum = a;
            // } else {
            // sum = c;
            // }

        }
        return sum;
    }

    /**
     * traverse left to right / up to down / right to left, and then:
     * 
     * one up / left to right / one up / right to left / and so on..
     * 
     * @param grid
     * @param rowCosts
     * @param colCosts
     * @param startRow
     * @param endRow
     * @param startCol
     * @param endCol
     * @return
     */
    private static BigInteger solveForEvenRowsA(boolean[][] grid, int[][] rowCosts, int[][] colCosts, final int startRow, final int endRow,
            final int startCol, final int endCol) {
        BigInteger sum = BigInteger.ZERO;
        grid[startRow][startCol] = true;
        // traverse left to right
        sum = sum.add(leftToRight(grid, colCosts, startRow, 2, endCol + 1));

        // traverse down
        sum = sum.add(upToDown(grid, rowCosts, endCol, startRow + 1, endRow + 1));

        // traverse right to left, but reset the last visited cell
        grid[endRow][endCol] = false;
        sum = sum.add(rightToLeft(grid, colCosts, endRow, endCol, 1));
        // traverse one up/then all the way right/then one up/then all the way left
        int row = endRow;
        int col = 1;
        while (row >= 2) {
            // one up
            grid[row][col] = true;
            sum = sum.add(BigInteger.valueOf(rowCosts[row][col + 1]));
            row--;

            // left to right
            col = grid[row].length - 1;
            BigInteger res = leftToRight(grid, colCosts, row, 2, col);
            sum = sum.add(res);

            if (row <= 1) {
                break;
            }
            // one up
            col--;
            grid[row][col] = true;
            sum = sum.add(BigInteger.valueOf(rowCosts[row][col + 1]));
            // row--;

            // right to left
            res = rightToLeft(grid, colCosts, row - 1, col, 1);
            sum = sum.add(res);
        }
        return sum;
    }

    /**
     * traverse up to down / right to left / down to up, and then:
     * 
     * one up / right to left / one up / left to right / and so on..
     * 
     * @param grid
     * @param rowCosts
     * @param colCosts
     * @param startRow
     * @param endRow
     * @param startCol
     * @param endCol
     * @return
     */
    private static BigInteger solveForEvenRowsC(boolean[][] grid, int[][] rowCosts, int[][] colCosts, final int startRow, final int endRow,
            final int startCol, final int endCol) {
        BigInteger sum = BigInteger.ZERO;
        // grid[startRow][startCol] = true;
        // traverse down
        sum = sum.add(upToDown(grid, rowCosts, startCol, startRow, endRow + 1));

        // traverse left to right
        sum = sum.add(leftToRight(grid, colCosts, endRow, startCol, endCol + 1));

        // traverse right to left, but reset the last visited cell
        grid[endRow][endCol] = false;
        // sum = sum.add(rightToLeft(grid, colCosts, endRow, endCol, 1));

        int row = endRow;
        int col = endCol;
        while (row >= 2) {
            // one up
            grid[row][col] = true;
            sum = sum.add(BigInteger.valueOf(rowCosts[row][col + 1]));
            row--;

            // right to left
            col = grid[row].length - 1;
            BigInteger res = rightToLeft(grid, colCosts, row, col, startCol + 2);
            sum = sum.add(res);

            if (row <= 1) {
                break;
            }
            // one up
            col = startCol + 2;
            if (col > endCol) {
                break;
            }
            grid[row][col] = true;
            sum = sum.add(BigInteger.valueOf(rowCosts[row][col]));
            row--;

            // left to right
            res = leftToRight(grid, colCosts, row, col, endCol + 1);
            sum = sum.add(res);
            col = endCol;
        }
        return sum;
    }

    private static BigInteger solveForOddRowsA(boolean[][] grid, int[][] rowCosts, int[][] colCosts, final int startRow, final int endRow,
            final int startCol, final int endCol) {
        BigInteger sum = BigInteger.ZERO;
        grid[startRow][startCol] = true;
        // traverse left to right
        sum = sum.add(leftToRight(grid, colCosts, startRow, 2, endCol + 1));

        // traverse down
        sum = sum.add(upToDown(grid, rowCosts, endCol, startRow + 1, endRow + 1));

        // // traverse right to left, but reset the last visited cell
        // grid[endRow][endCol] = false;
        // sum = sum.add(rightToLeft(grid, colCosts, endRow, endCol, 1));
        // traverse one up/then all the way right/then one up/then all the way left
        int row = endRow;
        int col = endCol;

        // TODO: traverse one up/then all the way right/then one up/then all the way left
        while (col > 1) {
            // one left
            grid[row][col] = true;
            sum = sum.add(BigInteger.valueOf(colCosts[row][col]));

            col--;

            // down to up
            BigInteger res = downToUp(grid, rowCosts, col, row, startRow + 2);
            sum = sum.add(res);

            // one left
            // row = currentRow;
            grid[row][col] = true;
            sum = sum.add(BigInteger.valueOf(colCosts[row][col]));
            col--;

            // up to down
            // 3 is the second row
            res = upToDown(grid, rowCosts, col, startRow + 2, endRow);
            row = endRow - 1;
            sum = sum.add(res);
        }
        return sum;
    }

    private static BigInteger leftToRight(boolean[][] grid, int[][] colCosts, int row, int colStart, int colEnd) {
        BigInteger sum = BigInteger.ZERO;
        for (int c = colStart; c < colEnd; c++) {
            if (!grid[row][c]) {
                grid[row][c] = true;
                sum = sum.add(BigInteger.valueOf(colCosts[row][c]));
            }
        }
        return sum;
    }

    private static BigInteger downToUp(boolean[][] grid, int[][] rowCosts, int col, int rowStart, int rowEnd) {
        BigInteger sum = BigInteger.ZERO;

        for (int r = rowStart; r >= rowEnd; r--) {
            if (!grid[r][col]) {
                grid[r][col] = true;
                sum = sum.add(BigInteger.valueOf(rowCosts[r][col + 1]));
            }
        }

        return sum;
    }

    private static BigInteger upToDown(boolean[][] grid, int[][] rowCosts, int col, int rowStart, int rowEnd) {
        BigInteger sum = BigInteger.ZERO;

        for (int r = rowStart; r < rowEnd; r++) {
            if (!grid[r][col]) {
                grid[r][col] = true;
                sum = sum.add(BigInteger.valueOf(rowCosts[r][col + 1]));
            }
        }

        return sum;
    }

    private static BigInteger rightToLeft(boolean[][] grid, int[][] colCosts, int row, int colStart, int colEnd) {
        BigInteger sum = BigInteger.ZERO;
        for (int c = colStart; c >= colEnd; c--) {
            if (!grid[row][c]) {
                grid[row][c] = true;
                sum = sum.add(BigInteger.valueOf(colCosts[row][c]));
            }
        }
        return sum;
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int M = sc.nextInt();
        int N = sc.nextInt();
        int[] col = new int[N];
        int[] row = new int[M];
        boolean[][] grid = new boolean[M + 1][N + 1];
        int[][] colCosts = new int[M + 1][N];
        int[][] rowCosts = new int[M + 1][M + 1];
        initGrid(colCosts);
        initGrid(rowCosts);
        for (int i = 1; i <= M; i++) {
            // m rows that contain N-1 integers
            colCosts[i] = sc.nextIntArrayFromTwo();
        }
        for (int i = 2; i <= M; i++) {
            // contains M-1 rows that contain M integers
            rowCosts[i] = sc.nextIntArrayFromTwo();
        }
        if (odd(M * N)) {
            System.out.println(0);
        } else {
            System.out.println(solve(grid, row, col, rowCosts, colCosts, M, N));
        }
    }

    private static boolean odd(int n) {
        return (n & 1) == 1;
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

        public int[] nextIntArray() throws Exception {
            String[] line = reader.readLine().trim().split(" ");
            int[] out = new int[line.length];
            for (int i = 0; i < line.length; i++) {
                out[i] = Integer.valueOf(line[i]);
            }
            return out;
        }

        public int[] nextIntArrayFromOne() throws Exception {
            String[] line = reader.readLine().trim().split(" ");
            int[] out = new int[line.length + 1];
            for (int i = 1; i <= line.length; i++) {
                out[i] = Integer.valueOf(line[i - 1]);
            }
            return out;
        }

        public int[] nextIntArrayFromTwo() throws Exception {
            String[] line = reader.readLine().trim().split(" ");
            int[] out = new int[line.length + 2];
            for (int i = 2; i <= line.length + 1; i++) {
                out[i] = Integer.valueOf(line[i - 2]);
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
    }
}
