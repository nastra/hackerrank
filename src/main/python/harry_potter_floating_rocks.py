from fractions import gcd

__author__ = 'nastra'

"""
See https://www.hackerrank.com/contests/101jan14/challenges/harry-potter-and-the-floating-rocks for problem statement
"""


def gcd(a, b):
    if b == 0:
        return a
    else:
        return gcd(b, a % b)


if __name__ == '__main__':
    #from fractions import gcd

    n = int(input())

    sum = 0
    for x in range(0, n):
        line = input().split()
        x1 = int(line[0])
        y1 = int(line[1])
        x2 = int(line[2])
        y2 = int(line[3])
        result = gcd(abs(x2 - x1), abs(y2 - y1)) - 1
        print(result)