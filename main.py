import sys
import numpy as np
import scipy.io.wavfile


def update_center(centroids_list: dict, centroids, x, k):
    print(k)
    for c in range(len(centroids_list)):
        samples = centroids_list[c]
        if len(samples) != 0:
            centroids[c] = np.round(np.average(samples, 0))
            print(centroids[c])
    return centroids


def classify(cur_centroids, sample):
    minlenght = np.linalg.norm(cur_centroids[0] - sample)
    mincenter = 0
    lenght = 0
    index = 0
    for center in cur_centroids:
        lenght = np.linalg.norm(center - sample)
        if minlenght > lenght:
            mincenter = index
            minlenght = lenght
        index = index + 1
    return mincenter


def classifyAllSamples(centroids_list: dict, cur_centroids, samples):
    centroids_list1 = dict()
    for samp in samples:
        index = classify(cur_centroids, samp)
        centroids_list1.setdefault(index, []).append(samp)
    return centroids_list1


if __name__ == '__main__':
    sample, centroids = sys.argv[1], sys.argv[2]
    fs, y = scipy.io.wavfile.read(sample)
    x = np.array(y.copy())
    centroids = np.loadtxt(centroids)
    k: int = centroids.size // 2
    centroids_list = dict()
    # first classification#
    centroids_list = classifyAllSamples(centroids_list, centroids, x)
    s = ""
    file = open("output.txt", "w")
    # Iterate 30 times#
    for iter in range(0, 30):
        centroids = update_center(centroids_list, centroids, x, k)
        print(type(centroids))
        centroids_list = classifyAllSamples(centroids_list, centroids, x)
        s = f"[iter {iter}]:{','.join([str(i) for i in centroids])}\n"
        file.write(s)

    scipy.io.wavfile.write("compressed.wav", fs, np.array(centroids, dtype=np.int16))
    file.close()
