# TODO: General solution for n dimensions?
# TODO: Include only relevant section of space.

FILENAME = 'input.txt'
INIT_CYCLES = 6


class Space:
    def __init__(self, input, cycles):
        self.input = input
        self.cycles = cycles
        self.space = self.init_space()
        self.offsets = self.create_offsets()
        self.simulate()

    def init_space(self):
        height = len(self.input)
        width = len(self.input[0])

        # Space can expand at most by one in both directions every cycle. Created
        # 3D space will fit any possible combinations. However, created space is
        # too large, especially in the earlier cycles, which will result in slower
        # calculations as pointless cubes are processed.
        self.z_max = 1 + 2 * INIT_CYCLES
        self.y_max = height + 2 * INIT_CYCLES
        self.x_max = width + 2 * INIT_CYCLES
        space = self.create_empty_space()

        offset = self.cycles

        for y in range(height):
            for x in range(width):
                space[offset][y + offset][x + offset] = input[y][x]

        return space

    def create_empty_space(self):
        space = [
            [['.'] * self.x_max for _y in range(self.y_max)] for _z in range(self.z_max)]
        return space

    def create_offsets(self):
        values = [-1, 0, 1]
        offsets = []
        for z_v in values:
            for y_v in values:
                for x_v in values:
                    offsets.append((z_v, y_v, x_v))
        offsets.remove((0, 0, 0))
        return offsets

    def count_active_neighbors(self, z, y, x):
        count = 0
        for o_z, o_y, o_x in self.offsets:
            new_coords = (z + o_z, y + o_y, x + o_x)

            # Check limits
            limit_broken = False
            for index, coord_letter in enumerate(['z', 'y', 'x']):
                coord = new_coords[index]
                if coord < 0 or coord >= getattr(self, coord_letter + '_max'):
                    limit_broken = True
                    continue

            if limit_broken:
                continue

            # Safe to access, check for active cubes
            if self.space[new_coords[0]][new_coords[1]][new_coords[2]] == '#':
                count += 1

        return count

    def simulate(self):
        for _cycle in range(self.cycles):
            self.process_cycle()

    def process_cycle(self):
        new_space = self.create_empty_space()
        active = 0
        for z in range(self.z_max):
            for y in range(self.y_max):
                for x in range(self.x_max):
                    count = self.count_active_neighbors(z, y, x)
                    if self.space[z][y][x] == '#' and count in {2, 3}:
                        new_space[z][y][x] = '#'
                        active += 1
                    elif self.space[z][y][x] == '.' and count == 3:
                        new_space[z][y][x] = '#'
                        active += 1

        self.space = new_space
        self.active = active


class HyperSpace:
    def __init__(self, input, cycles):
        self.input = input
        self.cycles = cycles
        self.space = self.init_space()
        self.offsets = self.create_offsets()
        self.simulate()

    def init_space(self):
        height = len(self.input)
        width = len(self.input[0])

        # Space can expand at most by one in both directions every cycle. Created
        # 4D space will fit any possible combinations. However, created space is
        # too large, especially in the earlier cycles, which will result in slower
        # calculations as pointless cubes are processed.
        self.w_max = 1 + 2 * INIT_CYCLES
        self.z_max = 1 + 2 * INIT_CYCLES
        self.y_max = height + 2 * INIT_CYCLES
        self.x_max = width + 2 * INIT_CYCLES
        space = self.create_empty_space()

        offset = self.cycles

        for y in range(height):
            for x in range(width):
                space[offset][offset][y + offset][x + offset] = input[y][x]

        return space

    def create_empty_space(self):
        space = [[[['.'] * self.x_max for _y in range(self.y_max)]
                  for _z in range(self.z_max)] for _w in range(self.w_max)]
        return space

    def create_offsets(self):
        values = [-1, 0, 1]
        offsets = []
        for w_v in values:
            for z_v in values:
                for y_v in values:
                    for x_v in values:
                        offsets.append((w_v, z_v, y_v, x_v))
        offsets.remove((0, 0, 0, 0))
        return offsets

    def count_active_neighbors(self, w, z, y, x):
        count = 0
        for o_w, o_z, o_y, o_x in self.offsets:
            new_coords = (w + o_w, z + o_z, y + o_y, x + o_x)

            # Check limits
            limit_broken = False
            for index, coord_letter in enumerate(['w', 'z', 'y', 'x']):
                coord = new_coords[index]
                if coord < 0 or coord >= getattr(self, coord_letter + '_max'):
                    limit_broken = True
                    continue

            if limit_broken:
                continue

            # Safe to access, check for active cubes
            if self.space[new_coords[0]][new_coords[1]][new_coords[2]][new_coords[3]] == '#':
                count += 1

        return count

    def simulate(self):
        for _cycle in range(self.cycles):
            self.process_cycle()

    def process_cycle(self):
        new_space = self.create_empty_space()
        active = 0
        for w in range(self.w_max):
            for z in range(self.z_max):
                for y in range(self.y_max):
                    for x in range(self.x_max):
                        count = self.count_active_neighbors(w, z, y, x)
                        if self.space[w][z][y][x] == '#' and count in {2, 3}:
                            new_space[w][z][y][x] = '#'
                            active += 1
                        elif self.space[w][z][y][x] == '.' and count == 3:
                            new_space[w][z][y][x] = '#'
                            active += 1

        self.space = new_space
        self.active = active


def read_input(filename):
    input = []
    with open(filename, 'r') as file:
        for line in file:
            input.append(list(line.rstrip('\n')))
    return input


def part_1(input):
    spess = Space(input, INIT_CYCLES)
    print(spess.active)


def part_2(input):
    hyper_spess = HyperSpace(input, INIT_CYCLES)
    print(hyper_spess.active)


if __name__ == "__main__":
    input = read_input(FILENAME)
    part_1(input)
    part_2(input)
