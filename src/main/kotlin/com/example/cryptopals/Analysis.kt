package com.example.cryptopals

object Analysis {
    object SingleByteXor {
        fun decrypt(encrypted: Bytes): String {
            return (0..255).asSequence()
                .map { encrypted.xor(it.toByte()) }
                .map { it.toString() }
                .map { Score.ByLetterFrequencies(it) }
                .sortedBy { it.score }
                .first()
                .candidate
        }
    }
}