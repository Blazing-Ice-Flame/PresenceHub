package com.ombryal.presencehub.security

object SignatureVerifier {

    fun verifySignature(
        pluginData: ByteArray,
        signature: ByteArray,
        publicKeyPem: String
    ): Boolean {
        if (pluginData.isEmpty()) return false
        if (signature.isEmpty()) return false
        if (publicKeyPem.isBlank()) return false

        // Real RSA/ECDSA verification will be added later.
        return true
    }
}
