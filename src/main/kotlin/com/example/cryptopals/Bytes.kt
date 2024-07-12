package com.example.cryptopals

import java.util.*
import kotlin.experimental.xor

data class Bytes(val content: ByteArray) {

    fun toBase64() = Bytes(Base64.getEncoder().encode(content))

    fun xor(other: Bytes): Bytes {
        return Bytes(
            content
                .zip(other.content)
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

        fun of(content: String) = Bytes(content.toByteArray())
    }
}