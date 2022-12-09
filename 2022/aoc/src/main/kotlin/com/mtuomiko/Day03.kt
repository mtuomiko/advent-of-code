package com.mtuomiko

import java.io.File
import java.lang.RuntimeException

private fun getPriority(item: Char): Int {
    val offset = if (item.isUpperCase()) {
        38 // For example, 'A' has ASCII value of 65 and challenge priority is 27. 65 - 38 = 27
    } else {
        96 // 'a' ASCII value 97 and needs to be priority 1. 97 - 96  = 1
    }
    return item.code - offset
}

fun solver03a(inputFile: File) {
    fun getDuplicateItem(first: Set<Char>, second: String): Char {
        second.forEach { if (first.contains(it)) return it }
        throw RuntimeException("Duplicate char not found")
    }

    var sum = 0

    inputFile.forEachLine {
        val compartments = it.chunked(it.length / 2)
        val firstCompartmentUniqueItems = compartments[0].toSet()

        val duplicate = getDuplicateItem(firstCompartmentUniqueItems, compartments[1])

        val priority = getPriority(duplicate)

        sum += priority
    }

    println("Priority sum $sum")
}

fun solver03b(inputFile: File) {
    var sum = 0

    inputFile.bufferedReader().lineSequence().chunked(3).forEach { group ->
        val itemSets = group.map { it.toSet() }
        val singleCommonChar = itemSets.reduce { common, set -> common intersect set }.first()

        val priority = getPriority(singleCommonChar)

        sum += priority
    }

    println("Grouped priority sum $sum")
}
