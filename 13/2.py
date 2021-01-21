buses = []

with open('input.txt', 'r') as file:
    file.readline()
    elems = file.readline().rstrip('\n').split(',')
    for elem in elems:
        if elem != 'x':
            buses.append(int(elem))
        else:
            buses.append(elem)


# Select constraining conditions from input
conditions = []

for i in range(len(buses)):
    bus = buses[i]
    if bus != 'x':
        conditions.append((i, bus))

# Brute force using increasing step
# Chinese remainder theorem could somehow be used for non-numerical solving?
step = 1
t = 0

for offset, bus in conditions:
    while (t + offset) % bus != 0:
        t += step
    step *= bus

print(t)
