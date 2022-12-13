package com.mtuomiko.day12

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.StringReader

private class Day12Test {
    private val testInput = """
        |Sabqponm
        |abcryxxl
        |accszExk
        |acctuvwj
        |abdefghi
    """.trimMargin()

    @Test
    fun `Part A solver returns correct answer for test input`() {
        val reader = StringReader(testInput)

        val result = solver12a(reader)

        Assertions.assertThat(result).isEqualTo(31)
    }

    @Test
    fun `Part B solver returns correct answer for test input`() {
        val reader = StringReader(testInput)

        val result = solver12b(reader)

        Assertions.assertThat(result).isEqualTo(29)
    }
}
