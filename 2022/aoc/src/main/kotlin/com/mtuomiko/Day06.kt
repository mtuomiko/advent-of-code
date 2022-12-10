package com.mtuomiko

import java.io.File

// No polling so it's an interesting 'buffer' :P
private class SpecializedUniqueIntBuffer(size: Int) {
    private val store = IntArray(size)
    private var pointer = 0

    fun add(value: Int) {
        store[pointer] = value
        pointer = (pointer + 1) % store.size
    }

    fun nonZeroSize() = store.fold(0) { count, element -> if (element == 0) count else count + 1 }

    fun fullAndAllUnique(): Boolean {
        val nonZeroCount = store.filter { it != 0 }.distinct().size
        return nonZeroCount == store.size
    }
}

private fun commonSolver(inputFile: File, size: Int): Int {
    val buffer = SpecializedUniqueIntBuffer(size)
    val reader = inputFile.bufferedReader()

    var index = 0
    while (true) {
        val value = reader.read()
        if (value == -1) {
            index = -1
            break
        }

        buffer.add(value)
        if (buffer.fullAndAllUnique()) break // needlessly checking "fullness" most of the time but whatevs
        index++
    }

    if (index == -1) {
        throw Exception("No unique marker of size $size encountered")
    }
    val result = index + 1
    println("First unique marker of size $size found at character $result")
    return result
}

fun solver06a(inputFile: File): Int {
    return commonSolver(inputFile, 4)
}

fun solver06b(inputFile: File): Int {
    return commonSolver(inputFile, 14)
}
