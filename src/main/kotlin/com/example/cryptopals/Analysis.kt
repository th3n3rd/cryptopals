package com.example.cryptopals

object Analysis {
    object SingleByteXor {
        fun deriveKey(encrypted: Bytes): Byte {
            return (0..255).asSequence()
                .map { it.toByte() }
                .map {
                    ScoredKey(
                        key = it,
                        score = Score.ByLetterFrequencies(encrypted.xor(it))
                    )
                }
                .sortedBy { it.score }
                .first()
                .key
        }

        fun detect(encrypted: List<Bytes>): Bytes {
            return encrypted
                .asSequence()
                .map { it.xor(deriveKey(it)) }
                .map {
                    ScoredPlaintext(
                        plaintext = it,
                        score = Score.ByLetterFrequencies(it)
                    )
                }
                .sortedBy { it.score }
                .map { it.plaintext }
                .first()
        }

        data class ScoredPlaintext(val plaintext: Bytes, val score: Double)
        data class ScoredKey(val key: Byte, val score: Double)
    }
}