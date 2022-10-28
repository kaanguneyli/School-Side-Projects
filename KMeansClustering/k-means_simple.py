import random

'''K-means clustering is an unsupervised clustering method which means it attempts to group similar data together, without knowing which group the data belongs to.
It works in an iterative manner:
    First, you choose some k random data points, which needs to have the same dimensionality as your data. Meaning: our dataset has the x and y coordinates, so our k points, which we will call cluster centers, centers or centroids needs to have x and y coordinates as well.
    Then you check for all your data for the Euclidean distance between each point and each center.
    Then you record somewhere which center is closest to each data point, lists can be useful here.
    Then you update the location of each center. You do this by looking at your records, and summing up all the data points belonging to a center, and dividing that sum with the number of data points belonging to that center. This is basically taking average, you are determining where the center should be moved to based on the points closest to it.
That's it, you can repeat it for a number of times; or you can repeat until there is no change for any points between two iterations.
Note that since you start from random points, which you can also modify, the algorithm does not always work out the same, and may even find results which are not very good.'''

# This video visualizing what is happening when we do this can help you understand better. https://youtu.be/nXY6PxAaOk0

# Please try to implement this yourselves or at least take a look at it when you have some free time, I implemented this using only the things you have learned in this course.


def get_l2_distance(p1, p2):
    squared_dist = 0
    for i in range(len(p1)):
        squared_dist += (p1[i] - p2[i])**2
    return squared_dist**0.5


def read_data(filepath):
    f = open(filepath, "r")
    data = []
    for line in f:
        s = line.strip()
        features = s.split(',')
        converted = [float(fv) for fv in features]
        data.append(converted)
    f.close()
    return data


def k_means(filepath, k, iter_count):
    data = read_data(filepath)
    centers = []
    flag = True
    for i in range(k):
        center = []
        for j in range(len(data[0])):
            c = random.uniform(0, 1)
            center.append(c)
        centers.append(center)
    for i in range(iter_count):
        for f in range(len(centers)):
            print("Iteration {}: center {} coordinates: {}".format(i, f, centers[f]))
        ownerships = []
        center_sums = [[0 for g in range(len(data[0]))] for t in range(len(centers))]
        flag = False
        for each in data:
            distances = []
            for c in centers:
                distances.append(get_l2_distance(c, each))
            min_val = min(distances)
            min_ind = distances.index(min_val)
            ownerships.append(min_ind)
        for j in range(len(ownerships)):
            center_sums[ownerships[j]] = [center_sums[ownerships[j]][dim] + data[j][dim] for dim in range(len(data[j]))]
        for j in range(len(center_sums)):
            centers[j] = [center_sums[j][m] / ownerships.count(j) for m in range(len(center_sums[j]))]


k_means("xclara.csv", 3, 10)  # You can change the number of clusters (k) to use, or the number of iterations.

# If you wish, you can find your own dataset (or maybe create your own dataset using the random module),
# and after handling the input part use this algorithm.
