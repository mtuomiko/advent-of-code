package com.mtuomiko.day09

import java.io.File
import kotlin.math.sign

enum class Direction(val yDelta: Int, val xDelta: Int, val letter: Char) {
    UP(-1, 0, 'U'),
    DOWN(1, 0, 'D'),
    RIGHT(0, 1, 'R'),
    LEFT(0, -1, 'L')
}

data class Position(val y: Int, val x: Int)

/**
 * Starts from relative (0, 0) position
 */
class HeadTailPair {
    private var headY = 0
    private var headX = 0
    private var tailY = 0
    private var tailX = 0
    val tailPositions = mutableSetOf<Position>()

    fun moveHead(direction: Direction, amount: Int) {
        repeat(amount) { moveHead(direction) }
    }

    fun moveHead(direction: Direction) {
        headY += direction.yDelta
        headX += direction.xDelta
        dragTail()
        markTailPosition()
    }

    private fun dragTail() {
        val yDiff = headY - tailY
        val xDiff = headX - tailX
        val absYDiff = kotlin.math.abs(yDiff)
        val absXDiff = kotlin.math.abs(xDiff)

        if (!touching(yDiff, xDiff)) {
            // tail movement limited to 1 in any direction
            val tailMovementY = if (absYDiff == 2) yDiff - yDiff.sign else yDiff.sign
            val tailMovementX = if (absXDiff == 2) xDiff - xDiff.sign else xDiff.sign
            tailY += tailMovementY
            tailX += tailMovementX
        }
    }

    private fun touching(yDiff: Int, xDiff: Int): Boolean {
        val chebysevDistance = kotlin.math.max(kotlin.math.abs(yDiff), kotlin.math.abs(xDiff))
        return chebysevDistance <= 1
    }

    private fun markTailPosition() {
        tailPositions.add(Position(tailY, tailX)) // don't care about if it was already present or not
    }
}

fun solver09a(inputFile: File): Int {
    val pair = HeadTailPair()
    inputFile.forEachLine { line ->
        val tokens = line.split(' ')
        val direction = Direction.values().first { it.letter == tokens[0][0] } // unnecessary looping on enum values
        val amount = tokens[1].toInt()

        pair.moveHead(direction, amount)
    }

    val result = pair.tailPositions.size

    println("Tail visited $result unique positions")
    return result
}

fun solver09b(inputFile: File): Int {
    return 1
}
