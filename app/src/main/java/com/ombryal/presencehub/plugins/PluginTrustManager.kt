package com.ombryal.presencehub.plugins

import com.ombryal.presencehub.security.ChecksumValidator
import com.ombryal.presencehub.security.PluginSecurityPolicy
import com.ombryal.presencehub.security.SignatureVerifier

class PluginTrustManager {

    fun canInstall(entry: PluginRegistryEntry): Boolean {
        return PluginSecurityPolicy.isPluginEligible(entry)
    }

    fun verifyDownloadedPlugin(
        pluginBytes: ByteArray,
        entry: PluginRegistryEntry,
        publicKeyPem: String
    ): Boolean {
        val hasChecksum = !entry.checksumSha256.isNullOrBlank()
        val hasSignature = !entry.signature.isNullOrBlank()
        val hasPublicKey = publicKeyPem.isNotBlank()

        if (!hasChecksum && !hasSignature) {
            return false
        }

        if (hasChecksum && !ChecksumValidator.matches(pluginBytes, entry.checksumSha256!!)) {
            return false
        }

        if (hasSignature) {
            if (!hasPublicKey) {
                return false
            }

            return SignatureVerifier.verifySignature(
                pluginData = pluginBytes,
                signature = entry.signature!!.toByteArray(),
                publicKeyPem = publicKeyPem
            )
        }

        return true
    }
}
