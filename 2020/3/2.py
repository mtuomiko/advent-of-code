def slope_trees(forest, right, down):
    x = 0
    count = 0
    width = len(forest[0])
    height = len(forest)

    for y in range(0, height, down):
        if forest[y][x] == '#':
            count += 1
        x = (x + right) % (width)

    return count


forest = []
result = 1

with open('input.txt', 'r') as file:
    for line in file:
        forest.append(line.rstrip('\n\r'))

slopes = [(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)]

for slope in slopes:
    count = slope_trees(forest, slope[0], slope[1])
    result *= count

print(result)
