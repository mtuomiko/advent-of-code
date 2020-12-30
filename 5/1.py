def seat_row(text):
    temp = text.replace('F', '0')
    binary = temp.replace('B', '1')
    row = int(binary, 2)
    return row


def seat_col(text):
    temp = text.replace('L', '0')
    binary = temp.replace('R', '1')
    col = int(binary, 2)
    return col


def seat_id(text):
    row = seat_row(text[:7])
    col = seat_col(text[-3:])
    id = row * 8 + col
    return id


max = 0

with open('input.txt', 'r') as file:
    for line in file:
        id = seat_id(line.strip('\n'))
        if id > max:
            max = id

print(max)
