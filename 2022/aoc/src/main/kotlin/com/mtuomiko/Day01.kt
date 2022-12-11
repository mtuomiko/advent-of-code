package com.mtuomiko

import java.io.Reader
import java.util.*

fun solver01a(input: Reader): Int {
    var maxElfCalories = 0
    var currentElfCalories = 0

    input.forEachLine {
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
    return maxElfCalories
}

fun solver01b(input: Reader): Long {
    val queue = SizedMinMaxPriorityQueue(3)
    var currentElfCalories = 0

    input.forEachLine {
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
    return total
}

/**
 * Ordered as a min priority queue, retains only the maximum elements once the queue fills up.
 */
class SizedMinMaxPriorityQueue(private val maxSize: Int) {
    private val pq = PriorityQueue<Int>(maxSize)

    /**
     * Add [value] to queue if it is larger than smallest element. If queue is full, removes the smallest element.
     */
    fun add(value: Int) {
        val currentMinimumInQueue = pq.peek()
        if (currentMinimumInQueue == null || value > currentMinimumInQueue) {
            pq.add(value)
            if (pq.size > maxSize) pq.poll()
        }
    }

    /**
     * Sum of all values in the queue.
     */
    fun total(): Long = pq.fold(0L) { acc, value -> acc + value }

    fun size(): Int = pq.size
}
