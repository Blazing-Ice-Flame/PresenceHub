# Security

Every plugin should be verified before loading.

Verification includes

- SHA-256 checksum
- API version compatibility
- signature validation

Plugins execute through the public plugin interface only.

Authentication tokens remain inside each plugin.
