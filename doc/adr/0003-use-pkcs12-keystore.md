# ADR-0003: Use PKCS12 Keystore Format

**Status:** Accepted | **Date:** 2025-12-13 | Developer: Justin Guida

## Context

Artemis Financial's SSL/TLS implementation requires secure storage for:

- Private keys for server authentication
- Self-signed certificates (development) / CA-signed certificates (production)
- Symmetric keys for data encryption

The keystore format must be:

- Secure and industry-standard
- Compatible with Java's KeyStore API
- Portable across different systems and tools

## Decision

Use **PKCS12** (.p12) format for keystore storage.

Generated using Java Keytool:
```bash
keytool -genkeypair -alias selfsigned -keyalg RSA -keysize 2048 \
  -storetype PKCS12 -keystore keystore.p12 -validity 365 \
  -dname "CN=localhost, OU=Development, O=Artemis Financial, L=City, ST=State, C=US"
```

Configuration in `application.properties`:
```properties
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-type=PKCS12
```

## Alternatives Considered

| Format              | Status | Reason Not Chosen                                                |
|---------------------|--------|------------------------------------------------------------------|
| JKS (Java KeyStore) | Legacy | Proprietary, weaker encryption, deprecated as default in Java 9+ |
| JCEKS               | Legacy | Proprietary Java format                                          |
| BKS (Bouncy Castle) | Secure | Requires external dependency                                     |
| PEM files           | Secure | Not natively supported by Java KeyStore API                      |

## Consequences

### Positive

- **Industry Standard**: PKCS#12 is defined in RFC 7292, widely supported
- **Default in Java 9+**: Replaced JKS as the default keystore type
- **Stronger Encryption**: Uses better encryption than legacy JKS
- **Portability**: Can be used with OpenSSL, browsers, and other tools
- **Single File**: Contains both private key and certificate chain

### Negative

- Password protects the entire keystore (cannot have different passwords per key in standard usage)
- Binary format (not human-readable like PEM)

### Risks

- **Password Security**: Keystore password must not be hardcoded in production
  - Development: Password in application.properties (acceptable)
  - Production: Use environment variables or secrets management (HashiCorp Vault, AWS Secrets Manager)

## Security Requirements

1. Use strong keystore password (12+ characters, mixed case, numbers, symbols)
2. Store keystore in `src/main/resources/` (classpath) for Spring Boot
3. Add `*.p12` to `.gitignore` to prevent accidental commits
4. In production, inject password via environment variable:
   ```properties
   server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}
   ```

## Compliance

- RFC 7292: PKCS #12 Personal Information Exchange Syntax
- Java 9+ default keystore format
- Compatible with FIPS 140-2 validated cryptographic modules

## Related Files

- [`src/main/resources/keystore.p12`](../../src/main/resources/keystore.p12) - PKCS12 keystore (generated)
- [`src/main/resources/application.properties`](../../src/main/resources/application.properties) - SSL configuration