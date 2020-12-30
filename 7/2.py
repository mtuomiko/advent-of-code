magic_bag = 'shiny gold'
bags = {}

with open('input.txt', 'r') as file:
    for line in file:
        words = line.split()
        key = f'{words[0]} {words[1]}'
        current = set()

        # Create graph
        for i in range(4, len(words), 4):
            if words[i] == 'no':
                break
            num = int(words[i])
            target = f'{words[i + 1]} {words[i + 2]}'
            elem = (target, num)
            current.add(elem)

        bags[key] = current

mem = {}


def dfs(bag, bags, mem):
    precalc = mem.get(bag)
    if precalc:
        return precalc

    count = 1
    inner_bags = bags.get(bag)
    if inner_bags:
        for inner_bag in inner_bags:
            count += inner_bag[1] * dfs(inner_bag[0], bags, mem)
        return count
    else:
        return 1


result = dfs(magic_bag, bags, mem) - 1

print(result)
