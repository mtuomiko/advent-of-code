package com.mtuomiko.day15

import com.mtuomiko.day14.solver14a
import com.mtuomiko.day14.solver14b
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.StringReader

class Day15Test {
    private val testInput = """
        |Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        |Sensor at x=9, y=16: closest beacon is at x=10, y=16
        |Sensor at x=13, y=2: closest beacon is at x=15, y=3
        |Sensor at x=12, y=14: closest beacon is at x=10, y=16
        |Sensor at x=10, y=20: closest beacon is at x=10, y=16
        |Sensor at x=14, y=17: closest beacon is at x=10, y=16
        |Sensor at x=8, y=7: closest beacon is at x=2, y=10
        |Sensor at x=2, y=0: closest beacon is at x=2, y=10
        |Sensor at x=0, y=11: closest beacon is at x=2, y=10
        |Sensor at x=20, y=14: closest beacon is at x=25, y=17
        |Sensor at x=17, y=20: closest beacon is at x=21, y=22
        |Sensor at x=16, y=7: closest beacon is at x=15, y=3
        |Sensor at x=14, y=3: closest beacon is at x=15, y=3
        |Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimMargin()

    @Test
    fun `Part A solver returns correct answer for test input`() {
        val reader = StringReader(testInput)

        val result = solver15a(reader, 10)

        Assertions.assertThat(result).isEqualTo(26)
    }

    @Test
    fun `Part B solver returns correct answer for test input`() {
        val reader = StringReader(testInput)

        val result = solver15b(reader, 20)

        Assertions.assertThat(result).isEqualTo(56000011L)
    }
}
