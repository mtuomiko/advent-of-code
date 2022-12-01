import java.io.File

val availableSolvers = mapOf(
    "01a" to ::solver01a,
    "01b" to ::solver01b
)

const val resourcesPath = "src/main/resources/"

fun main(args: Array<String>) {
    if (args.size != 2) { throw Exception("give solver and input as arguments") }
    val file = File(resourcesPath + args[1])

    val solver = availableSolvers[args[0]] ?: throw Exception("give valid solver")

    solver(file)
}
