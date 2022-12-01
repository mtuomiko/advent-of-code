from string import digits

FILENAME = 'input.txt'


def op_mul(var1, var2):
    return var1 * var2


def op_add(var1, var2):
    return var1 + var2


ops = {
    '+': op_add,
    '*': op_mul,
}


def calc_rpn(tokens):
    result_stack = []
    for token in tokens:
        if isinstance(token, int):
            result_stack.append(token)
        else:
            var1, var2 = result_stack.pop(), result_stack.pop()
            result_stack.append(ops[token](var1, var2))
    return result_stack[0]


def evaluate(string, precedences):
    # Create reverse polish notation with shunting-yard algorithm
    op_stack = []
    rpn = []

    for c in string:
        if c == ' ':
            continue
        if c in digits:
            rpn.append(int(c))
        elif c == '(':
            op_stack.append(c)
        elif c == ')':
            top_op = op_stack.pop()
            while top_op != '(':
                rpn.append(top_op)
                top_op = op_stack.pop()
        else:  # c must be operator
            while op_stack:
                top_op = op_stack.pop()
                if top_op not in ops or precedences[c] > precedences[top_op]:
                    op_stack.append(top_op)
                    break
                rpn.append(top_op)

            op_stack.append(c)
    while op_stack:
        rpn.append(op_stack.pop())

    # Calculate result from reverse polish notation
    return calc_rpn(rpn)


def part_1(input):
    part_1_precedences = {
        '+': 1,
        '*': 1,
    }
    result = sum([evaluate(line, part_1_precedences) for line in input])
    print(result)


def part_2(input):
    part_2_precedences = {
        '+': 2,
        '*': 1,
    }
    result = sum([evaluate(line, part_2_precedences) for line in input])
    print(result)


def read_input(filename):
    input = []
    with open(filename, 'r') as file:
        for line in file:
            input.append(line.rstrip('\n'))
    return input


if __name__ == "__main__":
    input = read_input(FILENAME)
    part_1(input)
    part_2(input)
