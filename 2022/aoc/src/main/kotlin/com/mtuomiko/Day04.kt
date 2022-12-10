package com.mtuomiko

import java.io.File

private fun lineToAssignmentPair(line: String): Pair<IntRange, IntRange> {
    val splitPair = line.split(',').map { assignmentString ->
        assignmentString.split('-').map { sectionIdString -> sectionIdString.toInt() }
    }
    return Pair(
        splitPair[0][0]..splitPair[0][1],
        splitPair[1][0]..splitPair[1][1]
    )
}

fun solver04a(inputFile: File): Int {
    fun eitherContainsOther(first: IntRange, second: IntRange): Boolean {
        if (first.first <= second.first && first.last >= second.last) return true // first contains second
        if (first.first >= second.first && first.last <= second.last) return true // second contains first
        return false
    }

    var completeOverlapCount = 0

    inputFile.forEachLine {
        val pair = lineToAssignmentPair(it)

        if (eitherContainsOther(pair.first, pair.second)) completeOverlapCount++
    }

    println("One assignment contained in other $completeOverlapCount times")
    return completeOverlapCount
}

fun solver04b(inputFile: File): Int {
    fun overlapExists(first: IntRange, second: IntRange): Boolean {
        if (first.first > second.last || second.first > first.last) {
            return false // either starts after other's end, cannot overlap
        }
        return true
    }

    var overlapCount = 0

    inputFile.forEachLine {
        val pair = lineToAssignmentPair(it)

        if (overlapExists(pair.first, pair.second)) overlapCount++
    }

    println("Assignments overlap $overlapCount times")
    return overlapCount
}
