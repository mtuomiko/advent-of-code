jolts = []

with open('input.txt', 'r') as file:
    for line in file:
        jolts.append(int(line))

jolts.sort()
jolts.append(jolts[len(jolts) - 1] + 3)

count = [0] * 4

joltage = 0
for j in jolts:
    diff = j - joltage
    count[diff] += 1
    joltage = j

print(count[1] * count[3])