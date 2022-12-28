package com.mtuomiko.day13

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.StringReader

class Day13Test {
    private val testInput = """
    |[1,1,3,1,1]
    |[1,1,5,1,1]
    |
    |[[1],[2,3,4]]
    |[[1],4]
    |
    |[9]
    |[[8,7,6]]
    |
    |[[4,4],4,4]
    |[[4,4],4,4,4]
    |
    |[7,7,7,7]
    |[7,7,7]
    |
    |[]
    |[3]
    |
    |[[[]]]
    |[[]]
    |
    |[1,[2,[3,[4,[5,6,7]]]],8,9]
    |[1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimMargin()

    @Test
    fun `Part A solver returns correct answer for test input`() {
        val reader = StringReader(testInput)

        val result = solver13a(reader)

        Assertions.assertThat(result).isEqualTo(13)
    }

    @Test
    fun `Part B solver returns correct answer for test input`() {
        val reader = StringReader(testInput)

        val result = solver13b(reader)

        Assertions.assertThat(result).isEqualTo(140)
    }
}
