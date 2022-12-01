result = 0

with open('input.txt', 'r') as file:
    for line in file:
        elems = line.split()
        
        limits = elems[0].split('-')
        pos1 = int(limits[0])
        pos2 = int(limits[1])
        
        char = elems[1][0]

        passw = elems[2]

        count = 0
        if passw[pos1 - 1] == char:
            count += 1
        if passw[pos2 - 1] == char:
            count += 1

        if count == 1:
            result += 1

print(result)
