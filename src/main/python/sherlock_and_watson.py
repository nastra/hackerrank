'''
Created on 15.02.2014

@author: nastra
@see: https://www.hackerrank.com/contests/feb14/challenges/sherlock-and-watson
'''
def reverse(numbers, lo, hi):
    i = lo
    j = hi
    while (i < j):
        numbers[i], numbers[j] = numbers[j], numbers[i]
        i += 1
        j -= 1
        
def solve(numbers, n, k):
    boundary = n - k
    reverse(numbers, 0, boundary - 1)
    reverse(numbers, boundary, n - 1)
    reverse(numbers, 0, n - 1)
    
    
if __name__ == '__main__':
    n, k, q = [int(i) for i in input().split(sep=" ")]
    numbers = [int(i) for i in input().split(sep=" ")]
    solve(numbers, n, k)
    for _ in range(0, q):
        print(numbers[int(input())])
    
