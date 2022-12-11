package com.mtuomiko

import java.io.Reader

private fun splitLinesOnFirstEmptyLine(lines: List<String>): Pair<List<String>, List<String>> {
    for ((index, line) in lines.withIndex()) {
        if (line.isEmpty()) return Pair(
            lines.take(index), // index = number of lines before empty line
            lines.drop(index + 1) // need to exclude the empty line also
        )
    }
    throw Exception("Separating empty line not found")
}

private fun parseStackLines(lines: List<String>): List<ArrayDeque<Char>> {
    val stackCount = lines.last().trim().split(Regex(" +")).size // greedy matching to handle 'long' spaces
    val deques = List(stackCount) { ArrayDeque<Char>() }

    val charPositionIndexes = (1..lines.last().length step 4).toList() // see input, this will then make sense

    deques.forEachIndexed { horizontalIndex, deque ->
        for (verticalIndex in 0 until lines.size - 1) {
            val line = lines[verticalIndex]
            val indexWithinLine = charPositionIndexes[horizontalIndex]
            val char = line[indexWithinLine]
            if (char.isLetter()) deque.addFirst(char)
        }
    }

    return deques
}

private fun <T> parseOperation(line: String, stacks: List<ArrayDeque<T>>): MoveOperation<T> {
    val integerValues = line.split(' ').slice(setOf(1, 3, 5)).map { it.toInt() }
    return MoveOperation(
        count = integerValues[0],
        from = stacks[integerValues[1] - 1], // stack numbering in input uses 1-based indexes
        to = stacks[integerValues[2] - 1]
    )
}

private class MoveOperation<T>(val count: Int, val from: ArrayDeque<T>, val to: ArrayDeque<T>)

private fun <T> processOperationLines(
    lines: List<String>,
    stacks: List<ArrayDeque<T>>,
    block: (operation: MoveOperation<T>) -> Unit
) {
    lines.forEach {
        val operation = parseOperation(it, stacks)
        block(operation)
    }
}

private fun commonSolver(input: Reader, block: (operation: MoveOperation<Char>) -> Unit): String {
    val lines = input.readLines() // Just read the whole thing instead of any sequential approach
    val (stackLines, operationLines) = splitLinesOnFirstEmptyLine(lines)
    val stacks = parseStackLines(stackLines)

    processOperationLines(operationLines, stacks, block)

    return stacks.map { it.last() }.joinToString("")
}

fun solver05a(input: Reader): String {
    fun <T> sequentialMove(operation: MoveOperation<T>) {
        with(operation) {
            repeat(count) { to.addLast(from.removeLast()) }
        }
    }

    val result = commonSolver(input, ::sequentialMove)
    println(result)
    return result
}

fun solver05b(input: Reader): String {
    fun <T> stackMove(operation: MoveOperation<T>) {
        with(operation) {
            val topStack = ArrayDeque<T>()
            repeat(count) {
                topStack.addFirst(from.removeLast()) // addFirst so the order isn't reversed
            }
            to.addAll(topStack)
        }
    }

    val result = commonSolver(input, ::stackMove)
    println(result)
    return result
}
