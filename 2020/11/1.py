from copy import deepcopy

original_grid = []

with open('input.txt', 'r') as file:
    for line in file:
        original_grid.append(line.rstrip('\n'))

width = len(original_grid[0])
height = len(original_grid)


def count_occupied(y, x, grid):
    adjacent = [
        (-1, -1), (-1, 0), (-1, 1),
        (0, -1), (0, 1),
        (1, -1), (1, 0), (1, 1),
    ]
    count = 0
    for move in adjacent:
        y1, x1 = move
        y2 = y + y1
        x2 = x + x1
        if y2 < 0 or x2 < 0:
            continue
        if y2 >= len(grid) or x2 >= len(grid[0]):
            continue
        if grid[y2][x2] == '#':
            count += 1
    return count


changes = True
prev_grid = original_grid

while changes:
    changes = False
    new_grid = [[None for _x in range(width)] for _y in range(height)]

    for y in range(height):
        for x in range(width):
            elem = prev_grid[y][x]
            if elem == '.':
                new_grid[y][x] = '.'
                continue
            count = count_occupied(y, x, prev_grid)
            if elem == 'L' and count == 0:
                new_grid[y][x] = '#'
                changes = True
            elif elem == '#' and count >= 4:
                new_grid[y][x] = 'L'
                changes = True
            else:
                new_grid[y][x] = elem

    prev_grid = new_grid

count = 0

for y in range(height):
    for x in range(width):
        if prev_grid[y][x] == '#':
            count += 1

print(count)
