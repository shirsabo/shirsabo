import random
import sys
import numpy as np
import scipy.io.wavfile
from sklearn.model_selection import KFold
from sklearn.utils import shuffle
import copy
def kfold_knn(xlist,ylist,k):
    xlist, ylist = shuffle(xlist, ylist, random_state=None)
    #print(xlist)
    n = len(xlist)
    maxAcurancy = - 1
    maxaccurancyIndex = 0
    Xtrain, Ytrain, Xtest, Ytest, accurancyI = [], [], [], [], []
    x1 = xlist
    kf = KFold(n_splits=5)
    kf.get_n_splits(x1)
    sum = 0;
    # print(kf)
    counter = 0
    for train_index, test_index in kf.split(x1):
        Xtrain, Ytrain, Xtest, Ytest = [], [], [], []
        for index in train_index:
            Xtrain.append(xlist[index])
            Ytrain.append(ylist[index])
        for index1 in test_index:
            Xtest.append(xlist[index1])
            Ytest.append(ylist[index1])
        accurancy = KNN_accuracy(Xtrain,Ytrain,Xtest,Ytest, k)
        #print(accurancy)
        sum = sum + accurancy
        counter = counter + 1
        #print(counter)
    #print("avergage for KNN is :")
    #print(sum / counter)
    return (sum / counter)
def KNN_accuracy(linetrainX,linetrainy,testx,testy, k):
    linetrainX, testx = norm_vec(linetrainX, testx, 0)
    dic1 = {}
    for i in range(0, len(linetrainX)):
        dic1.setdefault(linetrainy[i].replace('\n', ''), []).append(np.asarray(linetrainX[i]).astype(np.double))
    loss = 0
    iindex = 0
    minLoss = -1;
    dictemp = dict(dic1)
    loss = 0
    for e in range(0, len(testx)):
        # print (k,e )
        predict = k_nearest_neighbors(dictemp, dic1, np.asarray(testx[e]).astype(np.double), k)
       # print(predict, testy[e])
        if predict != testy[e].replace('\n', ''):
            loss = loss + 1
    return (1 - (loss / len(testx)))


def printPredictions(KNN,PR,PA):
    for i in range (len(KNN)):
        print(f"knn: {KNN[i]}, perceptron: {PR[i]}, pa: {PA[i]}")
def run_algo(linetrainX,linetrainy,testx,func,param):
    prediction = []
    linetrainX ,testx = norm_vec(linetrainX, testx, 0)
    matrix = func(linetrainX, linetrainy,param)
    for i in range(len(testx)):
        y_hat = np.argmax(np.dot(matrix, np.append(testx[i], 1)))
        prediction.append(y_hat)
    return prediction

def Kfold(xlist,ylist,func,param):
    xlist, ylist = shuffle(xlist,ylist, random_state=None)
    x1 = xlist
    kf = KFold(n_splits=10)
    kf.get_n_splits(x1)
    sum = 0;
    counter = 0
    for train_index, test_index in kf.split(x1):
        Xtrain, Ytrain, Xtest, Ytest = [], [], [], []
        for index in train_index:
            Xtrain.append(xlist[index])
            Ytrain.append(ylist[index])
        for index1 in test_index:
            Xtest.append(xlist[index1])
            Ytest.append(ylist[index1])
        accuracy = func(Xtrain, Ytrain, Xtest, Ytest,param)
        sum = sum + accuracy
        counter = counter + 1
    print(sum/counter)
    return sum/counter



def PA_accuracy(linetrainX,linetrainy,testx,testy,param):
    linetrainX,testx = norm_vec(linetrainX, testx, 0)
    loss = 0
    matrix = PA_algo(linetrainX, linetrainy,param)
    for i in range(len(testx)):
        y_hat = np.argmax(np.dot(matrix, np.append(testx[i], 1)))
        #print(y_hat, int(testy[i]))
        if int(testy[i]) != y_hat:
            loss = loss + 1
    return 1-(loss/len(testx))

