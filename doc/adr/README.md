# Architecture Decision Records

This directory contains Architecture Decision Records (ADRs) for the Artemis Financial Secure Software project.

## What is an ADR?

An Architectural Decision Record (ADR) captures a single architectural decision and its rationale. ADRs help teams:

- Document why decisions were made
- Onboard new team members quickly
- Avoid revisiting settled decisions
- Maintain consistency across the codebase

## ADR Index

| ADR | Title | Status | Date |
|-----|-------|--------|------|
| [0001](0001-use-sha256-for-checksum.md) | Use SHA-256 for Checksum Verification | Accepted | 2025-12-13 |
| [0002](0002-use-aes-gcm-cipher.md) | Use AES/GCM/NoPadding for Encryption | Accepted | 2025-12-13 |
| [0003](0003-use-pkcs12-keystore.md) | Use PKCS12 Keystore Format | Accepted | 2025-12-13 |
| [0004](0004-use-tls-https.md) | Use TLS 1.3/1.2 for HTTPS Communications | Accepted | 2025-12-13 |
| [0005](0005-known-limitations-not-production-ready.md) | Known Limitations - Not Production Ready | Accepted | 2025-12-14 |

## ADR Template

When creating new ADRs, use this template:

```markdown
# ADR-NNNN: Title

## Status
[Proposed | Accepted | Deprecated | Superseded]

## Date
YYYY-MM-DD

## Context
What is the issue that we're seeing that is motivating this decision?

## Decision
What is the change that we're proposing and/or doing?

## Alternatives Considered
What other options were evaluated?

## Consequences
What becomes easier or more difficult because of this decision?

## Compliance
What standards or regulations does this satisfy?
```

## References

- [ADR GitHub Organization](https://adr.github.io/)
- [Michael Nygard's ADR Article](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions)
- [AWS ADR Guidance](https://docs.aws.amazon.com/prescriptive-guidance/latest/architectural-decision-records/adr-process.html)