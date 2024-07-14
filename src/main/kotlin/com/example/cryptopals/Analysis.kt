package com.example.cryptopals

object Analysis {
    object SingleByteXor {
        fun deriveKey(encrypted: Bytes): Byte {
            return (0..255).asSequence()
                .map { it.toByte() }
                .map {
                    ScoredByteKey(
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

    }

    object RepeatingKeyXor {
        fun encrypt(plaintext: Bytes, key: Bytes): Bytes {
            return plaintext.xor(key)
        }

        fun decrypt(plaintext: Bytes): Bytes {
            val key = deriveKey(plaintext)
            return plaintext.xor(key)
        }

        private fun deriveKey(encrypted: Bytes): Bytes {
            val maxKeySize = 40
            return (2..maxKeySize)
                .asSequence()
                .map { keySize ->
                    val blocks = encrypted.chunked(keySize)
                    val pairs = blocks.zipWithNext()
                    val normalisedDistance = pairs
                        .map { (first, second) -> first distanceTo second }
                        .average() // mitigate outliers/noise
                        .div(keySize) // normalise to have the average distance per byte (not absolute), so we can compare distances regardless of the chunk size, else higher chunk sizes will likely generate higher distances and be penalised
                    ScoredKeyLength(keySize = keySize, score = normalisedDistance)
                }
                .sortedBy { it.score }
                .take(5)
                .map { it.keySize }
                .map { keySize ->
                    // TODO: use the Bytes class instead of low-level byte array
                    val blocks = encrypted.chunked(keySize).map {
                        it.content
                    }

                    val transposed = List(keySize) {
                        ByteArray(blocks.size)
                    }

                    for (i in blocks.indices) {
                        for (j in blocks[i].indices) {
                            transposed[j][i] = blocks[i][j]
                        }
                    }

                    val key = Bytes(
                        transposed
                            .map { Bytes(it) }
                            .map { SingleByteXor.deriveKey(it) }
                            .toByteArray()
                    ).extendUpTo(encrypted.content.size)

                    ScoredRepeatedKey(
                        key = key,
                        score = Score.ByLetterFrequencies(
                            encrypted.xor(key)
                        )
                    )
                }
                .sortedBy { it.score }
                .map { it.key }
                .first()
        }
    }

}

data class ScoredByteKey(val key: Byte, val score: Double)
data class ScoredRepeatedKey(val key: Bytes, val score: Double)
data class ScoredKeyLength(val keySize: Int, val score: Double)
data class ScoredPlaintext(val plaintext: Bytes, val score: Double)
