package com.example.cryptopals

import java.util.*

data class HexEncoded(val bytes: ByteArray) {

    fun toBase64() = Base64Encoded(
        Base64.getEncoder().encode(bytes)
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HexEncoded

        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }

    companion object {
        fun of(value: String): HexEncoded {
            return HexEncoded(
                value
                    .chunked(2)
                    .map { it.toInt(16).toByte() }
                    .toByteArray()
            )
        }
    }
}