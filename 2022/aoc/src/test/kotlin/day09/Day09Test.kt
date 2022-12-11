package com.mtuomiko.day09

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.StringReader

private class Day09Test {
    private val smallInput = """
        |R 4
        |U 4
        |L 3
        |D 1
        |R 4
        |D 1
        |L 5
        |R 2
        """.trimMargin()
    private val mediumInput = """
        |R 5
        |U 8
        |L 8
        |D 3
        |R 17
        |D 10
        |L 25
        |U 20
        """.trimIndent()

    @Test
    fun `Part A solver works for small input`() {
        val reader = StringReader(smallInput)

        val result = solver09a(reader)

        assertThat(result).isEqualTo(13)
    }

    @Test
    fun `Part B solver works for small input`() {
        val reader = StringReader(smallInput)

        val result = solver09b(reader)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `Part B solver works for medium input`() {
        val reader = StringReader(mediumInput)

        val result = solver09b(reader)

        assertThat(result).isEqualTo(36)
    }
}
