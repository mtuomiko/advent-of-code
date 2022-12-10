package com.mtuomiko

import java.io.File
import kotlin.math.max

fun solver08a(inputFile: File): Int {
    fun markVisibleHorizontally(visibilityGrid: Array<BooleanArray>, lines: List<String>) {
        lines.forEachIndexed { y, line ->
            var maximum = -1

            for ((x, c) in line.withIndex()) { // from left
                val value = c.digitToInt()
                if (!visibilityGrid[y][x]) {
                    if (value > maximum) visibilityGrid[y][x] = true
                }
                maximum = max(maximum, value)
                if (maximum == 9) break
            }

            maximum = -1

            for ((x, c) in line.withIndex().reversed()) { // from right
                val value = c.digitToInt()
                if (!visibilityGrid[y][x]) {
                    if (value > maximum) visibilityGrid[y][x] = true
                }
                maximum = max(maximum, value)
                if (maximum == 9) break
            }
        }
    }

    fun markVisibleVertically(visibilityGrid: Array<BooleanArray>, lines: List<String>) {
        for (x in lines[0].indices) {
            var maximum = -1

            for (y in lines.indices) { // from top
                val value = lines[y][x].digitToInt()
                if (!visibilityGrid[y][x]) {
                    if (value > maximum) visibilityGrid[y][x] = true
                }
                maximum = max(maximum, value)
                if (maximum == 9) break
            }

            maximum = -1

            for (y in lines.indices.reversed()) { // from bottom
                val value = lines[y][x].digitToInt()
                if (!visibilityGrid[y][x]) {
                    if (value > maximum) visibilityGrid[y][x] = true
                }
                maximum = max(maximum, value)
                if (maximum == 9) break
            }
        }
    }

    val lines = inputFile.readLines()

    val height = lines.size
    val width = lines[0].length

    val visibilityGrid = Array(height) { BooleanArray(width) { false } }

    markVisibleHorizontally(visibilityGrid, lines)
    markVisibleVertically(visibilityGrid, lines)

    val visibleCount = visibilityGrid.fold(0) { count, row ->
        count + row.fold(0) { rowCount, element ->
            if (element) rowCount + 1 else rowCount
        }
    }
    println("Visible trees $visibleCount")
    return visibleCount
}

enum class Direction(val yDelta: Int, val xDelta: Int) {
    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1)
}

/**
 * Obs! Mutable!
 */
class Position(var y: Int, var x: Int) {
    fun move(direction: Direction): Position {
        y += direction.yDelta
        x += direction.xDelta
        return this
    }
}

class ScenicScoreSolver(private val lines: List<String>) {
    private val height = lines.size
    private val width = lines[0].length

    fun getMaximumScenicScore(): Long {
        var maximumScore = 0L

        // disregard edges, acquire score
        for (y in 1 until height) {
            for (x in 1 until width) {
                val score = calculateScenicScore(y, x)
                if (score > maximumScore) maximumScore = score
            }
        }

        return maximumScore
    }

    /**
     * Multiply scores of all directions.
     */
    private fun calculateScenicScore(y: Int, x: Int) = Direction.values()
        .fold(1L) { product, direction ->
            product * calculateScoreInDirection(y, x, direction)
        }

    private fun calculateScoreInDirection(y: Int, x: Int, direction: Direction): Int {
        val originalValue = lines[y][x].digitToInt()
        var score = 0
        val pointer = Position(y, x).move(direction) // include initial step

        while (boundaryCondition(pointer, direction)) {
            score++
            if (originalValue <= lines[pointer.y][pointer.x].digitToInt()) break
            pointer.move(direction)
        }

        return score
    }

    /**
     * Returns false only when y or x is out-of-bounds *in the given* [direction]. Does not check the other
     * directions.
     */
    private fun boundaryCondition(position: Position, direction: Direction) = with(position) {
        when (direction) {
            Direction.NORTH -> y >= 0
            Direction.SOUTH -> y < height
            Direction.EAST -> x < width
            Direction.WEST -> x >= 0
        }
    }
}

// Naive solution, will repeatedly check same elements.
fun solver08b(inputFile: File): Long {
    val lines = inputFile.readLines()

    val solver = ScenicScoreSolver(lines)

    val result = solver.getMaximumScenicScore()

    println("Maximum scenic score is $result")
    return result
}
