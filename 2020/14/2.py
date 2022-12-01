BIT_SIZE = 36


def populate_address_space(mask, init_address, i, addresses, built_address):
    if i == len(mask):
        addresses.append(built_address)
        return
    mask_c = mask[i]
    if mask_c == '0':
        populate_address_space(mask, init_address, i + 1,
                               addresses, built_address + init_address[i])
    elif mask_c == '1':
        populate_address_space(mask, init_address, i + 1,
                               addresses, built_address + '1')
    elif mask_c == 'X':
        populate_address_space(mask, init_address, i + 1,
                               addresses, built_address + '0')
        populate_address_space(mask, init_address, i + 1,
                               addresses, built_address + '1')


def write_to_memory(memory, mask, init_address, value):
    addresses = []
    filled_address = bin(init_address)[2:].zfill(BIT_SIZE)
    populate_address_space(mask, filled_address, 0, addresses, '')
    for address in addresses:
        memory[address] = value


memory = {}

with open('input.txt', 'r') as file:
    for line in file:
        elems = line.rstrip('\n').split()
        if elems[0] == 'mask':
            mask = elems[2]
        else:
            mem_split = elems[0].split('[')
            init_address = int(mem_split[1][:-1])
            value = int(elems[2])
            write_to_memory(memory, mask, init_address, value)


total = 0
for value in memory.values():
    total += value

print(total)
