from collections import deque

magic_bag = 'shiny gold'
bags = {}

with open('input.txt', 'r') as file:
    for line in file:
        words = line.split()
        target = f'{words[0]} {words[1]}'

        # Create graph
        for i in range(4, len(words), 4):
            if words[i] == 'no':
                break
            origin = f'{words[i + 1]} {words[i + 2]}'
            bag = bags.get(origin)
            if bag == None:
                bags[origin] = {target}
            else:
                bag.add(target)

visited = {}
deck = deque()
deck.append(magic_bag)
visited[magic_bag] = True
result = 0

# DFS
while deck:
    bag = deck.popleft()
    connections = bags.get(bag)
    if connections:
        for connection in connections:
            if visited.get(connection):
                continue
            deck.append(connection)
            visited[connection] = True
            result += 1

print(result)
