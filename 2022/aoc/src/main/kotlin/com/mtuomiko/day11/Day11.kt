package com.mtuomiko.day11

import java.io.Reader

const val ROUNDS = 20
const val OLD = "old"
const val MULTIPLY = "*"
const val ADD = "+"

class Item(var worryLevel: Long) {
    fun relief() {
        worryLevel /= 3
    }

    fun operation(op: (Long, Long) -> Long) {
        operation(worryLevel, op)
    }

    fun operation(amount: Long, op: (Long, Long) -> Long) {
        worryLevel = op(worryLevel, amount)
    }
}

class Monkey(
    private val items: MutableList<Item>,
    private val operation: (Item) -> Unit,
    val selectTargetIndex: (Item) -> Int
) {
    var inspectCount = 0

    fun processQueue(): Pair<Item, Int>? {
        val item = items.removeFirstOrNull() ?: return null

        inspectCount++
        operation.invoke(item)
        item.relief()
        return Pair(item, selectTargetIndex(item))
    }

    fun receive(item: Item) {
        items.add(item)
    }
}

class Game(val monkeys: List<Monkey>) {
    fun play() {
        repeat(ROUNDS) { round() }
    }

    private fun round() {
        monkeys.forEach {
            while (true) {
                val itemMovement = it.processQueue() ?: break
                val targetMonkey = monkeys.getOrNull(itemMovement.second) ?: throw Exception("Monkey not found")
                targetMonkey.receive(itemMovement.first)
            }
        }
    }

}

fun parseMonkey(textInput: List<String>): Monkey {
    val trimmedAndTokenized = textInput.map { it.trim().split(' ') }
    val items = parseItemsLine(trimmedAndTokenized[1])
    val operation = parseOperationLine(trimmedAndTokenized[2])
    val selectTargetIndex = parseTestLines(trimmedAndTokenized.subList(3, 6))

    return Monkey(items.toMutableList(), operation, selectTargetIndex)
}

fun parseItemsLine(itemsLine: List<String>): List<Item> {
    val itemNumbers = itemsLine.subList(2, itemsLine.size)
    return itemNumbers.map {
        val worryLevel = it.trim(',').toLong()
        Item(worryLevel)
    }
}

fun parseOperationLine(line: List<String>): (Item) -> Unit {
    val operationId = line[4]
    val argument = line[5]

    val operator: (Long, Long) -> Long = when (operationId) {
        MULTIPLY -> Long::times
        ADD -> Long::plus
        else -> throw Exception("Unknown operation")
    }

    return if (argument == OLD) { item ->
        item.operation(operator)
    } else { item ->
        item.operation(argument.toLong(), operator)
    }
}

fun parseTestLines(lines: List<List<String>>): (Item) -> Int {
    val divisor = lines[0][3].toInt()
    val trueIndex = lines[1][5].toInt()
    val falseIndex = lines[2][5].toInt()

    return { item ->
        val divisible = (item.worryLevel % divisor) == 0L
        if (divisible) trueIndex else falseIndex
    }
}

fun solver11a(input: Reader): Long {
    val monkeySequence = input.buffered().lineSequence().chunked(7)
    val monkeys = monkeySequence.map(::parseMonkey).toList()
    val game = Game(monkeys)

    game.play()

    val result = game.monkeys.map { it.inspectCount }.sorted().takeLast(2)
        .fold(1L) { product, value -> product * value }

    println("Amount of monkey business is $result")
    return result
}

fun solver11b(input: Reader): Long {
    return 1
}
