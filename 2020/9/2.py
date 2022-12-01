from collections import deque

numbers = []
window_size = 25

with open('input.txt', 'r') as file:
    for line in file:
        numbers.append(int(line))


def add_count(valid_numbers, x):
    current = valid_numbers.get(x)
    if current == None:
        valid_numbers[x] = 1
    else:
        valid_numbers[x] += 1


def remove_count(valid_numbers, x):
    if valid_numbers[x] == 1:
        valid_numbers.pop(x)
    else:
        valid_numbers[x] -= 1


def popleft_number(window, valid_numbers):
    x = window[0]
    for i in range(1, len(window)):
        other = window[i]
        sum = x + other
        remove_count(valid_numbers, sum)
    window.popleft()


def add_number(window, valid_numbers, x):
    window.append(x)
    for i in range(0, len(window) - 1):
        other = window[i]
        sum = x + other
        add_count(valid_numbers, sum)


window = deque(numbers[:window_size])
size = len(window)
valid_numbers = {}

# Init valid numbers from preamble
for i in range(size):
    for j in range(i + 1, size):
        sum = window[i] + window[j]
        add_count(valid_numbers, sum)

# magic_num = None
for i in range(size, len(numbers)):
    num = numbers[i]
    if num not in valid_numbers:
        magic_num = num
        break
    popleft_number(window, valid_numbers)
    add_number(window, valid_numbers, num)

sums = [0 for _i in range(len(numbers))]
indexes = None

for i in range(len(numbers)):
    cur = numbers[i]
    for j in range(i + 1):
        sums[j] += cur
        if sums[j] == magic_num:
            indexes = (j, i)
            break
    if indexes:
        break

min = numbers[indexes[0]]
max = numbers[indexes[0]]

for i in range(indexes[0], indexes[1] + 1):
    cur = numbers[i]
    if cur < min:
        min = cur
    elif cur > max:
        max = cur

print(min + max)
