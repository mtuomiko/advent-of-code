package com.mtuomiko

import java.io.File

const val COMMAND_PREFIX = "$"
const val CHANGE_DIR = "cd"
const val ROOT = "/"
const val PARENT = ".."
const val DIR = "dir"

abstract class Node(val parent: DirNode?, val name: String)

class DirNode(
    parent: DirNode?,
    name: String,
    val nodes: MutableMap<String, Node> = mutableMapOf()
) : Node(parent, name)

class FileNode(parent: DirNode?, name: String, val size: Int) : Node(parent, name)

/**
 * Container for the filesystem tree structure. Doesn't consider the size of nodes.
 */
class Parser {
    val root: DirNode = DirNode(null, "")
    private lateinit var currentDir: DirNode // input needs to start from root, will practically never be null

    fun processLine(line: String) {
        val tokens = line.split(' ')
        if (tokens[0] == COMMAND_PREFIX) {
            if (tokens[1] == CHANGE_DIR) changeDir(tokens[2])
            return // ls command can be skipped, everything else in input will be ls results
        } else {
            processListing(tokens[0], tokens[1])
        }
    }

    private fun createDirIfNotExists(target: String): DirNode {
        val existingDir = currentDir.nodes[target] as DirNode?
        if (existingDir == null) {
            val createdDir = DirNode(parent = currentDir, name = target)
            currentDir.nodes[target] = createdDir
            return createdDir
        }
        return existingDir
    }


    private fun changeDir(target: String) {
        currentDir = when (target) {
            ROOT -> root
            PARENT -> currentDir.parent!!
            else -> createDirIfNotExists(target)
        }
    }

    private fun processListing(prefix: String, name: String) {
        if (prefix == DIR) {
            createDirIfNotExists(name)
            return
        }

        val fileSize = prefix.toInt()
        createFileIfNotExists(name, fileSize)
    }

    private fun createFileIfNotExists(target: String, size: Int): FileNode {
        val existingFile = currentDir.nodes[target] as FileNode?
        if (existingFile == null) {
            val createdFile = FileNode(parent = currentDir, name = target, size = size)
            currentDir.nodes[target] = createdFile
            return createdFile
        }
        return existingFile
    }
}

/**
 * Calculates size of [currentNode]. Directory node size is the sum of nested node sizes. While recursing, all found
 * directory sizes equal to or under [maxLimit] are added to [dirSizeList]. If [maxLimit] is `null`, all directory sizes
 * are added to the list.
 */
private fun recursiveSizeSearch(dirSizeList: MutableList<Long>, currentNode: Node, maxLimit: Int? = null): Long {
    if (currentNode is FileNode) return currentNode.size.toLong()

    val size = (currentNode as DirNode).nodes.values.fold(0L) { sum, node ->
        sum + recursiveSizeSearch(dirSizeList, node, maxLimit)
    }
    if (maxLimit == null || size <= maxLimit) {
        dirSizeList.add(size)
    }

    return size
}

fun solver07a(inputFile: File): Long {
    val dirMaxSizeLimit = 100000
    val parser = Parser()
    inputFile.forEachLine { parser.processLine(it) }

    val dirSizeList = mutableListOf<Long>()
    recursiveSizeSearch(dirSizeList, parser.root, dirMaxSizeLimit)
    val result = dirSizeList.filter { it <= dirMaxSizeLimit }.fold(0L) { sum, dirSize -> sum + dirSize }

    println("Sum of dir sizes at most $dirMaxSizeLimit is $result")
    return result
}

fun solver07b(inputFile: File): Long {
    val total = 70000000
    val needed = 30000000
    val maxUsageAllowed = total - needed

    val parser = Parser()
    inputFile.forEachLine { parser.processLine(it) }

    val dirSizeList = mutableListOf<Long>()
    val usage = recursiveSizeSearch(dirSizeList, parser.root)
    val minimumToDelete = usage - maxUsageAllowed

    val result = dirSizeList.filter { it >= minimumToDelete }.minOf { it }

    println("Smallest single dir that will free at least $minimumToDelete space, has the size of $result")
    return result
}
