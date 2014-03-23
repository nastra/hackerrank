'''
@see: https://www.hackerrank.com/contests/mar14/challenges/sherlock-and-squares
'''

import math
def is_perfect_square(n):
    return math.sqrt(n).is_integer()


def solve(a, b):
    if x == y:
        return 1
    return b - a + 1

    
def next_perfect_squares(a, b):
    x = math.ceil(math.sqrt(a))
    y = math.floor(math.sqrt(b))
    return x, y

if __name__ == '__main__':
    T = int(input())
    for _ in range(0, T):
        A, B = [int(i) for i in input().split()]
        x, y = next_perfect_squares(A, B)
        print(int(solve(x, y)))
