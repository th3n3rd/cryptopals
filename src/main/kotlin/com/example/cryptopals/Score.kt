package com.example.cryptopals

object Score {
    object ByLetterFrequencies {
        private val expectedFrequencies = mapOf(
            'a' to 8.167, 'b' to 1.492, 'c' to 2.782, 'd' to 4.253, 'e' to 12.702,
            'f' to 2.228, 'g' to 2.015, 'h' to 6.094, 'i' to 6.966, 'j' to 0.153,
            'k' to 0.772, 'l' to 4.025, 'm' to 2.406, 'n' to 6.749, 'o' to 7.507,
            'p' to 1.929, 'q' to 0.095, 'r' to 5.987, 's' to 6.327, 't' to 9.056,
            'u' to 2.758, 'v' to 0.978, 'w' to 2.360, 'x' to 0.150, 'y' to 1.974,
            'z' to 0.074
        )

        operator fun invoke(plaintext: Bytes): Double {
            return chiSquareTest(letterFrequencies(plaintext)) + penalties(plaintext)
        }

        private fun penalties(plaintext: Bytes): Double {
            val nonPrintableCharacter = plaintext
                .toString()
                .count { !it.isPrintable() && !it.isNewLine() }
                .toDouble() * 20

            val printableButSymbol = plaintext
                .toString()
                .count { it.isPrintableSymbol() }
                .toDouble() * 10

            return nonPrintableCharacter + printableButSymbol
        }

        private fun letterFrequencies(plaintext: Bytes): Map<Char, Double> {
            val content = plaintext.toString()
            val letters = content.replace("[^a-zA-Z]+".toRegex(), "")
            return letters
                .groupingBy { it.lowercaseChar() }
                .eachCount()
                .mapValues { (it.value.toDouble() / letters.length) * 100.0 }
        }

        private fun chiSquareTest(observedFrequencies: Map<Char, Double>): Double {
            if (observedFrequencies.isEmpty()) {
                return Double.POSITIVE_INFINITY
            }
            return expectedFrequencies.entries.sumOf { (key, expected) ->
                val observed = observedFrequencies[key] ?: 0.0
                val diff = observed - expected
                diff * diff / expected
            }
        }
    }
}

private fun Char.isPrintable() = code in 32..126
private fun Char.isNewLine() = code == 10 || code == 13
private fun Char.isPrintableSymbol() = isPrintable() && !isLetterOrDigit() && !isWhitespace()

data class ScoredByteKey(val key: Byte, val score: Double)
data class ScoredRepeatedKey(val key: Bytes, val score: Double)
data class ScoredKeyLength(val keySize: Int, val score: Double)
data class ScoredPlaintext(val plaintext: Bytes, val score: Double)