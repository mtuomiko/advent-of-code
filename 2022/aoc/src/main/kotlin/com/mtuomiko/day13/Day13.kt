package com.mtuomiko.day13

import java.io.Reader

private interface Node : Comparable<Node> {
    override fun equals(other: Any?): Boolean
    override fun toString(): String
}

private class ListNode(
    val values: MutableList<Node> = mutableListOf(),
    var consumedTokens: Int = 0
) : Node {
    override fun compareTo(other: Node): Int {
        if (other is ListNode) {
            val min = kotlin.math.min(this.values.size, other.values.size)
            for (i in 0 until min) {
                val comparison = this.values[i].compareTo(other.values[i])
                if (comparison != 0) return comparison
            }
            if (this.values.size < other.values.size) return 1
            if (this.values.size > other.values.size) return -1
            return 0
        }
        return this.compareTo(ListNode(mutableListOf(other)))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ListNode

        if (values != other.values) return false

        return true
    }

    override fun toString(): String {
        return "[$values]"
    }
}

private class IntNode(
    val value: Int
) : Node {
    override fun compareTo(other: Node): Int {
        if (other is IntNode) {
            if (this.value == other.value) return 0
            if (this.value < other.value) return 1
            if (this.value > other.value) return -1
        }
        return ListNode(mutableListOf(this)).compareTo(other)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IntNode

        if (value != other.value) return false

        return true
    }

    override fun toString(): String {
        return "$value"
    }

}

private fun parseTokens(tokens: List<String>): ListNode {
    val returnList = mutableListOf<Node>()
    var count = 0
    while (count < tokens.size) {
        val token = tokens[count]
        count++
        when (token) {
            "[" -> {
                val subList = parseTokens(tokens.subList(count, tokens.size))
                count += subList.consumedTokens
                returnList.add(subList)
            }

            "]" -> break
            "," -> continue
            else -> returnList.add(IntNode(token.toInt()))
        }
    }
    return ListNode(returnList, count)
}

private fun parsePacket(packet: String): ListNode {
    val tokens = packet.split(Regex("((?<=[\\[\\],])|(?=[\\[\\],]))"))
    return parseTokens(tokens.slice(1 until tokens.size - 1))
}

fun solver13a(input: Reader): Int {
    val sum = input.buffered().lineSequence().chunked(3).mapIndexed { index, chunk ->
        val left = parsePacket(chunk[0])
        val right = parsePacket(chunk[1])

        val result = left.compareTo(right)
        if (result == 1) index + 1
        else 0
    }.reduce { acc, value -> acc + value }

    println("Sum of indexes of packets in right order is $sum")
    return sum
}

fun solver13b(input: Reader): Int {
    val divider1 = parsePacket("[[2]]")
    val divider2 = parsePacket("[[6]]")
    val sortedPackets = input.buffered().lineSequence()
        .filter { it.isNotEmpty() }
        .map { parsePacket(it) }
        .toMutableList().also { it.addAll(listOf(divider1, divider2)) }
        .toList()
        .sortedDescending()
    val dividerIndices = sortedPackets.foldIndexed(Pair(-1, -1)) { index, result, packet ->
        when (packet) {
            divider1 -> result.copy(first = index + 1)
            divider2 -> result.copy(second = index + 1)
            else -> result
        }
    }
    val product = dividerIndices.first * dividerIndices.second

    println("Divider packets at indices ${dividerIndices.first} and ${dividerIndices.second}, decoder key is $product")
    return product
}
