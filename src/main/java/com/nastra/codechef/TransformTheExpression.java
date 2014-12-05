package com.nastra.codechef;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/problems/ONP/
 * @author nastra
 * 
 */
public class TransformTheExpression {

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int t = sc.nextInt();
        while (t > 0) {
            t--;
            out.println(InfixToPostfixTranslator.translate(sc.nextLine()));
        }

        out.close();
    }

    static class InfixToPostfixTranslator {

        private static final String OPERAND_REGEX = "[A-Za-z0-9]";
        private static final char LEFT_PARENTHESIS = '(';
        private static final char RIGHT_PARENTHESIS = ')';

        public static String translate(String in) {
            if (null == in || in.isEmpty()) {
                return in;
            }
            StringBuilder out = new StringBuilder();
            Stack<Character> stack = new Stack<Character>();

            for (int i = 0; i < in.length(); i++) {
                char current = in.charAt(i);
                if (isOperand(current)) {
                    out.append(current);
                } else if (isLeftParenthesis(current) || isOperator(current)) {
                    if (isOperator(current) && higherOrEqualOperatorOnStack(stack, current) && !isRightParenthesis(current)) {
                        out.append(stack.pop());
                    }
                    stack.push(current);
                } else if (isRightParenthesis(current)) {
                    while (!isLeftParenthesis(stack.peek())) {
                        out.append(stack.pop());
                    }
                    stack.pop(); // pop the remaining '('
                }
            }

            while (!stack.isEmpty()) {
                out.append(stack.pop());
            }
            return out.toString();
        }

        private static boolean isOperand(char current) {
            return Character.valueOf(current).toString().matches(OPERAND_REGEX);
        }

        private static boolean isLeftParenthesis(char current) {
            return LEFT_PARENTHESIS == current;
        }

        private static boolean isRightParenthesis(char current) {
            return RIGHT_PARENTHESIS == current;
        }

        private static boolean isOperator(char current) {
            return Operator.PLUS.operator == current || Operator.MINUS.operator == current || Operator.MULTIPLY.operator == current
                    || Operator.DIVIDE.operator == current || current == Operator.XOR.operator;
        }

        private static boolean higherOrEqualOperatorOnStack(Stack<Character> stack, char current) {
            return !stack.isEmpty() && (precedenceOf(stack.peek()) >= precedenceOf(current));
        }

        private static int precedenceOf(char operator) {
            if (operator == Operator.PLUS.operator || operator == Operator.MINUS.operator) {
                return Operator.PLUS.precedence;
            } else if (operator == Operator.MULTIPLY.operator || operator == Operator.DIVIDE.operator || operator == Operator.XOR.operator) {
                return Operator.MULTIPLY.precedence;

            }
            return 0;
        }

        private static enum Operator {

            PLUS('+', 1), MINUS('-', 1), MULTIPLY('*', 2), DIVIDE('/', 2), XOR('^', 2);
            private char operator;
            private int precedence;

            private Operator(char operator, int precedence) {
                this.operator = operator;
                this.precedence = precedence;
            }
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
