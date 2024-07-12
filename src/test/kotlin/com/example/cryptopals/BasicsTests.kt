package com.example.cryptopals

import com.example.cryptopals.Analysis.SingleByteXor
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path

class BasicsTests {

    @Test
    fun `convert hex to base64`() {
        val input = Bytes.fromHex("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d")
        val expected = Bytes.of("SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t")

        val actual = input.toBase64()

        actual shouldBeEqual expected
    }

    @Test
    fun `fixed xor`() {
        val first = Bytes.fromHex("1c0111001f010100061a024b53535009181c")
        val second = Bytes.fromHex("686974207468652062756c6c277320657965")
        val expected = Bytes.fromHex("746865206b696420646f6e277420706c6179")

        val actual = first.xor(second)

        actual shouldBe expected
    }

    @Test
    fun `single-byte xor cipher`() {
        val input = Bytes.fromHex("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736")
        val expected = Bytes.of("Cooking MC's like a pound of bacon")

        val key = SingleByteXor.deriveKey(input)

        input.xor(key) shouldBe expected
    }

    @Test
    fun `detect single-character xor`() {
        val inputs = Files.readString(Path.of("src/test/resources/detect-single-character-xor.txt"))
            .split("\n")
            .map { Bytes.fromHex(it) }
        val expected = Bytes.of("Now that the party is jumping\n")

        val actual = SingleByteXor.detect(inputs)

        actual shouldBe expected
    }

        result shouldBe expected
    }
}
