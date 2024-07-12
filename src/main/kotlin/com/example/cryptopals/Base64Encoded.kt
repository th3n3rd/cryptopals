package com.example.cryptopals

data class Base64Encoded(val bytes: ByteArray) {
    companion object {
        fun of(value: String): Base64Encoded {
            return Base64Encoded(value.toByteArray())
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Base64Encoded

        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int {
        return bytes.contentHashCode()
    }
}