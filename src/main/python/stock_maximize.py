__author__ = 'nastra'


def getMaxProfit(stockPrices, n):
    optimalPrices = [0] * n
    optimalPrices[n - 1] = stockPrices[n - 1]
    for i in range(n - 2, -1, -1):
        optimalPrices[i] = max(stockPrices[i], optimalPrices[i + 1])

    maxProfit = 0
    shares = 0
    for i in range(0, n):
        if i == n - 1 or optimalPrices[i + 1] < optimalPrices[i]:
            # sell all shares
            maxProfit += shares * optimalPrices[i]
            shares = 0
        elif stockPrices[i] < optimalPrices[i]:
            # buy one share
            shares += 1
            maxProfit -= stockPrices[i]

    return maxProfit


if __name__ == '__main__':
    testcases = int(input())

    for x in range(0, testcases):
        n = int(input())
        prices = [int(p) for p in input().split()]
        print(getMaxProfit(prices, n))