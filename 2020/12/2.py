class Ship:
    def __init__(self, y, x, waypoint_y, waypoint_x):
        self.y = y
        self.x = x
        self.waypoint_y = waypoint_y
        self.waypoint_x = waypoint_x

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
            self.move_waypoint(self.directions[0], num)
        elif action == 'E':
            self.move_waypoint(self.directions[1], num)
        elif action == 'S':
            self.move_waypoint(self.directions[2], num)
        elif action == 'W':
            self.move_waypoint(self.directions[3], num)
        elif action == 'L':
            self.rotate_waypoint(-num)
        elif action == 'R':
            self.rotate_waypoint(num)
        elif action == 'F':
            self.forward(num)

    def move_waypoint(self, direction, distance):
        self.waypoint_y += direction[0] * distance
        self.waypoint_x += direction[1] * distance

    def forward(self, distance):
        self.y += self.waypoint_y * distance
        self.x += self.waypoint_x * distance

    def rotate_waypoint(self, degrees):
        absolute = degrees % 360
        if absolute == 90 or absolute == 270:
            self.waypoint_y, self.waypoint_x = self.waypoint_x, self.waypoint_y
        if absolute == 90:
            self.waypoint_y = -self.waypoint_y
        elif absolute == 180:
            self.waypoint_y = -self.waypoint_y
            self.waypoint_x = -self.waypoint_x
        elif absolute == 270:
            self.waypoint_x = -self.waypoint_x

    def get_origin_distance(self):
        return abs(ship.y) + abs(ship.x)


ship = Ship(0, 0, 1, 10)

with open('input.txt') as file:
    for line in file:
        ship.do_action(line.rstrip('\n'))

print(ship.get_origin_distance())
