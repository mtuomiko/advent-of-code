package com.mtuomiko.day10

import java.io.Reader

const val ROWS = 6
const val COLS = 40
const val DEFAULT_CHAR = '-'
const val LIT = '#'
const val DARK = '.'

open class Machine(protected val interestingCycles: List<Int> = emptyList()) {
    val signalStrengths = mutableListOf<Int>()
    protected var xRegister = 1
    protected var cycle = 1

    fun noOperation() {
        tick()
    }

    fun addXOperation(amount: Int) {
        tick()
        tick()
        xRegister += amount
    }

    protected open fun tick() {
        if (cycle in interestingCycles) {
            signalStrengths.add(cycle * xRegister)
        }
        cycle++
    }
}

class DrawingMachine(interestingCycles: List<Int> = emptyList()) : Machine(interestingCycles) {
    private var horizontalIndex = 0
    private var verticalIndex = 0
    val displayBuffer = Array(ROWS) { CharArray(COLS) { DEFAULT_CHAR } }

    override fun tick() {
        if (cycle in interestingCycles) {
            signalStrengths.add(cycle * xRegister)
        }
        checkEndOfRow()
        draw()
        cycle++
        horizontalIndex++
    }

    private fun checkEndOfRow() {
        if (horizontalIndex >= COLS) {
            horizontalIndex = 0
            verticalIndex++
        }
    }

    private fun draw() {
        displayBuffer[verticalIndex][horizontalIndex] = if (horizontalIndexUnderSprite()) {
            LIT
        } else {
            DARK
        }
    }

    private fun horizontalIndexUnderSprite() = kotlin.math.abs(horizontalIndex - xRegister) <= 1
}

fun runOperationsOnMachine(input: Reader, machine: Machine) {
    input.forEachLine {
        val tokens = it.split(' ')
        val operation = tokens[0]
        when (operation) {
            "noop" -> machine.noOperation()
            "addx" -> {
                val amount = tokens[1].toInt()
                machine.addXOperation(amount)
            }
            else -> throw Exception("Unknown operation")
        }
    }
}

fun solver10a(input: Reader): Int {
    val interestingCycles = generateSequence(20) { it + 40 }.take(6).toList()
    val machine = Machine(interestingCycles)

    runOperationsOnMachine(input, machine)

    val result = machine.signalStrengths.sum()
    println("Sum of signal strengths at $interestingCycles cycles was $result")
    return result
}

fun solver10b(input: Reader): List<String> {
    val machine = DrawingMachine()

    runOperationsOnMachine(input, machine)

    val result = machine.displayBuffer.map { it.joinToString("") }.toList()
    println("CRT image:")
    result.forEach(::println)
    return result
}
