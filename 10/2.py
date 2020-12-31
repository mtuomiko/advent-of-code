jolts = [0]

with open('input.txt', 'r') as file:
    for line in file:
        jolts.append(int(line))

jolts.sort()
jolts.append(jolts[len(jolts) - 1] + 3)
memory = [None for _i in range(len(jolts))]


def count(jolts, i):
    if i == len(jolts) - 1:
        return 1
    if memory[i]:
        return memory[i]

    sum = 0
    upper = min(i + 3, len(jolts) - 1)

    for j in range(i + 1, upper + 1):
        diff = jolts[j] - jolts[i]
        if diff <= 3:
            sum += count(jolts, j)

    memory[i] = sum
    return sum


result = count(jolts, 0)

print(result)
