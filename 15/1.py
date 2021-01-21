with open('input.txt', 'r') as file:
    numbers = file.readline().rstrip('\n').split(',')

numbers = [int(number) for number in numbers]
data = {}
time = 1

for number in numbers:
    data[number] = (time, -1)
    prev = number
    time += 1

while time <= 2020:  # Or 30000000 for part two of this day
    prev_data = data[prev]

    if prev_data[1] == -1:
        new_number = 0
    else:
        new_number = prev_data[0] - prev_data[1]

    new_data = data.get(new_number)
    if not new_data:
        data[new_number] = (time, -1)
    else:
        data[new_number] = (time, new_data[0])

    prev = new_number
    time += 1

print(prev)
