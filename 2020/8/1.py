instructions = []

with open('input.txt', 'r') as file:
    for line in file:
        words = line.split()
        instr = (words[0], int(words[1]))
        instructions.append(instr)


def process_instruction(index, instructions, ran, acc):
    if ran[index]:
        return acc
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
    return process_instruction(next, instructions, ran, acc)


ran = [False for _i in range(len(instructions))]
result = process_instruction(0, instructions, ran, 0)
print(result)
