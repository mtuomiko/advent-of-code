result = 0

with open('input.txt', 'r') as file:
    current = set()
    for line in file:
        if line == '\n' and len(current):
            result += len(current)
            current = set()
        else:
            current = current | set(line.rstrip('\n'))
    if len(current):
        result += len(current)

print(result)
