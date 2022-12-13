package com.mtuomiko.day11

import java.io.Reader

const val OLD = "old"
const val MULTIPLY = "*"
const val ADD = "+"

class Item(var worryLevel: Long) {
    fun operation(op: (Long, Long) -> Long) {
        operation(worryLevel, op)
    }

    fun operation(amount: Long, op: (Long, Long) -> Long) {
        worryLevel = op(worryLevel, amount)
    }
}

private fun Item.perform(operation: (Item) -> Unit) {
    operation.invoke(this)
}

class Monkey(
    val items: MutableList<Item>,
    val operation: (Item) -> Unit,
    val divisor: Int,
    private val trueMonkeyIndex: Int,
    private val falseMonkeyIndex: Int,
) {
    var inspectCount = 0

    fun getFirstItem(): Item? {
        val item = items.removeFirstOrNull() ?: return null
        inspectCount++
        return item
    }

    fun selectTargetIndex(item: Item): Int {
        val divisible = (item.worryLevel % divisor) == 0L
        return if (divisible) trueMonkeyIndex else falseMonkeyIndex
    }
}

/**
 * Steel nerves cause worry levels to divide by three! Least common multiple gives a safe upper bound for worry levels.
 * Not having a boundary could cause overflow (relevant for part B). Conditional checks still work the same even though
 * the worry levels wrap over after reaching the boundary (modulo leastCommonMultiple).
 */
class Game(val monkeys: List<Monkey>, steelNerves: Boolean) {
    private val leastCommonMultiple = leastCommonMultiple(monkeys.map { it.divisor.toLong() })
    private val reliefOperation = { item: Item ->
        val worryLevelToModulo = if (steelNerves) item.worryLevel / 3 else item.worryLevel
        item.worryLevel = worryLevelToModulo % leastCommonMultiple
    }

    fun play(rounds: Int) {
        repeat(rounds) { round() }
    }

    private fun round() {
        monkeys.forEach { monkey ->
            while (true) {
                val item = monkey.getFirstItem() ?: break

                item.perform(monkey.operation)
                item.perform(reliefOperation)
                val targetMonkeyIndex = monkey.selectTargetIndex(item)
                val targetMonkey = monkeys.getOrNull(targetMonkeyIndex) ?: throw Exception("Monkey not found")
                targetMonkey.items.add(item)
            }
        }
    }
}

private fun euclideanGCD(a: Long, b: Long): Long {
    return if (b == 0L) a else euclideanGCD(b, a % b)
}

/**
 * Doesn't even help us since default input for 8 monkeys gives the least common multiple of 9699690 which you can get
 * by just multiplying all the divisors.
 */
private fun leastCommonMultiple(numbers: List<Long>) = numbers.fold(1L) { a, b -> a * (b / euclideanGCD(a, b)) }

fun parseMonkey(textInput: List<String>): Monkey {
    val tokenizedLines = textInput.map { it.trim().split(' ') }
    val items = parseItemsLine(tokenizedLines[1])
    val operation = parseOperationLine(tokenizedLines[2])
    val divisor = tokenizedLines[3][3].toInt()
    val trueIndex = tokenizedLines[4][5].toInt()
    val falseIndex = tokenizedLines[5][5].toInt()

    return Monkey(items.toMutableList(), operation, divisor, trueIndex, falseIndex)
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

fun solver11a(input: Reader): Long {
    val rounds = 20
    val monkeySequence = input.buffered().lineSequence().chunked(7)
    val monkeys = monkeySequence.map(::parseMonkey).toList()
    val game = Game(monkeys, true)

    game.play(rounds)

    val result = game.monkeys.map { it.inspectCount }.sorted().takeLast(2)
        .fold(1L) { product, value -> product * value }

    println("Amount of monkey business after $rounds rounds using steel nerves is $result")
    return result
}

fun solver11b(input: Reader): Long {
    val rounds = 10000
    val monkeySequence = input.buffered().lineSequence().chunked(7)
    val monkeys = monkeySequence.map(::parseMonkey).toList()
    val game = Game(monkeys, false)

    game.play(rounds)

    val result = game.monkeys.map { it.inspectCount }.sorted().takeLast(2)
        .fold(1L) { product, value -> product * value }

    println("Amount of monkey business after $rounds rounds without steel nerves is $result")
    return result
}
