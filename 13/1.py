buses = []

with open('input.txt', 'r') as file:
    time = int(file.readline().rstrip('\n'))
    elems = file.readline().rstrip('\n').split(',')
    for elem in elems:
        if elem != 'x':
            buses.append(int(elem))

diffs = [None for _i in range(len(buses))]

for i in range(len(buses)):
    diffs[i] = buses[i] - (time % buses[i])

min_diff_index = min(range(len(diffs)), key=diffs.__getitem__)
earliest_id = buses[min_diff_index]
earliest_diff = diffs[min_diff_index]

print(earliest_id * earliest_diff)
