__author__ = 'nastra'

"""
See https://www.hackerrank.com/contests/101jan14/challenges/filling-jars for problem statement
"""

if __name__ == '__main__':
    line = input().split()
    n = int(line[0])
    m = int(line[1])

    sum = 0
    for x in range(0, m):
        line = input().split()
        a = int(line[0])
        b = int(line[1])
        k = int(line[2])
        sum += ((b - a + 1) * k)
    print(sum // n)

