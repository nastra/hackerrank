'''
Created on 29.03.2014
@see https://www.hackerrank.com/challenges/halloween-party
@author: nastra
'''

if __name__ == '__main__':
    t = int(input())
    for i in range(0, t):
        k = int(input())
        floor = int(k / 2);
        res = 0
        if(k % 2 == 0):
            res = floor * floor
        else:
            res = floor * (floor + 1)
            
        print(res)
