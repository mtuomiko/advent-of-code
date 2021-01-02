class Ship:
    def __init__(self):
        self.heading = 90
        self.y = 0
        self.x = 0
        self.directions = [
            (1, 0),  # North, positive y-axis
            (0, 1),  # East, positive x-axis
            (-1, 0),  # South, negative y-axis
            (0, -1),  # West, negative x-axis
        ]

    def do_action(self, s):
        action = s[0]
        num = int(s[1:])
        if action == 'N':
            self.move_direction(self.directions[0], num)
        elif action == 'E':
            self.move_direction(self.directions[1], num)
        elif action == 'S':
            self.move_direction(self.directions[2], num)
        elif action == 'W':
            self.move_direction(self.directions[3], num)
        elif action == 'L':
            self.turn_ship(-num)
        elif action == 'R':
            self.turn_ship(num)
        elif action == 'F':
            self.forward(num)

    def move_direction(self, direction, distance):
        self.y += direction[0] * distance
        self.x += direction[1] * distance

    def forward(self, distance):
        index = self.heading // 90
        direction = self.directions[index]
        self.move_direction(direction, distance)

    def turn_ship(self, degrees):
        self.heading = (self.heading + degrees) % 360

    def get_origin_distance(self):
        return abs(ship.y) + abs(ship.x)


ship = Ship()

with open('input.txt') as file:
    for line in file:
        ship.do_action(line.rstrip('\n'))

print(ship.get_origin_distance())
