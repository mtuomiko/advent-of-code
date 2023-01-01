package com.mtuomiko.day14

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.StringReader

class Day14Test {
    private val testInput = """
    |498,4 -> 498,6 -> 496,6
    |503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimMargin()

    @Test
    fun `Part A solver returns correct answer for test input`() {
        val reader = StringReader(testInput)

        val result = solver14a(reader)

        Assertions.assertThat(result).isEqualTo(24)
    }

    @Test
    fun `Part B solver returns correct answer for test input`() {
        val reader = StringReader(testInput)

        val result = solver14b(reader)

        Assertions.assertThat(result).isEqualTo(93)
    }
}
