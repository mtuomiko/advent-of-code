import java.io.File

enum class Result(val score: Int) {
    LOSS(0),
    DRAW(3),
    WIN(6)
}

enum class Item(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

val tokenToItemMap = mapOf(
    'A' to Item.ROCK,
    'X' to Item.ROCK,
    'B' to Item.PAPER,
    'Y' to Item.PAPER,
    'C' to Item.SCISSORS,
    'Z' to Item.SCISSORS
)

val evaluation = mapOf(
    Item.ROCK to mapOf(Item.ROCK to Result.DRAW, Item.PAPER to Result.LOSS, Item.SCISSORS to Result.WIN),
    Item.PAPER to mapOf(Item.ROCK to Result.WIN, Item.PAPER to Result.DRAW, Item.SCISSORS to Result.LOSS),
    Item.SCISSORS to mapOf(Item.ROCK to Result.LOSS, Item.PAPER to Result.WIN, Item.SCISSORS to Result.DRAW),
)

fun solver02a(inputFile: File) {
    var score = 0

    inputFile.forEachLine { line ->
        val tokens = line.split(' ').map { it[0] }
        val opponent = tokenToItemMap[tokens[0]]!!
        val player = tokenToItemMap[tokens[1]]!!

        val result = evaluation[player]!![opponent]!!
        val roundResult = result.score + player.score
        score += roundResult
    }

    println(score)
}

val tokenToResultMap = mapOf(
    'X' to Result.LOSS,
    'Y' to Result.DRAW,
    'Z' to Result.WIN
)

val evaluationForItem = mapOf(
    Result.LOSS to mapOf(Item.ROCK to Item.SCISSORS, Item.PAPER to Item.ROCK, Item.SCISSORS to Item.PAPER),
    Result.DRAW to mapOf(Item.ROCK to Item.ROCK, Item.PAPER to Item.PAPER, Item.SCISSORS to Item.SCISSORS),
    Result.WIN to mapOf(Item.ROCK to Item.PAPER, Item.PAPER to Item.SCISSORS, Item.SCISSORS to Item.ROCK),
)

fun solver02b(inputFile: File) {

    var score = 0

    inputFile.forEachLine { line ->
        val tokens = line.split(' ').map { it[0] }
        val opponent = tokenToItemMap[tokens[0]]!!

        val result = tokenToResultMap[tokens[1]]!!
        val player = evaluationForItem[result]!![opponent]!!

        val roundResult = result.score + player.score
        score += roundResult
    }

    println(score)
}
