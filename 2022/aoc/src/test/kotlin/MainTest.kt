package com.mtuomiko

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.Reader
import kotlin.reflect.KFunction1
import kotlin.reflect.jvm.kotlinFunction

private class MainTest {

    /**
     * Check that the solvers can be run through the application. Doesn't check results, just that nothing explodes.
     */
    @Test
    fun `Main function completes using all solvers with default inputs`() {
        commandLineArguments.forEach {
            main(arrayOf(it.first, it.second))
        }
    }

    /**
     * Main regression test for solvers. Method source provides all functions to be called, their default input files
     * and expected answers. Solvers might have other contextual logging output which is not tested.
     */
    @ParameterizedTest
    @MethodSource("solverSource")
    fun `Solvers provide the correct answer`(
        kFunction: KFunction1<Reader, Any>,
        input: Reader,
        expectedResult: Any
    ) {
        val result = kFunction.invoke(input)

        assertThat(result).isEqualTo(expectedResult)
    }

    companion object {
        private const val solverString = "solver"
        private const val classPathPrefix = "com.mtuomiko."
        private const val resourcesPath = "src/main/resources/"
        private const val paddingTemplate = "%02d"
        private const val inputSubfix = "_input.txt"
        private val day10BAnswer = listOf(
            "###..#....###...##..####.###...##..#....",
            "#..#.#....#..#.#..#.#....#..#.#..#.#....",
            "#..#.#....#..#.#..#.###..###..#....#....",
            "###..#....###..####.#....#..#.#....#....",
            "#....#....#....#..#.#....#..#.#..#.#....",
            "#....####.#....#..#.#....###...##..####."
        )

        /**
         * Allows skipping :) Needs explicit typing for submaps to avoid conversion to a concrete result type. For
         * example, { 'a' to 66306, 'b' to 195292L } would otherwise result in a Long value type HashMap. Obviously
         * 195292 doesn't need Long type but that's not relevant here.
         */
        private val answers = mapOf(
            1 to mapOf<Char, Any>('a' to 66306, 'b' to 195292L),
            2 to mapOf<Char, Any>('a' to 13446, 'b' to 13509),
            3 to mapOf<Char, Any>('a' to 7795, 'b' to 2703),
            4 to mapOf<Char, Any>('a' to 485, 'b' to 857),
            5 to mapOf<Char, Any>('a' to "SBPQRSCDF", 'b' to "RGLVRCQSB"),
            6 to mapOf<Char, Any>('a' to 1640, 'b' to 3613),
            7 to mapOf<Char, Any>('a' to 1581595L, 'b' to 1544176L),
            8 to mapOf<Char, Any>('a' to 1812, 'b' to 315495L),
            9 to mapOf<Char, Any>('a' to 5907, 'b' to 2303),
            9 to mapOf<Char, Any>('a' to 5907, 'b' to 2303),
            10 to mapOf<Char, Any>('a' to 12560, 'b' to day10BAnswer),
            11 to mapOf<Char, Any>('a' to 58794L),
        )
        private val commandLineArguments: List<Pair<String, String>> = answers.flatMap { (dayNumber, answers) ->
            val paddedDayNumber = paddingTemplate.format(dayNumber)
            answers.keys.map { "$paddedDayNumber$it" to "$paddedDayNumber$inputSubfix" }
        }

        /**
         * Method source for testing all solvers using default inputs and challenge answers. Finds the functions using
         * reflection and the listed answers in the companion object. This is well in the territory of unnecessary and
         * confusing cleverness. Also, it's fragile since it relies on the function, class and package naming.
         *
         * Return Arguments should be:
         * <ul>
         * <li>solverFunction: KFunction1<Reader, Any> (result out is using Any since the result type varies)
         * <li>inputF: [Reader]
         * <li>result: Any (like Int, Long, String)
         */
        @JvmStatic
        fun solverSource(): List<Arguments> {
            val argumentTriples = answers.flatMap { (dayNumber, dayAnswers) ->
                val paddedNumber = paddingTemplate.format(dayNumber)
                val file = File(getInputFilename(paddedNumber))

                val answerFunctionPairs = pairAnswersToKFunctions(dayAnswers, paddedNumber)
                answerFunctionPairs.map {
                    val inputReader = InputStreamReader(FileInputStream(file), Charsets.UTF_8)
                    Triple(it.second, inputReader, it.first)
                }
            }
            return argumentTriples.map { Arguments.of(it.first, it.second, it.third) }
        }

        private fun getInputFilename(filePrefix: String) = "$resourcesPath$filePrefix$inputSubfix"

        private fun pairAnswersToKFunctions(
            answers: Map<Char, Any>,
            paddedDayNumber: String
        ): List<Pair<Any, KFunction1<*, *>>> {
            val clazz = locateClass(paddedDayNumber)

            return answers.map { (letter, result) ->
                val functionName = "$solverString$paddedDayNumber$letter"
                val method = clazz.declaredMethods.first { it.name == functionName }
                val kFunction = method.kotlinFunction!! as KFunction1<*, *>
                result to kFunction
            }
        }

        private fun locateClass(paddedDayNumber: String): Class<*> {
            // Start reflection on corresponding Java class because we can't reflect on top-level functions with Kotlin.
            // If not found on root, check package for corresponding day: for example package day09.
            return try {
                Class.forName("${classPathPrefix}Day${paddedDayNumber}Kt")
            } catch (e: ClassNotFoundException) {
                Class.forName("${classPathPrefix}day${paddedDayNumber}.Day${paddedDayNumber}Kt")
            }
        }
    }
}
