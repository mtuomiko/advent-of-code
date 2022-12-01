from math import inf

MAGIC_WORD = 'departure'


def is_in_some_range(ranges, num):
    if not ranges:
        return False
    minimum = ranges[0][0]
    maximum = ranges[-1][1]
    if num < minimum or num > maximum:
        return False
    for range in ranges:
        if num >= range[0] and num <= range[1]:
            return True
    return False


def parse_ticket(string):
    elems = string.split(',')
    ticket = [int(elem) for elem in elems]
    return ticket


fields = {}
start = inf
end = 0
nearby_tickets = []

with open('input.txt', 'r') as file:
    input = file.readlines()

line_iter = 0
# Read field class ranges
while True:
    line = input[line_iter]
    if not line or line == '\n':
        break
    elems = line.rstrip('\n').split()
    name = line.rstrip('\n').split(':')[0]
    ranges = [elems[-3], elems[-1]]
    for input_range in ranges:
        numbers = input_range.split('-')
        minimum = int(numbers[0])
        maximum = int(numbers[1])
        if minimum < start:
            start = minimum
        if maximum > end:
            end = maximum
        range_tuple = (minimum, maximum)
        if name not in fields:
            fields[name] = [range_tuple]
        else:
            fields[name].append(range_tuple)
    line_iter += 1

# Read my ticket
line_iter += 1
while True:
    line = input[line_iter]
    if line == 'your ticket:\n':
        my_ticket_str = input[line_iter + 1].rstrip('\n')
        my_ticket = parse_ticket(my_ticket_str)
    if not line or line == 'nearby tickets:\n':
        break
    line_iter += 1

# Read nearby tickets
line_iter += 1
while line_iter < len(input):
    line = input[line_iter]
    ticket = parse_ticket(line.rstrip('\n'))
    nearby_tickets.append(ticket)
    line_iter += 1


# Form list for each possible value within start and end of the whole range
endpoints = [[] for _i in range(end - start + 1)]

# Add to list the points where some valid range begins or ends
for field in fields.values():
    for minimum, maximum in field:
        endpoints[minimum - start].append('S')
        endpoints[maximum - start].append('E')

combined_ranges = []
active_count = 0  # Number of active valid ranges
current_start = None
current_end = None

# Iterate whole range and keep count of active valid ranges
for i, i_endpoints in enumerate(endpoints):
    prev = active_count
    for endpoint in i_endpoints:
        if endpoint == 'S':
            active_count += 1
        elif endpoint == 'E':
            active_count -= 1

    # Some combined range starts here because there is no earlier start and
    # previous iteration didn't belong to any range and current one does.
    if not prev and active_count and current_start is None:
        current_start = i + start

    if not prev and not active_count and current_start is not None:
        combined_ranges.append((current_start, i + start - 1))
        current_start = None

# Process last range which was valid until the end of whole range
if current_start is not None:
    combined_ranges.append((current_start, end))


faulty = []
valid_tickets = []

for ticket in nearby_tickets:
    valid = True
    for num in ticket:
        if not is_in_some_range(combined_ranges, num):
            valid = False
    if valid:
        valid_tickets.append(ticket)


n_tickets = len(valid_tickets)
n_fields = len(valid_tickets[0])

possible_fields = [set(fields.keys()) for _i in range(n_fields)]


def remove_from_others(fields, i):
    name = next(iter(fields[i]))
    for index, field_set in enumerate(fields):
        if index != i:
            field_set.discard(name)


remaining = set(fields.keys())

while remaining:
    for i in range(n_fields):
        for j in range(n_tickets):
            num = valid_tickets[j][i]
            for name, ranges in fields.items():
                if not is_in_some_range(ranges, num):
                    possible_fields[i].discard(name)
                if len(possible_fields[i]) == 1:
                    remove_from_others(possible_fields, i)
                    remaining.discard(next(iter(possible_fields[i])))

# Convert the list of single string element sets into list of strings
possible_fields = [next(iter(value)) for value in possible_fields]

# print(possible_fields)
# print(my_ticket)

product = 1
for index, field_name in enumerate(possible_fields):
    if field_name.startswith(MAGIC_WORD):
        product *= my_ticket[index]

print(product)
