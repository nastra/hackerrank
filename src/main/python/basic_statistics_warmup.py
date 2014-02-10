__author__ = 'nastra'

import numpy as np
from scipy import stats
import math


def confidence_interval(x, std):
    mean = x.mean(axis=0)
    part = std / math.sqrt(len(x))
    low = mean - 1.96 * part
    high = mean + 1.96 * part

    return low, high


number_of_values = int(input())
raw_in = input()
num = [int(x) for x in raw_in.split(" ")]
numbers = np.array(num)

mean = np.mean(numbers)
median = np.median(numbers)
modes, num_modes = stats.mode(numbers)
mode = modes.min()
standard_deviation = np.std(numbers)
low, high = confidence_interval(numbers, standard_deviation)

print("%.1f" % (mean))
print("%.1f" % (median))
print("%d" % (mode))
print("%.1f" % (standard_deviation))
print("%.1f %.1f" % (low, high))
