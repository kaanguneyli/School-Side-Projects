import random


def list_the_file(filename):
    f = open(filename, "r")
    data = []
    for line in f:
        line = line.strip()
        items = line.split(',')
        items_float = [float(item) for item in items]
        data.append(items_float)
    f.close()
    return data


def calculate_distance(p1, p2):
    return ((p1[0] - p2[0])**2 + (p1[1] - p2[1])**2) ** 0.5


def k_means(k, filepath):
    data = list_the_file(filepath)
    base_points = []
    for i in range(k):
        base_point = []
        for j in range(len(data[0])):
            c = random.uniform(0, 1)
            base_point.append(c)
        base_points.append(base_point)
    done = False
    iteration_number = 0
    while not done:
        ex_base_points = []
        ownerships = []
        for i in range(len(base_points)):
            ex_base_points.append(base_points[i])
            ownerships.append([])
        for f in range(len(base_points)):
            print("Iteration {}: center {} coordinates: {}".format(iteration_number, f, base_points[f]))
        # datadaki her eleman için
        for each in data:
        # her base_pointe olan mesafeyi bul
            distances = [calculate_distance(point, each) for point in base_points]
        # minimum olanı bul, noktayı o centerın grubuna ekle
            distance = min(distances)
            index = distances.index(distance)
            ownerships[index].append(each)
        # her noktanın listinin ortalamasını bul ve yeni noktayı seç
        for i in range(len(ownerships)):
            sum_x = 0
            sum_y = 0
            count = 0
            for j in range(len(ownerships[i])):
                sum_x += ownerships[i][j][0]
                sum_y += ownerships[i][j][1]
                count += 1
            base_points[i] = [sum_x/count, sum_y/count]
        # centerlar aynıysa bitir
        iteration_number += 1
        if (base_points == ex_base_points):
            done = True

k_means(3, "xclara.csv")


