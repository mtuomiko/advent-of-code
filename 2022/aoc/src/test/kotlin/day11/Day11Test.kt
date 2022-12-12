package com.mtuomiko.day11

import com.mtuomiko.day10.solver10a
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.StringReader

private class Day11Test {
    private val testInput = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
    """.trimMargin()

    @Test
    fun `Part A solver returns correct answer for test input`() {
        val reader = StringReader(testInput)

        val result = solver11a(reader)

        Assertions.assertThat(result).isEqualTo(10605L)
    }
}
