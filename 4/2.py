import re


def parse_line(current, line):
    elems = line.split()
    for e in elems:
        parts = e.split(':')
        if len(parts) == 2:
            current[parts[0]] = parts[1]


def validate_year(passport, field, min, max):
    year = passport.get(field)
    if year == None:
        return False

    int_year = int(year)
    if int_year >= min and int_year <= max:
        return True
    return False


def validate_height(passport):
    height = passport.get('hgt')
    if height == None or len(height) < 4:
        return False

    unit = height[-2:]
    num = height[:-2]
    int_num = int(num)

    if unit == 'cm' and int_num >= 150 and int_num <= 193:
        return True
    elif unit == 'in' and int_num >= 59 and int_num <= 76:
        return True
    return False


def validate_hair_color(passport):
    hair_color = passport.get('hcl')
    if hair_color == None:
        return False

    p = re.compile('^#[0-9a-f]{6}$')
    if p.match(hair_color):
        return True
    return False


def validate_eye_color(passport):
    eye_color = passport.get('ecl')
    if eye_color == None:
        return False

    p = re.compile('^(amb|blu|brn|gry|grn|hzl|oth)$')
    if p.match(eye_color):
        return True
    return False


def validate_passport_id(passport):
    pid = passport.get('pid')
    if pid == None:
        return False

    p = re.compile('^\\d{9}$')
    if p.match(pid):
        return True
    return False


passports = []
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
    if (
        validate_year(passport, 'byr', 1920, 2002) and
        validate_year(passport, 'iyr', 2010, 2020) and
        validate_year(passport, 'eyr', 2020, 2030) and
        validate_height(passport) and
        validate_hair_color(passport) and
        validate_eye_color(passport) and
        validate_passport_id(passport)
    ):
        result += 1

print(result)
