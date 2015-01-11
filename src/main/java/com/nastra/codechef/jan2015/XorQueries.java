package com.nastra.codechef.jan2015;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * @see http://www.codechef.com/JAN15/problems/XRQRS/
 * @author nastra
 * 
 */
public class XorQueries {

    private static void generateTestData() {
        int queries = 50000;
        System.out.println(queries);
        int count = 1;
        for (int i = 1; i <= queries; i++) {
            System.out.println("0 " + (i - 1));
            if (i % 10 == 0) {
                System.out.println("4 1 " + (i - 1) + " " + i / 2);
                // System.out.println("1 1 " + (i - 1) + " " + i / 2);
                // System.out.println("3 1 " + (i - 1) + " " + i / 2);
                // System.out.println("2 " + Math.min(count, 1000));
                count = 1;
            }
            count++;
        }
    }

    public static void main(String[] args) throws Exception {
        // generateTestData();
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int m = sc.nextInt();
        RandomAccessList list = new RandomAccessList();
        while (m > 0) {
            m--;
            String type = sc.next();
            if (type.equals("0")) {
                list.add(sc.nextInt());
            } else if (type.equals("1")) {
                int left = sc.nextInt() - 1;
                int right = sc.nextInt() - 1;
                int x = sc.nextInt();
                out.println(list.xorMaxInRange(x, left, right));
            } else if (type.equals("2")) {
                int k = sc.nextInt();
                list.deleteLastKNumbers(k);
            } else if (type.equals("3")) {
                int left = sc.nextInt() - 1;
                int right = sc.nextInt() - 1;
                int x = sc.nextInt();
                out.println(list.numberOfIntsLessOrEqualTo(x, left, right));
            } else {
                int left = sc.nextInt() - 1;
                int right = sc.nextInt() - 1;
                int k = sc.nextInt();
                // out.println(list.selectKthOrderStatistic(k, left, right));
                out.println(list.selectIter(k, left, right));
            }
        }
        out.close();
    }

    static class RandomAccessList {
        private int elements = 0;
        private Node head;
        private Node tail;

        private Map<Integer, Node> indexToNodeMap = new HashMap<Integer, Node>();

        private class Node {
            Node next;
            Node prev;
            int value;
            int index;

            public Node(int value) {
                super();
                this.value = value;
            }

            @Override
            public String toString() {
                return "Node [index=" + index + ", value=" + value + "]";
            }

        }

        public void add(int value) {
            elements++;
            Node x = new Node(value);
            if (null == head) {
                x.index = 0;
                head = x;
                tail = x;
            } else {
                x.index = tail.index + 1;
                tail.next = x;
                x.prev = tail;
                tail = x;
            }
            indexToNodeMap.put(x.index, x);
        }

        public void deleteLastKNumbers(int k) {
            int index = elements - k;
            if (index < 0) {
                throw new IllegalArgumentException(k + " is larger than the number of elements, which is " + elements);
            }
            Node prev = indexToNodeMap.get(index - 1);
            Node x = indexToNodeMap.get(index);
            while (null != x) {
                indexToNodeMap.remove(x.index);
                elements--;
                x = x.next;
            }
            if (null != prev) {
                prev.next = null;
                tail = prev;
            } else {
                head = tail = null;
                elements = 0;
            }
        }

        public int xorMaxInRange(int x, int left, int right) {
            Node start = indexToNodeMap.get(left);
            Node end = indexToNodeMap.get(right + 1);
            Node n = start;
            int xorMax = 0;
            int maxValue = 0;
            while (n != end) {
                if (xorMax < (x ^ n.value)) {
                    xorMax = x ^ n.value;
                    maxValue = n.value;
                }
                n = n.next;
            }

            return maxValue;
        }

        public int numberOfIntsLessOrEqualTo(int x, int left, int right) {
            Node start = indexToNodeMap.get(left);
            Node end = indexToNodeMap.get(right + 1);
            Node n = start;
            int count = 0;
            while (n != end) {
                if (n.value <= x) {
                    count++;
                }
                n = n.next;
            }
            return count;
        }

        public int selectKthOrderStatistic(int k, int left, int right) {
            Node start = indexToNodeMap.get(left);
            Node end = indexToNodeMap.get(right + 1);
            Node n = start;
            PriorityQueue<Integer> pq = new PriorityQueue<Integer>(elements, new Comparator<Integer>() {

                public int compare(Integer one, Integer two) {
                    return two.compareTo(one);
                }

            });
            int item = n.value;
            while (n != end) {
                pq.add(n.value);
                n = n.next;
            }
            int i = 0;
            while (i < k) {
                i++;
                item = pq.remove();
            }
            return item;
        }

        public int kthOrderStatistic(int k, int left, int right) {
            Node start = indexToNodeMap.get(left);
            Node end = indexToNodeMap.get(right + 1);
            Node n = start;
            int item = n.value;
            while (n != end) {
                n = n.next;
            }
            return item;
        }

