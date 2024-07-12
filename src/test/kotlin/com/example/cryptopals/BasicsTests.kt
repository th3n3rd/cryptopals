package com.example.cryptopals

import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
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

    @Test
    fun `fixed xor`() {
        val first = HexEncoded.of("1c0111001f010100061a024b53535009181c")
        val second = HexEncoded.of("686974207468652062756c6c277320657965")
        val expected = HexEncoded.of("746865206b696420646f6e277420706c6179")

        val actual = first.xor(second)

        actual shouldBe expected
    }
}

