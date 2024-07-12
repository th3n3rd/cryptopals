package com.example.cryptopals

import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ScoreByLetterFrequenciesTests {

    @Test
    fun `the score for an empty string is infinity`() {
        val result = Score.ByLetterFrequencies(Bytes.of(""))

        result shouldBe Double.POSITIVE_INFINITY
    }

    @Test
    fun `the score for a string with no letters is also infinity`() {
        val result = Score.ByLetterFrequencies(Bytes.of("(>.<)"))

        result shouldBe Double.POSITIVE_INFINITY
    }

    @Test
    fun `scoring is not case sensitive`() {
        val first = Score.ByLetterFrequencies(Bytes.of("Az"))
        val second = Score.ByLetterFrequencies(Bytes.of("aZ"))

        first shouldBeEqual second
    }

    @Test
    fun `non-printable characters (excluding new liens) are penalised with a factor of 20`() {
        val twoPenalties = Score.ByLetterFrequencies(Bytes.of("Hello world\t\n\t"))
        val onePenalty = Score.ByLetterFrequencies(Bytes.of("Hello world\t\n"))
        val noPenalty = Score.ByLetterFrequencies(Bytes.of("Hello world"))

        twoPenalties shouldBe onePenalty + 20
        twoPenalties shouldBe noPenalty + 40
    }

    @Test
    fun `printable characters that are symbols are penalised with a factor of 10`() {
        val twoPenalties = Score.ByLetterFrequencies(Bytes.of("Hello world!!"))
        val onePenalty = Score.ByLetterFrequencies(Bytes.of("Hello world!"))
        val noPenalty = Score.ByLetterFrequencies(Bytes.of("Hello world"))

        twoPenalties shouldBe onePenalty + 10
        twoPenalties shouldBe noPenalty + 20
    }
}