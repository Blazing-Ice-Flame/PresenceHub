package com.ombryal.presencehub.security

import java.security.MessageDigest

object ChecksumValidator {

    fun sha256(input: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(input)
        return hash.joinToString("") { byte -> "%02x".format(byte) }
    }

    fun matches(input: ByteArray, expectedSha256: String): Boolean {
        return sha256(input).equals(expectedSha256, ignoreCase = true)
    }
}
