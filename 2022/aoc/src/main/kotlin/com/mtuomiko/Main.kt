package com.mtuomiko

import com.mtuomiko.day09.solver09a
import com.mtuomiko.day09.solver09b
import java.io.File

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
)

const val resourcesPath = "src/main/resources/"

fun main(args: Array<String>) {
    if (args.size != 2) {
        throw Exception("give solver and input as arguments")
    }
    val solverString = args[0]
    val inputFilename = args[1]
    val file = File(resourcesPath + inputFilename)

    val solver = availableSolvers[solverString] ?: throw Exception("give valid solver")

    val result = solver(file)
    println("Solver $solverString: $result")
}
