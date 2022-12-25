package com.mtuomiko.day13

import java.io.Reader

fun solver13a(input: Reader): Int {
    input.buffered().lineSequence().chunked(3).mapIndexed { index, chunk ->
        val leftString = chunk[0]
        val rightString = chunk[1]


    }
}

fun solver13b(input: Reader): Int {

}
