package com.mtuomiko.day09

import java.io.Reader
import kotlin.math.sign

enum class Direction(val yDelta: Int, val xDelta: Int, val letter: Char) {
    UP(-1, 0, 'U'),
    DOWN(1, 0, 'D'),
    RIGHT(0, 1, 'R'),
    LEFT(0, -1, 'L')
}

data class Position(val y: Int, val x: Int)

class Knot(val next: Knot? = null) {
    private var y = 0
    private var x = 0
    val tailPositions = if (next == null) {
        mutableSetOf(Position(0, 0))
    } else {
        null
    }

    fun getPosition() = Position(y, x)

    fun move(yDelta: Int, xDelta: Int, amount: Int) {
        repeat(amount) { move(yDelta, xDelta) }
    }

    fun move(yDelta: Int, xDelta: Int) {
        y += yDelta
        x += xDelta
        if (next == null) {
            tailPositions!!.add(Position(y, x))
            return
        }
        dragNext()
    }

    private fun dragNext() {
        val nextPosition = next!!.getPosition()
        val yDiff = y - nextPosition.y
        val xDiff = x - nextPosition.x
        val absYDiff = kotlin.math.abs(yDiff)
        val absXDiff = kotlin.math.abs(xDiff)

        if (!touching(yDiff, xDiff)) {
            // next knot movement limited to 1 in any direction
            val nextMovementY = if (absYDiff == 2) yDiff - yDiff.sign else yDiff.sign
            val nextMovementX = if (absXDiff == 2) xDiff - xDiff.sign else xDiff.sign
            next.move(nextMovementY, nextMovementX)
        }
    }

    private fun touching(yDiff: Int, xDiff: Int): Boolean {
        val chebysevDistance = kotlin.math.max(kotlin.math.abs(yDiff), kotlin.math.abs(xDiff))
        return chebysevDistance <= 1
    }
}

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

private fun commonSolver(input: Reader, ropeLength: Int): Int {
    fun createRope(length: Int): List<Knot> {
        var previousKnot: Knot? = null
        return List(length) {
            val newKnot = Knot(previousKnot)
            previousKnot = newKnot
            newKnot
        }
    }
    val rope = createRope(ropeLength)
    val head = rope.last()
    val tail = rope.first()
    input.forEachLine { line ->
        val tokens = line.split(' ')
        val direction = Direction.values().first { it.letter == tokens[0][0] } // unnecessary looping on enum values
        val amount = tokens[1].toInt()

        head.move(direction.yDelta, direction.xDelta, amount)
    }

    val result = tail.tailPositions!!.size

    println("Rope of length $ropeLength had it's tail visit $result unique positions")
    return result
}

fun solver09a(input: Reader): Int {
    return commonSolver(input, 2)
}

fun solver09b(input: Reader): Int {
    return commonSolver(input, 10)
}
