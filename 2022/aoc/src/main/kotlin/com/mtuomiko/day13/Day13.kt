package com.mtuomiko.day13

import java.io.Reader

private interface Node

private class ListNode(
    val values: MutableList<Node> = mutableListOf(),
    var consumedTokens: Int = 0
) : Node

private class IntNode(
    val value: Int
) : Node

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

private fun isInRightOrder(left: Node, right: Node): Int {
    if (left is IntNode && right is IntNode) {
        if (left.value == right.value) return 0
        if (left.value < right.value) return 1
        if (left.value > right.value) return -1
    }

    if (left is ListNode && right is ListNode) {
        val min = kotlin.math.min(left.values.size, right.values.size)
        for (i in 0 until min) {
            val comparison = isInRightOrder(left.values[i], right.values[i])
            if (comparison != 0) return comparison
        }
        if (left.values.size < right.values.size) return 1
        if (left.values.size > right.values.size) return -1
        return 0
    }

    if (left is IntNode) return isInRightOrder(ListNode(mutableListOf(left)), right)
    if (right is IntNode) return isInRightOrder(left, ListNode(mutableListOf(right)))

    throw Exception("Unreachable")
}

fun solver13a(input: Reader): Int {
    val sum = input.buffered().lineSequence().chunked(3).mapIndexed { index, chunk ->
        val left = parsePacket(chunk[0])
        val right = parsePacket(chunk[1])

        val result = isInRightOrder(left, right)
        if (result == 1) index + 1
        else 0
    }.reduce { acc, value -> acc + value }

    println("Sum of indexes of packets in right order is $sum")
    return sum
}

fun solver13b(input: Reader): Int {
    return 0
}
