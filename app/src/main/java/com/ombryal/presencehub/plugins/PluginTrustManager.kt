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
        if (entry.checksumSha256.isNullOrBlank()) return false
        if (entry.signature.isNullOrBlank()) return false

        val checksumOk = ChecksumValidator.matches(pluginBytes, entry.checksumSha256)
        if (!checksumOk) return false

        val signatureOk = SignatureVerifier.verifySignature(
            pluginData = pluginBytes,
            signature = entry.signature.toByteArray(),
            publicKeyPem = publicKeyPem
        )

        return signatureOk
    }
}
