def count(answers, size):
    count = 0
    for key in answers:
        if answers[key] == size:
            count += 1
    return count


result = 0

with open('input.txt', 'r') as file:
    current = {}
    size = 0
    for line in file:
        if line == '\n' and len(current):
            result += count(current, size)
            current = {}
            size = 0
        else:
            size += 1
            for c in line.rstrip('\n'):
                if c not in current:
                    current[c] = 1
                else:
                    current[c] += 1
    # Process last entry
    if len(current):
        result += count(current, size)

print(result)
