package com.mtuomiko

import java.io.File
import java.util.*

fun solver01a(inputFile: File) {
    var maxElfCalories = 0
    var currentElfCalories = 0

    inputFile.forEachLine {
        if (it.isNotEmpty()) {
            val calories = it.toInt()
            currentElfCalories += calories
        } else {
            if (currentElfCalories > maxElfCalories) {
                maxElfCalories = currentElfCalories
            }
            currentElfCalories = 0
        }
    }

    if (currentElfCalories > maxElfCalories) {
        maxElfCalories = currentElfCalories
    }

    println("Maximum calories: $maxElfCalories")
}

fun solver01b(inputFile: File) {
    val queue = SizedMinMaxPriorityQueue(3)
    var currentElfCalories = 0

    inputFile.forEachLine {
        if (it.isNotEmpty()) {
            val calories = it.toInt()
            currentElfCalories += calories
        } else {
            queue.add(currentElfCalories)
            currentElfCalories = 0
        }
    }

    queue.add(currentElfCalories)
    val total = queue.total()

    println("Total calories for top ${queue.size()} elfs: $total")
}

/**
 * Ordered as a min priority queue, retains only the maximum elements
 */
class SizedMinMaxPriorityQueue(private val maxSize: Int) {
    private val pq = PriorityQueue<Int>(maxSize)

    fun add(value: Int) {
        val currentMinimumInQueue = pq.peek()
        if (currentMinimumInQueue == null || value > currentMinimumInQueue) {
            pq.add(value)
            if (pq.size > maxSize) pq.poll()
        }
    }

    fun total(): Long = pq.fold(0L) { acc, value -> acc + value }

    fun size(): Int = pq.size
}
