import org.junit.jupiter.api.Test

private class MainTest {
    private val challengeCount = 7
    private val letters = listOf("a", "b")
    private val paddingTemplate = "%02d"
    private val inputSubfix = "_input.txt"
    private val argumentCombinations: List<Pair<String, String>> =
        List(challengeCount) {
            paddingTemplate.format(it + 1)
        }.flatMap { paddedNumber ->
            letters.map { letter -> Pair("$paddedNumber$letter", "$paddedNumber$inputSubfix") }
        }

    @Test
    fun allSolversCompleteUsingDefaultInputs() {
        argumentCombinations.forEach {
            main(arrayOf(it.first, it.second))
        }
    }
}
