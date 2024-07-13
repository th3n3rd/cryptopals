package com.example.cryptopals

import java.util.*
import kotlin.experimental.xor

data class Bytes(val content: ByteArray) {

    fun xor(other: Bytes): Bytes {
        val maxLength = maxOf(content.size, other.content.size)

        val extended = extendUpTo(maxLength)
        val otherExtended = other.extendUpTo(maxLength)

        return Bytes(
            extended.content
                .zip(otherExtended.content)
                .map { (first, second) -> first.xor(second) }
                .toByteArray()
        )
    }

    fun xor(byte: Byte): Bytes {
        return xor(
            Bytes(
                ByteArray(content.size) {
                    byte
                }
            )
        )
    }

    fun extendUpTo(length: Int): Bytes {
        return Bytes(
            ByteArray(length) {
                content[it % content.size]
            }
        )
    }

    fun toBase64() = Bytes(
        Base64.getEncoder().encode(content)
    )

    fun toHex() = Bytes(
        content.joinToString("")
            .chunked(2)
            .map { it.toByte() }
            .toByteArray()
    )

    override fun toString(): String {
        return content
            .map { it.toInt().toChar() }
            .joinToString("")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bytes

        return content.contentEquals(other.content)
    }

    override fun hashCode(): Int {
        return content.contentHashCode()
    }

    companion object {
        fun fromHex(encoded: String) = Bytes(
            encoded
                .chunked(2)
                .map { it.toInt(16).toByte() }
                .toByteArray()
        )

        fun fromHexMultiline(encoded: String) = fromHex(
            encoded.replace("\n", "")
        )

        fun of(content: String) = Bytes(content.toByteArray())
    }
}