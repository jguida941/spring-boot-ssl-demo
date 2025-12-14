# ADR-0001: Use SHA-256 for Checksum Verification

## Status

Accepted

## Date

2025-12-13

## Context

Artemis Financial requires a data verification step in the form of a checksum to ensure secure communications when transferring data through their web application. The system needs a cryptographic hash function that:

- Provides strong collision resistance
- Is widely supported and standardized
- Meets regulatory requirements (NIST, PCI DSS)
- Is available in standard Java without external dependencies

## Decision

Use **SHA-256** (Secure Hash Algorithm 256-bit) via Java's `MessageDigest` class for checksum verification.

Implementation:
```java
MessageDigest digest = MessageDigest.getInstance("SHA-256");
byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
```

## Alternatives Considered

| Algorithm | Bit Length | Status | Reason Not Chosen |
|-----------|------------|--------|-------------------|
| MD5 | 128-bit | Deprecated | Known collision vulnerabilities |
| SHA-1 | 160-bit | Deprecated | Collision attacks demonstrated in 2017 |
| SHA-384 | 384-bit | Secure | Overkill for this use case, slower |
| SHA-512 | 512-bit | Secure | Overkill for this use case, slower |
| SHA-3 | 256-bit | Secure | Less widely supported, newer |

## Consequences

### Positive

- 256-bit output provides strong collision resistance (2^128 operations to find collision)
- NIST approved and FIPS 180-4 compliant
- Required algorithm in Java SE (guaranteed availability)
- Meets PCI DSS "strong cryptography" requirements
- Fast performance for web application use

### Negative

- SHA-256 is not keyed; it provides integrity but not authentication (addressed by using within TLS)
- Output is fixed-length; not suitable for password hashing (not our use case)

### Risks

- None significant for this use case

## Compliance

- NIST SP 800-131A: SHA-256 is approved for all uses
- PCI DSS: Meets strong cryptography requirements
- FIPS 180-4: SHA-256 is a FIPS-approved hash function