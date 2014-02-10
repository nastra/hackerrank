__author__ = 'nastra'

"""
You are given an integer N, can you check if the number is an element of fibonacci series? The first few elements of
fibonacci series are 0,1,1,2,3,5,8,13…. A fibonacci series is one where every element is a sum of the previous two
elements in the series. The first two elements are 0 and 1.

Formally:

fib0 = 0
fib1 = 1
fibn = fibn-1 + fibn-2 ∀ n > 1

Input Format
First line contains T, number of test cases.
T lines follows. Each line will contain an integer N.

Output Format
Output “IsFibo” (without quotes) if N is a fibonacci number and “IsNotFibo” (without quotes) if it is not a fibonacci
number, in a new line corresponding to each test case.

Constraints
1 <= T <= 10^5
1 <= N <= 10^10
"""

import math


def isFibonacci(x):
    base = 5 * x * x
    one = base + 4
    two = base - 4

    firstResult = is_square(one)
    if firstResult:
        return True
    return is_square(two)


def is_square(integer):
    root = math.sqrt(integer)
    if int(root + 0.5) ** 2 == integer:
        return True
    else:
        return False


if __name__ == "__main__":
    n = int(input())

    for i in range(0, n):
        x = int(input())
        result = isFibonacci(x)
        if (result):
            print("IsFibo")
        else:
            print("IsNotFibo")
