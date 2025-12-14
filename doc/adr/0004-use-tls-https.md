# ADR-0004: Use TLS 1.3/1.2 for HTTPS Communications

## Status

Accepted

## Date

2025-12-13

## Context

Artemis Financial has a public web interface handling sensitive financial data. All communications between clients and servers must be encrypted to:

- Protect data in transit from eavesdropping
- Prevent man-in-the-middle attacks
- Meet regulatory compliance requirements
- Establish trust with customers

## Decision

Use **HTTPS with TLS 1.3** (preferred) or **TLS 1.2** (minimum) for all web communications.

Configuration in `application.properties`:
```properties
server.port=8443
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-type=PKCS12
```

Recommended cipher suites (TLS 1.3):
- `TLS_AES_256_GCM_SHA384`
- `TLS_AES_128_GCM_SHA256`
- `TLS_CHACHA20_POLY1305_SHA256`

Recommended cipher suites (TLS 1.2):
- `TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384`
- `TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256`

## Alternatives Considered

| Protocol | Status | Reason Not Chosen |
|----------|--------|-------------------|
| HTTP | Insecure | No encryption, data sent in plaintext |
| SSL 2.0 | Broken | Multiple critical vulnerabilities |
| SSL 3.0 | Broken | POODLE attack (CVE-2014-3566) |
| TLS 1.0 | Deprecated | BEAST attack, PCI DSS non-compliant since 2018 |
| TLS 1.1 | Deprecated | Weak cipher suites, deprecated by major browsers |

## Consequences

### Positive

- **Data Confidentiality**: All traffic encrypted with AES-GCM
- **Data Integrity**: TLS provides message authentication
- **Server Authentication**: Certificate verifies server identity
- **Forward Secrecy**: ECDHE key exchange protects past sessions if key compromised
- **Regulatory Compliance**: Meets PCI DSS, HIPAA, GLBA requirements
- **Browser Trust**: Modern browsers require HTTPS for sensitive features

### Negative

- **Certificate Management**: Requires valid certificates (self-signed for dev, CA-signed for prod)
- **Performance**: Slight overhead for TLS handshake (mitigated by TLS 1.3 improvements)
- **Self-Signed Warning**: Browsers show security warning for self-signed certs

### Risks

- **Certificate Expiry**: Must monitor and renew certificates before expiration
- **Weak Cipher Configuration**: Must disable legacy ciphers (addressed by using defaults)

## TLS 1.3 vs TLS 1.2

| Feature | TLS 1.2 | TLS 1.3 |
|---------|---------|---------|
| Handshake Round Trips | 2 | 1 |
| 0-RTT Resumption | No | Yes |
| Weak Cipher Support | Some | Removed |
| Forward Secrecy | Optional | Required |
| NIST Deadline | Current | Required by Jan 2024 |

## Production Recommendations

1. **Use CA-Signed Certificates**: Let's Encrypt (free) or commercial CA
2. **Enable HSTS**: Strict-Transport-Security header
3. **Disable HTTP**: Redirect all HTTP to HTTPS
4. **Certificate Transparency**: Use certificates logged to CT logs

## Compliance

- PCI DSS 3.2.1: TLS 1.2 minimum required since June 2018
- NIST SP 800-52 Rev 2: TLS 1.3 recommended, TLS 1.2 acceptable
- NIST: Organizations required to add TLS 1.3 support by January 2024
- HIPAA: Requires encryption for PHI in transit