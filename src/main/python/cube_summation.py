import numpy as np

"""
See https://www.hackerrank.com/contests/101jan14/challenges/cube-summation for problem statement
"""

if __name__ == '__main__':
    t = int(input())
    for i in range(0, t):
        n, m = (int(i) for i in input().split())
        matrix = x = np.zeros((n + 1, n + 1, n + 1))
        for j in range(0, m):
            op = input().split()
            if 'UPDATE' == op[0]:
                a = int(op[1])
                b = int(op[2])
                c = int(op[3])
                value = int(op[4])
                matrix[a, b, c] = value
            else:
                a = int(op[1])
                b = int(op[2])
                c = int(op[3])
                d = int(op[4])
                e = int(op[5])
                f = int(op[6])
                tot = np.sum(matrix[a:d + 1:1, b:e + 1:1, c:f + 1:1])
                print(int(tot))

