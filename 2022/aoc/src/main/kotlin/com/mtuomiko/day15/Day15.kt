package com.mtuomiko.day15

import java.io.Reader
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Coordinates(val y: Int, val x: Int) {
    override fun toString(): String {
        return "[y=$y, x=$x]"
    }
}

class Sensor(
    val coordinates: Coordinates,
    val beaconCoordinates: Coordinates
) {
    val beaconManhattanDistance =
        abs(coordinates.y - beaconCoordinates.y) + abs(coordinates.x - beaconCoordinates.x)

    fun xValuesWithinDistanceForYRow(y: Int): IntRange? {
        val yDiff = abs(coordinates.y - y)
        val first = coordinates.x - (beaconManhattanDistance - yDiff)
        val last = coordinates.x + (beaconManhattanDistance - yDiff)
        return if (first > last) null else first..last
    }
}

class IntRangeContainer(private val rangeList: MutableList<IntRange> = mutableListOf()) {
    fun add(intRange: IntRange): IntRangeContainer {
        rangeList.add(intRange)
        combine()
        return this
    }

    fun getAsSet() = rangeList.fold(setOf<Int>()) { acc, range -> acc union range.toSet() }

    fun getFirstIntBetweenLimitsButNotContained(min: Int, max: Int): Int? {
        if (containedCountBetweenLimits(min, max) == max - min) return null // all values contained

        for (x in min..max) {
            if (!contained(x)) return x
        }
        return null
    }

    // real janky but should suffice for small amount of ranges
    private fun combine() {
        var combined = false
        if (rangeList.size <= 1) return

        outer@ for (a in rangeList) {
            for (b in rangeList) {
                if (a === b) continue
                if (a.first <= b.last && a.last >= b.first) {
                    val newRange = (min(a.first, b.first))..(max(a.last, b.last))
                    rangeList.add(newRange)
                    rangeList.remove(a)
                    rangeList.remove(b)
                    combined = true
                    break@outer
                }
            }
        }

        if (combined) combine() // rerun after finding combined pair
    }

    private fun containedCountBetweenLimits(min: Int, max: Int): Int {
        var count = 0
        rangeList.forEach {
            count += min(it.last, max) - max(it.first, min) // intersecting amount
        }
        return count
    }

    private fun contained(value: Int) = rangeList.any { it.contains(value) }
}

private fun parseSensor(line: String): Sensor {
    val digitPattern = Regex("-?\\d+")
    val results = digitPattern.findAll(line)
    val numbersList = results.map { it.value.toInt() }.toList()

    val sensorCoordinates = Coordinates(y = numbersList[1], x = numbersList[0])
    val beaconCoordinates = Coordinates(y = numbersList[3], x = numbersList[2])

    return Sensor(sensorCoordinates, beaconCoordinates)
}

fun solver15a(input: Reader): Int {
    return solver15a(input, 2000000)
}

fun solver15a(input: Reader, y: Int): Int {
    val sensors = input.buffered().lineSequence().map { parseSensor(it) }.toList()
    val xValueRangeContainer = sensors
        .mapNotNull { it.xValuesWithinDistanceForYRow(y) }
        .fold(IntRangeContainer()) { container, xValues -> container.add(xValues) }
    val existingBeaconXValues = sensors.filter { it.beaconCoordinates.y == y }.map { it.beaconCoordinates.x }.toSet()
    val xValuesWithoutExistingBeacons = xValueRangeContainer.getAsSet() subtract existingBeaconXValues

    val result = xValuesWithoutExistingBeacons.size
    println("Row y=$y cannot contain a beacon in $result positions")
    return result
}


fun solver15b(input: Reader): Long {
    return solver15b(input, 4000000)
}

fun solver15b(input: Reader, max: Int): Long {
    val sensors = input.buffered().lineSequence().map { parseSensor(it) }.toList()

    var freeCoordinates: Coordinates? = null
    for (row in 0..max) {
        val xValueRangeContainer = sensors
            .mapNotNull { it.xValuesWithinDistanceForYRow(row) }
            .fold(IntRangeContainer()) { container, xValues -> container.add(xValues) }
        val xFree = xValueRangeContainer.getFirstIntBetweenLimitsButNotContained(0, max)
        if (xFree != null) {
            freeCoordinates = Coordinates(y = row, x = xFree)
            break
        }
    }

    val result = freeCoordinates!!.x * 4000000L + freeCoordinates.y
    println("Within y,x limits 0 to $max, beacon must be in $freeCoordinates. Result is $result")
    return result
}
