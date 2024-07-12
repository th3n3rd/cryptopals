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

        data class ScoredKey(val key: Byte, val score: Double)
    }
}