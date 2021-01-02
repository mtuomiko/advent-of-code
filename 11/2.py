from copy import deepcopy

original_grid = []

with open('input.txt', 'r') as file:
    for line in file:
        original_grid.append(line.rstrip('\n'))

width = len(original_grid[0])
height = len(original_grid)


def count_occupied(y, x, grid):
    moves = [
        (-1, -1), (-1, 0), (-1, 1),
        (0, -1), (0, 1),
        (1, -1), (1, 0), (1, 1),
    ]
    count = 0
    for move in moves:
        new_y = y
        new_x = x
        while True:
            move_y, move_x = move
            new_y = new_y + move_y
            new_x = new_x + move_x

            if new_y < 0 or new_x < 0:
                break
            if new_y >= len(grid) or new_x >= len(grid[0]):
                break

            elem = grid[new_y][new_x]
            if elem == 'L':
                break
            if elem == '#':
                count += 1
                break

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
            elif elem == '#' and count >= 5:
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
