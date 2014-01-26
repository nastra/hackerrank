__author__ = 'nastra'
import sys
from sklearn.linear_model import LinearRegression
import numpy as np

training_data = """2.81,5.62
7.14,8.00
2.72,5.44
3.87,7.74
1.90,3.80
7.82,8.00
7.02,8.00
5.50,8.00
9.15,8.00
4.87,8.00
8.08,8.00
5.58,8.00
9.13,8.00
0.14,0.28
2.00,4.00
5.47,8.00
0.80,1.60
4.37,8.00
5.31,8.00
0.00,0.00
1.78,3.56
3.45,6.90
6.13,8.00
3.53,7.06
4.61,8.00
1.76,3.52
6.39,8.00
0.02,0.04
9.69,8.00
5.33,8.00
6.37,8.00
5.55,8.00
7.80,8.00
2.06,4.12
7.79,8.00
2.24,4.48
9.71,8.00
1.11,2.22
8.38,8.00
2.33,4.66
1.83,3.66
5.94,8.00
9.20,8.00
1.14,2.28
4.15,8.00
8.43,8.00
5.68,8.00
8.21,8.00
1.75,3.50
2.16,4.32
4.93,8.00
5.75,8.00
1.26,2.52
3.97,7.94
4.39,8.00
7.53,8.00
1.98,3.96
1.66,3.32
2.04,4.08
11.72,8.00
4.64,8.00
4.71,8.00
3.77,7.54
9.33,8.00
1.83,3.66
2.15,4.30
1.58,3.16
9.29,8.00
1.27,2.54
8.49,8.00
5.39,8.00
3.47,6.94
6.48,8.00
4.11,8.00
1.85,3.70
8.79,8.00
0.13,0.26
1.44,2.88
5.96,8.00
3.42,6.84
1.89,3.78
1.98,3.96
5.26,8.00
0.39,0.78
6.05,8.00
1.99,3.98
1.58,3.16
3.99,7.98
4.35,8.00
6.71,8.00
2.58,5.16
7.37,8.00
5.77,8.00
3.97,7.94
3.65,7.30
4.38,8.00
8.06,8.00
8.05,8.00
1.10,2.20
6.65,8.00"""


def get_data(from_file=True):
    if from_file:
        data = open('trainingdata.txt')
        out = ''
        for line in data:
            out += line
        return out
    else:
        return training_data


def load_data():
    data = get_data(False)
    features = []
    targets = []
    for line in data.splitlines():
        one, two = line.split(',')
        features.append([float(one)])
        targets.append([float(two)])

    features = np.array(features)
    targets = np.array(targets)
    features = features[features < 4]
    targets = targets[targets < 8]
    features = features[features > 0]
    targets = targets[targets > 0]

    return features, targets


def extract_and_clean_data():
    X, y = load_data()
    features = [[f] for f in X]
    targets = [[t] for t in y]
    return np.array(features), np.array(targets)


#def plot_data(X, y, models):
#    import matplotlib.pyplot as plt
#
#    plt.scatter(X, y)
#    plt.xlabel("charging time")
#    plt.ylabel("battery life")
#    plt.autoscale(tight=True)
#    plt.grid()
#    plt.savefig('chart.png')


def train_classifier():
    X, y = extract_and_clean_data()
    #plot_data(X, y, None)
    classifier = LinearRegression()
    return classifier.fit(X, y)


if __name__ == '__main__':
    classifier = train_classifier()
    data = float(sys.stdin.readline())
    prediction = classifier.predict(data)
    predicted = prediction[0][0]

    if data >= 4.00:
        print 8.00
    else:
        print "{:0.2f}".format(round(predicted, 2))
