# Maintain two bitmasks based on input mask: clear_mask for setting bits to
# zero and set_mask for setting bits to one.
def create_masks(mask_string):
    clear_mask = ~0b0
    set_mask = 0b0
    length = len(mask_string)
    for i in range(len(mask_string)):
        c = mask_string[i]
        if c == '1':
            set_mask = set_bit(set_mask, length - 1 - i)
        elif c == '0':
            clear_mask = clear_bit(clear_mask, length - 1 - i)

    return (clear_mask, set_mask)


def set_bit(value, n):
    return value | (1 << n)


def clear_bit(value, n):
    return value & ~(1 << n)


memory = {}
clear_mask = ~0b0  # Used with binary AND to set values to 0
set_mask = 0b0  # Used with binary OR to set values to 1

with open('input.txt', 'r') as file:
    for line in file:
        elems = line.rstrip('\n').split()
        if elems[0] == 'mask':
            clear_mask, set_mask = create_masks(elems[2])
        else:
            mem_split = elems[0].split('[')
            address = int(mem_split[1][:-1])
            value = int(elems[2])

            value &= clear_mask
            value |= set_mask
            memory[address] = value

total = 0
for value in memory.values():
    total += value

print(total)
