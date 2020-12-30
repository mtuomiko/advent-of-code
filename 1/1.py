expenses = []
target = 2020

with open('input.txt', 'r') as file:
    for line in file:
        expenses.append(int(line))

num = len(expenses)
for i in range(num):
    for j in range(i + 1, num):
        if expenses[i] + expenses[j] == target:
            print('Eureka!', expenses[i], expenses[j],
                  expenses[i] * expenses[j])
