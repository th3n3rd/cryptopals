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

    infix fun distanceTo(other: Bytes): Int = content.zip(other.content).sumOf {
        it.first.toInt().xor(it.second.toInt()).countOneBits()
    }

    fun chunked(size: Int) = content
        .asList()
        .chunked(size)
        .map { it.toByteArray() }
        .map { Bytes(it) }

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

        fun fromBase64(encoded: String) = Bytes(
            Base64.getDecoder().decode(encoded)
        )

        fun fromBase64Multiline(encoded: String) = fromBase64(
            encoded.replace("\n", "")
        )

        fun of(content: String) = Bytes(content.toByteArray())
    }
}