package com.nastra.hackerrank;

import com.nastra.hackerrank.util.FastScanner;

/**
 * One classic method for composing secret messages is called a square code. The spaces are removed from the english text and the characters are
 * written into a square (or rectangle). The width and height of the rectangle have the constraint,
 * 
 * floor(sqrt(word)) <= width, height <= ceil(sqrt(word))
 * 
 * Among the possible squares, choose the one with the minimum area.
 * 
 * in case of rectangle number of rows is kept smaller than number of columns) . For example, the sentence “if man was meant to stay on the ground god
 * would have given us roots” is 54 characters long, so it is written into a rectangle with 7 rows and 8 columns.
 * 
 * ifmanwas meanttos tayonthe groundgo dwouldha vegivenu sroots
 * 
 * The coded message is obtained by reading down the columns going left to right. For example, the message above is coded as:
 * 
 * imtgdvs fearwer mayoogo anouuio ntnnlvt wttddes aohghn sseoau
 * 
 * You will be given a message in english with no spaces between the words.The maximum message length be 81 characters. Print the encoded message. In
 * the encoded message there should be a space between any two consecutive words.
 * 
 * 
 * @see https://www.hackerrank.com/challenges/encryption
 * @author nastra - Eduard Tudenhoefner
 */
public class Encryption {

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        String word = sc.next();

        System.out.println(solve(word));
    }

    private static String solve(String word) {
        char[][] squareCode = encrypt(word);
        StringBuilder out = new StringBuilder();

        int maxRows = squareCode.length;
        int maxCols = squareCode[0].length;

        int row = 0;
        int col = 0;

        while (true) {
            if (row >= maxRows) {
                row = 0;
                col++;
                out.append(" ");
            }
            if (col >= maxCols) {
                break;
            }
            char c = squareCode[row][col];
            if (c == '~') {
                row++;
                continue;
            } else {
                out.append(c);
            }
            row++;
        }

        return out.toString().trim();
    }

    private static char[][] encrypt(String word) {
        int len = word.length();
        double row = Math.floor(Math.sqrt((double) len));
        double col = Math.ceil(Math.sqrt((double) len));

        // find smallest possible area where all characters fit into
        if (((int) row * col) < len) {
            double min = Math.min(row, col);
            if (min == row) {
                row++;
            } else {
                col++;
            }
        }

        char[][] squareCode = new char[(int) row][(int) col];
        int k = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (k < len) {
                    squareCode[i][j] = word.charAt(k);
                    k++;
                } else {
                    squareCode[i][j] = '~';
                }
            }
        }
        return squareCode;
    }
}
