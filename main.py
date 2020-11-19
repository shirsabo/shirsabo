import sys
import numpy as np
import scipy.io.wavfile

def most_frequent(List):
    return max(set(List), key = List.count)

def euclidian_distance(self, a, b):
    return np.sqrt(np.sum((a - b) ** 2, axis=1))


def k_nearest_neighbors(data, predict, k=3):

    distances = []
    for group in data:
        for features in data[group]:
            euclidean_distance = np.linalg.norm(np.array(features) - np.array(predict))
            distances.append([euclidean_distance, group])

    votes = [i[1] for i in sorted(distances)[:k]]
    vote_result = most_frequent(votes)
    return vote_result

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    test , train_x, train_y  = sys.argv[1], sys.argv[2] , sys.argv[3]
    xList , yList =[],[]
    with open(train_x, 'r') as readerX:
        with open(train_y, 'r') as readerY:
            lineX ,lineY = readerX.readline(),readerY.readline()
            while lineX != '' and lineY != '':  # The EOF char is an empty string
                x1 =lineX.split(",")
                if x1[11][0] == 'W':
                    x1[11] = 1
                    x1.append(0)
                else:
                    x1[11] = 0
                    x1.append(1)
                xList.append(np.asarray(x1))
                yList.append(lineY)
                lineX = readerX.readline()
                lineY = readerY.readline()
    print (len(xList))

