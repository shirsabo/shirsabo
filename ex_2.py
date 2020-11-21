import random
import sys
import numpy as np
import scipy.io.wavfile
from sklearn.utils import shuffle

def create_matrix():
    arr = []
    for i in range (3):
        arr.append(np.append(np.random.uniform(0, 1, 12),0))
    return arr

def preceptron_accuracy(xlist,ylist):
    n = len(xlist)
    size = round(n * 0.8)
    linetrainX = xlist[:size]
    linetrainy = ylist[:size]
    testx = xlist[size:]
    testy = ylist[size:]
    loss = 0
    matrix = preceptron_algo(linetrainX,linetrainy)
    for i in range (len(testx)):
        y_hat = np.argmax(np.dot(matrix,np.append(testx[i],1)))
        print (y_hat,int(testy[i]))
        if int(testy[i]) != y_hat:
            loss  = loss +1
    print("preceptron_accuracy")
    print (loss)

def preceptron_algo(pxlist,pylist):
    ephocs = 13
    learningRate = 0.2
    eta = np.array([learningRate]*13)
    eta [12] = 1
    matrix = create_matrix()
    for e in range(ephocs):
        Xconvert = []
        x_train, y_train = shuffle(pxlist, pylist, random_state=1)
        for i in range(0,len(pxlist)):
            print (i)
            Xconvert.append(np.append(x_train[i], 1))
        x_train = Xconvert
        for x, y in zip(x_train, y_train):
            y_hat = np.argmax(np.dot(matrix, x))
            if y != y_hat:
                matrix[int(y)] = matrix[int(y)] + learningRate * x
                matrix[int(y)][len(matrix[int(y)])-1] = matrix[int(y)][len(matrix[int(y)])-1] + 1
                matrix[int(y_hat)] = matrix[int(y_hat)] - learningRate * x
                matrix[int(y_hat)][len(matrix[int(y_hat)]) - 1] = matrix[int(y_hat)][len(matrix[int(y_hat)]) - 1] - 1
    print (matrix)
    print("--------------------")
    return matrix

#-----------------------------------------------
def norm_vec(Xlist, testIn, flag):
    if flag == 1:
        tests = TestList()
    elif flag == 0:
        tests = testIn
    minVal, maxVal = min_max(Xlist)
    y = np.array(np.concatenate((tests, Xlist.copy())))
    for i in range(0, len(Xlist)):
        for j in range(0, len(minVal)):
            # print (i,j)
            Xlist[i][j] = (Xlist[i][j] - minVal[j]) / (maxVal[j] - minVal[j])
    for testElement in range(i, len(y)):
        for col in range(0, len(minVal)):
            if (testElement - i - 1 >= 0):
                tests[testElement - i - 1][col] = (tests[testElement - i - 1][col] - minVal[col]) / (
                            maxVal[col] - minVal[col])
    return Xlist, tests


def min_max(Xlist):
    n = len(Xlist)
    m = len(Xlist[0])
    matrix = []
    for i in range(n):
        matrix.append(np.asarray(Xlist[i]).astype(np.double))
    cols_num = len(matrix[0])
    min_vals, max_vals = [0] * m, [0] * m
    for y in range(0, m):
        val = matrix[0][y]
        min_vals[y], max_vals[y] = val, val
        for x in range(0, n):
            if matrix[x][y] > max_vals[y]:
                max_vals[y] = matrix[x][y]
            if matrix[x][y] < min_vals[y]:
                min_vals[y] = matrix[x][y]
    return min_vals, max_vals


def validation(line1x, line1y):
    n = len(line1x)
    size = round(n * 0.8)
    dic1 = {}
    for i in range(size):
        dic1.setdefault(line1y[i].replace('\n', ''), []).append(np.asarray(line1x[i]).astype(np.double))
    linetrain = line1x[:size]
    line1x = line1x[size:]
    linetrain, line1x = norm_vec(linetrain, line1x, 0)
    line1y = line1y[size:]
    loss = 0;
    iindex = 0
    minLoss = -1;
    dictemp = dict(dic1)
    for k in range(1, 20):
        loss = 0
        for e in range(0, len(line1x)):
            # print (k,e )
            predict = k_nearest_neighbors(dictemp, dic1, np.asarray(line1x[e]).astype(np.double), k)
            if predict != line1y[e].replace('\n', ''):
                loss = loss + 1
        print(loss)
        if (loss / len(line1x)) <= 0.2:
            print(loss / len(line1x))
            return k


def TestList():
    testList = []
    with open(test, 'r') as readerTest:
        testVec = readerTest.readline()
        while testVec != '':
            testVec = convertVec(testVec.split(","))
            testList.append(np.asarray(testVec).astype(np.float))
            testVec = readerTest.readline()
    return testList


def KNN_algo(dic2, listTest, k):
    # print (len(testList))

    for i in range(len(listTest)):
        predict = k_nearest_neighbors(dic2, dic2, np.asarray(listTest[i]).astype(np.double), k)
        print("knn: " + predict)


def convertVec(x1):
    if x1[11][0] == 'W':
        x1[11] = "1"
    # x1.append("0")
    elif x1[11][0] == 'R':
        x1[11] = "0"
    # x1.append("1")
    return x1


def most_frequent(List):
    return str(np.bincount(List).argmax())


def euclidianDistance(self, a, b):
    return np.sqrt(np.sum((a - b) ** 2, axis=1))


def k_nearest_neighbors(tempData, data, predict, k):
    distances = []
    for key, value in tempData.items():
        for vec in value:
            euclidean_distance = np.linalg.norm(vec - predict)
            distances.append([euclidean_distance, key])
    votes = [i[1] for i in sorted(distances)[:k]]
    # votes.sort(reverse=True)
    vote_result = most_frequent(votes)
    return vote_result


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    test, train_x, train_y = sys.argv[1], sys.argv[2], sys.argv[3]
    xList, yList = [], []
    dic = {}
    Xlist, Ylist = [], []
    with open(train_x, 'r') as readerX:
        with open(train_y, 'r') as readerY:
            lineX, lineY = readerX.readline(), readerY.readline()
            while lineX != '' and lineY != '':  # The EOF char is an empty string
                x1 = lineX.split(",")
                x1 = convertVec(x1)
                Xlist.append(np.asarray(x1).astype(np.double))
                Ylist.append(lineY)
                # dic[lineY.replace('\n', '')] = np.asarray(x1).astype(np.double)
                lineX = readerX.readline()
                lineY = readerY.readline()
    Xlist, testList = norm_vec(np.asarray(Xlist), None, 1)
    for i in range(len(Xlist)):
        dic.setdefault(Ylist[i].replace('\n', ''), []).append(Xlist[i])
    KNN_algo(dic, testList, validation(Xlist, Ylist))
    preceptron_accuracy(Xlist, Ylist)
    # KNN_algo(dic,testList,4)
