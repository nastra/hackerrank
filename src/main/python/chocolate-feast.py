'''
Created on 29.03.2014

@see https://www.hackerrank.com/challenges/chocolate-feast
@author: nastra
'''
import math

if __name__ == '__main__':
    t = int(input())
    for i in range(0, t):
        N, C, M = [int(i) for i in input().split()]
        total = 0
        total = int(N / C)
        curr = total
        
        while curr >= M:
            wrappers = int(curr / M)
            total = total + wrappers
            curr = wrappers + (curr % M)
            
        print(total)