        public Integer selectIter(int k, int left, int right) {
            if (left > right) {
                return null;
            }
            Node start = indexToNodeMap.get(left);
            if (left == right) {
                return start.value;
            }
            while (left <= right) {
                int pivotIndex = randomPartition(left, right);
                // int pivotIndex = randomizedPartition(left, right);
                int leftSize = pivotIndex - left + 1;
                if (leftSize == k) {
                    return indexToNodeMap.get(pivotIndex).value;
                } else if (k < leftSize) {
                    right = pivotIndex - 1;
                } else {
                    left = pivotIndex + 1;
                    k = k - leftSize;
                }
            }
            return null;
        }

        private int randomizedPartition(int low, int high) {
            int randomIndex = Random.randInt(low, high);
            swap(low, randomIndex);
            return partition(low, high);
        }

        private int rand3wayPartition(int low, int high) {
            int randomIndex = Random.randInt(low, high);
            swap(low, randomIndex);
            return partition3way(low, high);
        }

        private int partition3way(int lo, int hi) {
            int lt = lo, gt = hi;
            int v = indexToNodeMap.get(lo).value;
            int i = lo;
            while (i <= gt) {
                if (indexToNodeMap.get(i).value < v)
                    swap(lt++, i++);
                else if (indexToNodeMap.get(i).value > v)
                    swap(i, gt--);
                else
                    i++;
            }
            return lt;
        }

        private int partition(int low, int high) {
            int p = low;
            int i = low + 1;
            for (int j = low + 1; j <= high; j++) {
                if (indexToNodeMap.get(j).value <= indexToNodeMap.get(p).value) {
                    swap(i, j);
                    i++;
                }
            }
            swap(low, i - 1);
            return i - 1;
        }

        private int randomPartition(int leftIndex, int rightIndex) {
            int pivotIndex = medianOf3(leftIndex, rightIndex);
            int pivotValue = indexToNodeMap.get(pivotIndex).value;
            int storeIndex = leftIndex;

            swap(pivotIndex, rightIndex);

            for (int i = leftIndex; i < rightIndex; i++) {
                if (indexToNodeMap.get(i).value <= pivotValue) {
                    swap(storeIndex, i);
                    storeIndex++;
                }
            }

            swap(rightIndex, storeIndex);

            return storeIndex;
        }

        private int medianOf3(int leftIndex, int rightIndex) {
            int centerIndex = (leftIndex + rightIndex) / 2;

            if (indexToNodeMap.get(leftIndex).value > indexToNodeMap.get(rightIndex).value) {
                swap(leftIndex, centerIndex);
            }

            if (indexToNodeMap.get(leftIndex).value > indexToNodeMap.get(rightIndex).value) {
                swap(leftIndex, rightIndex);
            }

            if (indexToNodeMap.get(centerIndex).value > indexToNodeMap.get(rightIndex).value) {
                swap(centerIndex, rightIndex);
            }

            swap(centerIndex, rightIndex - 1);

            return rightIndex - 1;
        }

        private void swap(int i, int j) {
            Node one = indexToNodeMap.get(i);
            // Node onePrev = one.prev;
            // Node oneNext = one.next;
            Node two = indexToNodeMap.get(j);
            // one.prev = two.prev;
            // one.next =
            int tmp = two.value;
            two.value = one.value;
            one.value = tmp;
        }

        public void printInorder() {
            Node start = head;
            while (start != null) {
                System.out.print(start + " -> ");
                start = start.next;
            }
            System.out.println();
        }

        public static void main(String... args) {
            RandomAccessList list = new RandomAccessList();
            System.out.println("add some elements");
            list.add(8);
            list.printInorder();
            list.add(4);
            list.printInorder();
            list.add(7);
            list.printInorder();
            list.add(3);
            list.printInorder();
            list.add(12);
            list.printInorder();

            System.out.println("delete last 4: ");
            list.deleteLastKNumbers(4);
            list.printInorder();

            System.out.println("add some elements...");
            list.add(4);
            list.printInorder();
            list.add(4);
            list.printInorder();
            list.add(4);
            list.printInorder();

            System.out.println("delete last 2: ");
            list.deleteLastKNumbers(2);
            list.printInorder();

            System.out.println("add some elements...");
            list.add(4);
            list.printInorder();
            list.add(4);
            list.printInorder();
            list.add(4);
            list.printInorder();

            System.out.println("delete all...");
            list.deleteLastKNumbers(list.elements);
            list.printInorder();

            System.out.println("XOR");
            list.add(8);
            list.add(2);
            System.out.println(list.xorMaxInRange(7, 1, 1));
            System.out.println(list.xorMaxInRange(7, 1, 1));
            list.add(1);
        }

    }

    static class Random {

        private static java.util.Random random = new java.util.Random();

        public static int randInt(int min, int max) {

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int randomNum = random.nextInt((max - min) + 1) + min;

            return randomNum;
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
