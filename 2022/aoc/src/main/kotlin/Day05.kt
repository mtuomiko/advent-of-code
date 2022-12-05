import java.io.File

private fun splitLinesOnFirstEmptyLine(lines: List<String>): Pair<List<String>, List<String>> {
    for ((index, line) in lines.withIndex()) {
        if (line.isEmpty()) return Pair(
            lines.take(index), // index = number of lines before empty line
            lines.drop(index + 1) // need to exclude the empty line also
        )
    }
    throw Exception("Separating empty line not found")
}

// ArrayDeque probably not even useful for stacks, we're not adding to the beginning at any point
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

private fun parseOperation(line: String): Operation {
    val integerValues = line.split(' ').slice(setOf(1, 3, 5)).map { it.toInt() }
    return Operation(integerValues[0], integerValues[1], integerValues[2])
}

private class Operation(val count: Int, val from: Int, val to: Int)

private fun <T> runOperations(lines: List<String>, stacks: List<ArrayDeque<T>>) {
    lines.forEach {
        val operation = parseOperation(it)
        with(operation) {
            repeat(count) {
                val box = stacks[from - 1].removeLast()
                stacks[to - 1].addLast(box)
            }
        }
    }
}

fun solver05a(inputFile: File) {
    val lines = inputFile.readLines() // I'm just gonna read the whole thing instead of any sequential approach
    val (stackLines, operationLines) = splitLinesOnFirstEmptyLine(lines)
    val stacks = parseStackLines(stackLines)

    runOperations(operationLines, stacks)

    stacks.forEach { print(it.last()) }
}

private fun <T> runOperationsB(lines: List<String>, stacks: List<ArrayDeque<T>>) {
    lines.forEach {
        val operation = parseOperation(it)
        with(operation) {
            val topStack = ArrayDeque<T>()
            repeat(count) {
                topStack.addFirst(stacks[from - 1].removeLast())
            }
            stacks[to - 1].addAll(topStack)
        }
    }
}

fun solver05b(inputFile: File) {
    val lines = inputFile.readLines() // I'm just gonna read the whole thing instead of any sequential approach
    val (stackLines, operationLines) = splitLinesOnFirstEmptyLine(lines)
    val stacks = parseStackLines(stackLines)

    runOperationsB(operationLines, stacks)

    stacks.forEach { print(it.last()) }
}
