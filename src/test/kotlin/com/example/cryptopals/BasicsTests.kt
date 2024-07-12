package com.example.cryptopals

import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test

class BasicsTests {

    @Test
    fun `convert hex to base64`() {
        val input = HexEncoded.of(
            "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
        )
        val expected = Base64Encoded.of(
            "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
        )

        val actual = input.toBase64()

        actual shouldBeEqual expected
    }

}

