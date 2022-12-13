package com.mtuomiko.day12

import java.io.Reader

const val MAX_CLIMBING_CAPABILITY = 1
const val INITIAL_DISTANCE = -1
const val START = 'S'
const val START_HEIGHT_EQUIVALENT = 'a'
const val END = 'E'
const val END_HEIGHT_EQUIVALENT = 'z'

class Position(val y: Int, val x: Int)

enum class Direction(val yDelta: Int, val xDelta: Int) {
    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1)
}

/**
 * Edges are reversed in order to handle multiple start positions easier. Meaning, that an edge of a node symbolises
 * that it's possible to traverse from the other node to this node. This shouldn't matter in practice, just that it can
 * be confusing compared to the challenge description.
 */
class Node(
    char: Char,
    val edges: MutableList<Node> = mutableListOf(),
    var distance: Int = INITIAL_DISTANCE
) {
    private val height = when (char) {
        START -> START_HEIGHT_EQUIVALENT.code
        END -> END_HEIGHT_EQUIVALENT.code
        else -> char.code
    }

    fun canBeTraversedFrom(other: Node) = (height - other.height) <= MAX_CLIMBING_CAPABILITY
}

class Grid(val starts: List<Node>, val end: Node)

private fun parseGrid(lines: List<String>, allowMultipleStarts: Boolean): Grid {
    val height = lines.size
    val width = lines[0].length

    val tempGridArray = Array(height) { arrayOfNulls<Node>(width) }
    val starts = mutableListOf<Node>()
    var end: Node? = null
    // initialize
    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, character ->
            val node = Node(character)
            if (character == START) starts.add(node)
            else if (character == END) end = node
            else if (character == START_HEIGHT_EQUIVALENT && allowMultipleStarts) starts.add(node)
            tempGridArray[y][x] = node
        }
    }
    // set edges
    tempGridArray.forEachIndexed { y, nodeArray ->
        nodeArray.forEachIndexed { x, node ->
            val allTargets = Direction.values().map { Position(y + it.yDelta, x + it.xDelta) }
            val withinBounds = allTargets.filter { it.y in 0 until height && it.x in 0 until width }
            val nodes = withinBounds.map { tempGridArray[it.y][it.x]!! }
            val withOkHeight = nodes.filter { node!!.canBeTraversedFrom(it) }
            node!!.edges.addAll(withOkHeight)
        }
    }

    return Grid(starts, end!!)
}

/**
 * Basic breadth first search. The search is reversed to handle multiple start positions easier and thus assumes that
 * the node edges are reversed as well.
 */
private fun findShortestPathLengthToEnd(grid: Grid): Int {
    val queue = ArrayDeque<Node>()
    grid.end.apply {
        distance = 0
    }
    queue.add(grid.end)

    while (queue.isNotEmpty()) {
        val node = queue.removeLast()
        // note referential comparison
        if (grid.starts.any { node === it }) {
            return node.distance
        }
        node.edges.forEach {
            // only proceed if target node hasn't been visited
            if (it.distance == INITIAL_DISTANCE) {
                it.distance = node.distance + 1
                queue.addFirst(it)
            }
        }
    }

    return -1
}

fun solver12a(input: Reader): Int {
    val lines = input.readLines()
    val grid = parseGrid(lines, false)

    val result = findShortestPathLengthToEnd(grid)

    println("Shortest path from S to E took $result steps")
    return result
}

fun solver12b(input: Reader): Int {
    val lines = input.readLines()
    val grid = parseGrid(lines, true)

    val result = findShortestPathLengthToEnd(grid)

    println("Shortest path from S or any low ground to E took $result steps")
    return result
}
