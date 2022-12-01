import sys

# Magical maximum useful message length, calculated from input message max length
max_message_length = 96


def split_on_emptyline(input):
    '''
    Split input string array to a tuple of two string arrays on first occurence of an empty line. Frist empty line 
    omitted from result.
    '''
    write_second_array = False
    first_array = []
    second_array = []
    for line in input:
        if line == '':  # two part input separated by an empty line
            write_second_array = True
        elif write_second_array:
            second_array.append(line)
        else:
            first_array.append(line)
    return first_array, second_array


# def form_words(sections, index, builder, results):
#     # End of recursion, add the result
#     if index >= len(sections):
#         results.add(builder)
#         return
#     for alt in sections[index]:
#         form_words(sections, index + 1, builder + alt, results)


def search(rules, index, memory):
    '''
    Returns a set of all possible string values allowed by rule indicated by index. Memory used as a cache to prevent 
    parsing same rules multiple times.
    '''

    # If the wanted rule has already been found, return the set stored in memory
    if index in memory:
        return memory[index]

    root_rule = rules[index]
    # If rule contains only letters, it doesn't need parsing and can be returned
    if root_rule.isalpha():
        return {root_rule}

    elems = root_rule.split()
    sub_rules = []
    result = set()

    # Find possible | symbol and then add all the other elements as two sub-rules
    for i, elem in enumerate(elems):
        if elem == '|':
            sub_rules.extend([elems[:i], elems[i + 1:]])
            break
    # If no | symbol present, then add everything as a single rule
    if not sub_rules:
        sub_rules.append(elems)

    # for sub_rule in sub_rules:
    #     part_lists = []
    #     for part in sub_rule:
    #         part_lists.append(search(rules, int(part), memory))
    #     words = set()
    #     form_words(part_lists, 0, '', words)
    #     result.update(words)

    for sub_rule in sub_rules:
        combination_builder = set()
        # for rule_key in sub_rule:

        part_lists = []
        for part in sub_rule:
            part_lists.append(search(rules, int(part), memory))
        words = set()
        form_words(part_lists, 0, '', words)
        result.update(words)

    memory[index] = result
    return result


def map_raw_rules(raw_rules):
    '''
    Parse rules to a dict with integer keys pointing to a string with actual rule content.
    Example input line: 9: 20 2 | 39 74
    Line result: key 9 (int), value 20 2 | 39 74

    Possible leading or trailing spaces/double quotes are stripped from the rule.
    Example input line: 39: "a"
    Line result: key 39 (int), value a 
    '''
    rules = {}
    for rule in raw_rules:
        elems = rule.split(':')
        rules[int(elems[0])] = elems[1].strip(' "')

    return rules


def process(input):
    raw_rules, messages = split_on_emptyline(input)
    rules = map_raw_rules(raw_rules)

    memory = {}
    result = search(rules, 0, memory)

    count = 0
    for message in messages:
        if message in result:
            count += 1
    return count


def read_input(filename):
    input = []
    with open(filename, 'r') as file:
        input = [line.rstrip('\n') for line in file]
    return input


if __name__ == "__main__":
    if len(sys.argv) == 2:
        input = read_input(sys.argv[1])
        # print(input)
        print(process(input))
        # part_2(input)
    else:
        print("give input file as param")
