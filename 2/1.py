result = 0

with open('input.txt', 'r') as file:
    for line in file:
        elems = line.split()

        limits = elems[0].split('-')
        min = int(limits[0])
        max = int(limits[1])

        char = elems[1][0]

        count = elems[2].count(char)
        if count <= max and count >= min:
            result += 1

print(result)