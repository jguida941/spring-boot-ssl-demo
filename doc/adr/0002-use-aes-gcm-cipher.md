# ADR-0002: Use AES/GCM/NoPadding for Encryption

## Status

Accepted

## Date

2025-12-13

## Context

Artemis Financial handles sensitive financial data including savings, retirement, investments, and insurance information. The application requires:

- Strong encryption for data at rest and in transit
- Authenticated encryption to prevent tampering
- Compliance with financial industry regulations
- Standard Java support without external dependencies

## Decision

Use **AES/GCM/NoPadding** as the primary cipher transformation for symmetric encryption.

- **AES** (Advanced Encryption Standard): 128/256-bit block cipher
- **GCM** (Galois/Counter Mode): Authenticated encryption mode
- **NoPadding**: GCM is a stream mode; no padding required

Implementation:
```java
Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
```

## Alternatives Considered

| Cipher | Mode | Status | Reason Not Chosen |
|--------|------|--------|-------------------|
| DES | Any | Deprecated | 56-bit key is insecure |
| 3DES/DESede | Any | Legacy | Slow, being phased out |
| AES | CBC | Secure | No built-in authentication |
| AES | CTR | Secure | No built-in authentication |
| AES | CCM | Secure | Less performant than GCM |
| Blowfish | Any | Legacy | Non-standard key sizes |
| RC4 | Stream | Broken | Multiple vulnerabilities |

## Consequences

### Positive

- AEAD (Authenticated Encryption with Associated Data) - provides confidentiality AND integrity
- NIST approved and widely adopted
- Required in Java SE 9+ (guaranteed availability)
- Excellent performance with hardware acceleration (AES-NI)
- Single algorithm handles both encryption and authentication
- Meets PCI DSS, HIPAA, and GLBA requirements

### Negative

- GCM requires unique IV/nonce for each encryption with same key
- IV reuse completely breaks GCM security
- Maximum message size of ~64GB per key/IV pair

### Risks

- **IV Reuse**: Must ensure unique 12-byte IV for every encryption operation
- **Key Management**: Keys must be stored securely in PKCS12 keystore (see ADR-0003)

## Implementation Requirements

1. Generate 256-bit AES keys using `KeyGenerator.getInstance("AES")`
2. Use `SecureRandom` (DRBG) for 12-byte IV generation
3. Never reuse IV with same key
4. Store authentication tag with ciphertext

## Compliance

- NIST SP 800-38D: GCM is an approved mode of operation
- FIPS 197: AES is the approved symmetric cipher
- PCI DSS: Meets strong cryptography requirements
- NIST required TLS 1.3 support by January 2024