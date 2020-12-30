instructions = []

with open('input.txt', 'r') as file:
    for line in file:
        words = line.split()
        instr = (words[0], int(words[1]))
        instructions.append(instr)


def process_instruction(index, instructions, ran, acc, fix_used):
    if index == len(instructions):
        return acc
    elif ran[index]:
        return None
    else:
        ran[index] = True

    instruction = instructions[index]
    command = instruction[0]
    value = instruction[1]
    next = index + 1

    if command == 'acc':
        acc += value
    elif command == 'jmp':
        next = index + value
    branch_1 = process_instruction(next, instructions, ran, acc, fix_used)
    branch_2 = None

    if not fix_used and (command == 'jmp' or command == 'nop'):
        fix_used = True
        if command == 'jmp':
            next = index + 1
        elif command == 'nop':
            next = index + value

        branch_2 = process_instruction(
            next, instructions, ran.copy(), acc, fix_used)

    return branch_1 if branch_2 == None else branch_2


ran = [False for _i in range(len(instructions))]
result = process_instruction(0, instructions, ran, 0, False)
print(result)
