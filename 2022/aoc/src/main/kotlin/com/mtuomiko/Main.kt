package com.mtuomiko

import com.mtuomiko.day09.solver09a
import com.mtuomiko.day09.solver09b
import com.mtuomiko.day10.solver10a
import com.mtuomiko.day10.solver10b
import com.mtuomiko.day11.solver11a
import com.mtuomiko.day11.solver11b
import com.mtuomiko.day12.solver12a
import com.mtuomiko.day12.solver12b
import com.mtuomiko.day13.solver13a
import com.mtuomiko.day13.solver13b
import com.mtuomiko.day14.solver14a
import com.mtuomiko.day14.solver14b
import com.mtuomiko.day15.solver15a
import com.mtuomiko.day15.solver15b
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

val availableSolvers = mapOf(
    "01a" to ::solver01a, "01b" to ::solver01b,
    "02a" to ::solver02a, "02b" to ::solver02b,
    "03a" to ::solver03a, "03b" to ::solver03b,
    "04a" to ::solver04a, "04b" to ::solver04b,
    "05a" to ::solver05a, "05b" to ::solver05b,
    "06a" to ::solver06a, "06b" to ::solver06b,
    "07a" to ::solver07a, "07b" to ::solver07b,
    "08a" to ::solver08a, "08b" to ::solver08b,
    "09a" to ::solver09a, "09b" to ::solver09b,
    "10a" to ::solver10a, "10b" to ::solver10b,
    "11a" to ::solver11a, "11b" to ::solver11b,
    "12a" to ::solver12a, "12b" to ::solver12b,
    "13a" to ::solver13a, "13b" to ::solver13b,
    "14a" to ::solver14a, "14b" to ::solver14b,
    "15a" to ::solver15a, "15b" to ::solver15b,
)

const val resourcesPath = "src/main/resources/"

fun main(args: Array<String>) {
    if (args.size != 2) {
        throw IllegalArgumentException("give solver and input as arguments")
    }
    val solverString = args[0]
    val inputFilename = args[1]

    val file = File(resourcesPath + inputFilename)
    val inputReader = InputStreamReader(FileInputStream(file), Charsets.UTF_8)

    val solver = availableSolvers[solverString] ?: throw IllegalArgumentException("give valid solver")

    val result = solver(inputReader)
    println("Solver $solverString: $result")
}
