package com.example.cryptopals

object Analysis {
    object SingleByteXor {
        fun decrypt(encrypted: HexEncoded): String {
            return (0..255).asSequence()
                .map { it.toChar() }
                .map { encrypted.xor(it) }
                .map { it.decode() }
                .map { Score.ByLetterFrequencies(it) }
                .sortedBy { it.score }
                .first()
                .candidate
        }
    }
}