def calculate_hinge(predict, actual, x):
    print ("norm actual",np.dot(actual, x))
    print ("norm predict",np.dot(predict, x))
    return max(0, 1 - np.dot(actual, x) + np.dot(predict, x))


def calculate_tao(w,y,y_hat, x):
   w_y_x = np.dot (w[y,:],x)
   w_y_hat_x = np.dot (w[y_hat,:],x)
   loss = max (0,1 - w_y_x + w_y_hat_x)
   x_norm = np.linalg.norm(x)
   if (x_norm == 0):
       print ("heyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy")
       return 0
   return loss/(2*(np.power(x_norm,2)))


def PA_algo(pxlist, pylist,param):
    ephocs = param[0]
    matrix = create_matrix()
    for e in range(ephocs):
        Xconvert = []
        x_train, y_train = shuffle(pxlist, pylist,random_state=None)
        for i in range(0, len(pxlist)):
            Xconvert.append(np.append(x_train[i], 1))
        x_train = Xconvert
        for x, y in zip(x_train, y_train):
            Dot = np.dot(matrix, x)
            expect_index = [int(y)]
            m = np.zeros(Dot.size, dtype=bool)
            m [expect_index] = True
            w_no_y = np.ma.array(Dot, mask=m)
            y_hat = np .argmax(w_no_y)
            tao = calculate_tao(matrix, int(y),int (y_hat), x)
            matrix[int(y),:] = matrix[int(y),:] + (tao * x)
            matrix[int(y_hat),:] = matrix[int(y_hat),:] - (tao * x)

    return matrix

# ----------------------------------------------------
def create_matrix():
    arr = np.zeros(shape= (3,13))
    return arr


def preceptron_accuracy(linetrainX,linetrainy,testx,testy,param):
    linetrainX,testx = norm_vec(linetrainX, testx, 0)
    loss = 0
    matrix = preceptron_algo(linetrainX, linetrainy,param)
    for i in range(len(testx)):
        y_hat = np.argmax(np.dot(matrix, np.append(testx[i], 1)))
        if int(testy[i]) != y_hat:
            loss = loss + 1
    return (1-(loss/len(testx)))


def preceptron_algo(pxlist, pylist,param):
    ephocs = param[0]
    learningRate = param[1]
    eta = np.array([learningRate] * 13)
    eta[12] = 1
    matrix = create_matrix()
    for e in range(ephocs):
        Xconvert = []
        x_train, y_train = shuffle(pxlist, pylist, random_state=None)
        for i in range(0, len(pxlist)):
            Xconvert.append(np.append(x_train[i], 1))
        x_train = Xconvert
        for x, y in zip(x_train, y_train):
            y_hat = np.argmax(np.dot(matrix, x))
            if int(y) != int(y_hat):
                matrix[int(y),:] = matrix[int(y),:] + learningRate * x
                matrix[int(y_hat),:] = matrix[int(y_hat),:] - learningRate * x
    return matrix


