forest = []
result = 0

with open('input.txt', 'r') as file:
    for line in file:
        forest.append(line.rstrip('\n\r'))

width = len(forest[0])
height = len(forest)

x = 0
for y in range(height):
    if forest[y][x] == '#':
        result += 1
    x = (x + 3) % (width)

print(result)
