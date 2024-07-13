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
}