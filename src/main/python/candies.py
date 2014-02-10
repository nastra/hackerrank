__author__ = 'nastra'


def get_minimum_candies(candy, ratings, k):
    if k == 0:
        return 0

    for i in range(1, k):
        c = candy[i]
        left = ratings[i - 1]
        mid = ratings[i]
        right = ratings[i + 1]

        if left < mid <= right:
            c = candy[i - 1] + 1
        elif left < mid and mid > right:
            c = candy[i - 1] + 1
        elif left > mid:
            j = i
            while i > 0 and ratings[j - 1] > ratings[j]:
                candy[j - 1] = max(candy[j - 1], candy[j] + 1)
                j -= 1
        elif left == mid or mid == right:
            c = 1

        candy[i] = c

    return candy


if __name__ == '__main__':
    n = int(input())
    ratings = []
    candy = [1 for j in range(0, n + 1)]

    for x in range(0, n):
        rating = int(input())
        ratings.append(rating)
    ratings.append(0)
    print(sum(get_minimum_candies(candy, ratings, n)) - 1)
