from math import inf


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


valid_ranges = []
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
    ranges = [elems[-3], elems[-1]]
    for input_range in ranges:
        numbers = input_range.split('-')
        minimum = int(numbers[0])
        maximum = int(numbers[1])
        if minimum < start:
            start = minimum
        if maximum > end:
            end = maximum
        valid_ranges.append((minimum, maximum))
    line_iter += 1

# Read fluff
line_iter += 1
while True:
    line = input[line_iter]
    if not line or line == 'nearby tickets:\n':
        break
    line_iter += 1

# Read nearby tickets
line_iter += 1
while line_iter < len(input):
    line = input[line_iter]
    elems = line.rstrip('\n').split(',')
    nums = [int(elem) for elem in elems]
    nearby_tickets.append(nums)
    line_iter += 1


# Form list for each possible value within start and end of the whole range
endpoints = [[] for _i in range(end - start + 1)]

# Add to list the points where some valid range begins or ends
for minimum, maximum in valid_ranges:
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

for ticket in nearby_tickets:
    for num in ticket:
        if not is_in_some_range(combined_ranges, num):
            faulty.append(num)

print(combined_ranges)
print(faulty)
print(sum(faulty))