# -----------------------------------------------
def norm_vec(Xlist, testIn, flag):
    XlistCopy = Xlist.copy()
    if flag == 1:
        tests = TestList()
    elif flag == 0:
        tests = testIn
    testInCopy =  tests.copy()
    minVal, maxVal = min_max(Xlist)
    y = np.array(np.concatenate((tests, Xlist.copy())))
    for i in range(0, len(Xlist)):
        for j in range(0, len(minVal)):
            # print (i,j)
            XlistCopy[i][j] = (XlistCopy[i][j] - minVal[j]) / (maxVal[j] - minVal[j])
    for testElement in range(0, len(tests)):
        for col in range(0, len(minVal)):
                testInCopy[testElement][col] = (testInCopy[testElement][col] - minVal[col]) / (maxVal[col] - minVal[col])
    return XlistCopy, testInCopy


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
    size = round(n * 0.9)
    linetrain = line1x[:size]
    line1x = line1x[size:]
    linetrain, line1x = norm_vec(linetrain, line1x, 0)
    line1y = line1y[:size]
    dic1 = {}
    for i in range(0,size):
        dic1.setdefault(line1y[i].replace('\n', ''), []).append(np.asarray(linetrain[i]).astype(np.double))
    loss = 0;
    iindex = 0
    minLoss = -1;
    dictemp = dict(dic1)
    for k in range(1, 150):
        loss = 0
        for e in range(0, len(line1x)):
            # print (k,e )
            predict = k_nearest_neighbors(dictemp, dic1, np.asarray(line1x[e]).astype(np.double), k)
            #print(predict, line1y[e])
            if predict != line1y[e].replace('\n', ''):
                loss = loss + 1
                #print(loss / len(line1x))
        if (1 - (loss / len(line1x))) >= 0.8:
            print(loss / len(line1x))
            print(k)
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
    prdeiction = []

    for i in range(len(listTest)):
        predict = k_nearest_neighbors(dic2, dic2, np.asarray(listTest[i]).astype(np.double), k)
        prdeiction.append(predict)
        #print ("knn:", predict)
    return prdeiction


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
    XlistCopyKNN = []
    XlistCopyPR = []
    XlistCopyPA = []
    XlistTemp = []
    dic = {}
    Xlist, Ylist = [], []
    with open(train_x, 'r') as readerX:
        with open(train_y, 'r') as readerY:
            lineX, lineY = readerX.readline(), readerY.readline()
            while lineX != '' and lineY != '':  # The EOF char is an empty string
                x1 = lineX.split(",")
                x1 = convertVec(x1)
                Xlist.append(np.asarray(x1).astype(np.double))
                XlistCopyKNN.append(np.asarray(x1).astype(np.double))
                XlistCopyPR.append(np.asarray(x1).astype(np.double))
                XlistCopyPA.append(np.asarray(x1).astype(np.double))
                XlistTemp.append(np.asarray(x1).astype(np.double))
                Ylist.append(lineY)
                # dic[lineY.replace('\n', '')] = np.asarray(x1).astype(np.double)
                lineX = readerX.readline()
                lineY = readerY.readline()
    Xlist, testList = norm_vec(np.asarray(Xlist), None, 1)
    for i in range(len(Xlist)):
        dic.setdefault(Ylist[i].replace('\n', ''), []).append(Xlist[i])
    predictionKNN = KNN_algo(dic,  testList, 7)
 #   preceptron_accuracy(XlistCopyPR, Ylist)
  #  PA_accuracy(Xlist, Ylist)
    # KNN_algo(dic,testList,4)
    #Kfold(XlistCopyKNN, Ylist,preceptron_accuracy)
    maxVal = 0
    maxIndex = 0
    arr = [0]*30
    '''
    for j in range (20):
        print (j)
        print (XlistCopyKNN)
        for i in range(2, 30):
            val = kfold_knn(copy.deepcopy(XlistCopyKNN), Ylist, i)
            if val > maxVal:
                maxVal = val
                maxIndex = i
        arr[maxIndex] =  arr[maxIndex] +1
    print (arr)
    print (max(arr))

    print ("max k:")
    print (maxVal,maxIndex)
     '''
    val = kfold_knn(copy.deepcopy(XlistCopyKNN), Ylist, 7)
    print("knn accurancy:")
    print(val)
    maxVal = 0
    maxIndex = 0
    arr =[[0]*30,[0] * 150]
    val =0
    sum =0
    counter = 0
    for j in range (0,10):
        print(j)
        maxVal = 0
        param = [60,0.005]
        val =Kfold(copy.deepcopy(XlistCopyPR), Ylist, preceptron_accuracy, param)
        if (val<0.65):
            counter = counter + 1
        sum = sum +val
        print (val )
    print ("avg:",sum / 10)
    val = Kfold(copy.deepcopy(XlistCopyPA), Ylist, PA_accuracy,param)
    print("pa accurancy:")
    print(val)
    TestL = TestList()
    prdeictionPR = run_algo( XlistCopyPR, Ylist, TestList(), preceptron_algo,param)
    param = [10]
    prdeictionPA = run_algo(copy.deepcopy(XlistCopyPA), Ylist, TestList(), PA_algo,param)
    printPredictions(predictionKNN,prdeictionPR ,  prdeictionPA)
