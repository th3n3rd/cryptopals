package com.example.cryptopals

import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test

class BytesTests {

    @Test
    fun `extendable up to a given length`() {
        val original = Bytes.of("xy")
        val expected = Bytes.of("xyxyxy")

        val actual = original.extendUpTo(6)

        actual shouldBeEqual expected
    }

    @Test
    fun `compute the distance to another set of bytes, ie Hamming distance`() {
        val expected = 37

        val actual = Bytes.of("this is a test") distanceTo Bytes.of("wokka wokka!!!")

        actual shouldBeEqual expected
    }
}