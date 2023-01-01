package com.mtuomiko.day14

import java.io.Reader
import kotlin.math.sign

private const val COORDINATE_SEPARATOR = " -> "
private const val AXIS_SEPARATOR = ','
private const val AIR = '.'
private const val ROCK = '#'
private const val SAND = 'o'
private const val SAND_ORIGIN_X = 500
private const val SAND_ORIGIN_Y = 0

class Coordinates(val y: Int, val x: Int)

private fun drawLine(grid: Array<CharArray>, a: Coordinates, b: Coordinates) {
    val yDiff = a.y - b.y
    val xDiff = a.x - b.x
    if (yDiff != 0 && xDiff != 0) throw Exception("Can only draw straight lines")
    val stepDelta = Coordinates(y = (-yDiff).sign, x = (-xDiff).sign)
    val rockCount = kotlin.math.abs(if (yDiff != 0) yDiff else xDiff) + 1

    var y = a.y
    var x = a.x
    repeat(rockCount) {
        grid[y][x] = ROCK
        y += stepDelta.y
        x += stepDelta.x
    }
}

interface Particle {
    val symbol: Char
    val priorities: List<Coordinates>
}

object Sand : Particle {
    override val symbol = SAND
    override val priorities = listOf(
        Coordinates(1, 0),
        Coordinates(1, -1),
        Coordinates(1, 1)
    )
}

class Animator(private val grid: Array<CharArray>, private val origin: Coordinates, private val particle: Particle) {
    var counter = 0
        private set

    fun produceUntilOverflowOrFull() {
        while (grid[origin.y][origin.x] == AIR && singleParticle()) {
            counter++
        }
    }

    private fun singleParticle(): Boolean {
        var y = origin.y
        var x = origin.x

        outer@ while (true) {
            for (priority in particle.priorities) {
                val newY = y + priority.y
                val newX = x + priority.x
                if (positionOutOfBounds(newY, newX)) return false
                if (grid[newY][newX] == AIR) {
                    y = newY
                    x = newX
                    continue@outer
                }
            }
            grid[y][x] = particle.symbol
            return true
        }
    }

    private fun positionOutOfBounds(y: Int, x: Int) = (x < 0 || x >= grid[0].size || y >= grid.size)
}

class Grid(val grid: Array<CharArray>, val xOffset: Int)

fun commonSolver(input: Reader, gridCreator: (maxX: Int, minX: Int, maxY: Int) -> Grid): Int {
    var maxX = 0
    var minX = Integer.MAX_VALUE
    var maxY = 0
    val sections = input.buffered().lineSequence().map { row ->
        val coordinateStrings = row.split(COORDINATE_SEPARATOR)
        coordinateStrings.map {
            val values = it.split(AXIS_SEPARATOR)
            val x = values[0].toInt()
            val y = values[1].toInt()
            if (x > maxX) maxX = x
            else if (x < minX) minX = x
            if (y > maxY) maxY = y
            Coordinates(y, x)
        }
    }.toList()

    val grid = gridCreator(maxX, minX, maxY)

    sections.forEach { section ->
        section
            .map { Coordinates(it.y, it.x + grid.xOffset) } // translate x-coordinates
            .zipWithNext { a, b -> drawLine(grid.grid, a, b) }
    }

    val sandAnimator = Animator(grid.grid, Coordinates(y = SAND_ORIGIN_Y, x = SAND_ORIGIN_X + grid.xOffset), Sand)
    sandAnimator.produceUntilOverflowOrFull()

    return sandAnimator.counter
}

fun solver14a(input: Reader): Int {
    fun gridCreator(maxX: Int, minX: Int, maxY: Int): Grid {
        // Reserve needed grid near 0,0. Size includes extra for zero indices.
        val xSize = (maxX - minX) + 1
        val xOffset = xSize - maxX - 1
        val ySize = maxY + 1
        return Grid(Array(ySize) { CharArray(xSize) { AIR } }, xOffset)
    }

    val result = commonSolver(input, ::gridCreator)

    println("Produced $result sand particles before overflow")
    return result
}

fun solver14b(input: Reader): Int {
    fun gridCreator(_maxX: Int, _minX: Int, maxY: Int): Grid {
        // Reserve needed grid near 0,0
        val ySize = maxY + 3 // extra for zero indices + floor space in y axis
        val xSize = (ySize * 2) + 1 // needed floor space for max height pile
        val xOffset = (xSize / 2) - SAND_ORIGIN_X // center on origin in x-axis
        val grid = Array(ySize) { CharArray(xSize) { AIR } }

        drawLine(grid, Coordinates(ySize - 1, 0), Coordinates(ySize - 1, xSize - 1)) // draw floor

        return Grid(grid, xOffset)
    }

    val result = commonSolver(input, ::gridCreator)

    println("Produced $result particles before filling up or overflowing")
    return result
}
