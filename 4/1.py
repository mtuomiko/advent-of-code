def parse_line(current, line):
    elems = line.split()
    for e in elems:
        parts = e.split(':')
        if len(parts) == 2:
            current[parts[0]] = parts[1]


passports = []
required = ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid']
result = 0

with open('input.txt', 'r') as file:
    current = {}
    for line in file:
        if line == '\n' and len(current):
            passports.append(current)
            current = {}
        else:
            parse_line(current, line)
    if len(current):
        passports.append(current)

for passport in passports:
    valid = True
    for field in required:
        if field not in passport:
            valid = False
            break
    if valid:
        result += 1

print(result)
