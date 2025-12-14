# Project Roadmap

This document outlines planned improvements to transform this educational demo into a production-ready application.

## Current State

**Version:** 0.1.0 (Educational Demo)

The `main` branch contains the original CS-305 assignment implementation with intentional security simplifications for educational purposes. See [ADR-0005](../adr/0005-known-limitations-not-production-ready.md) for documented limitations.

---

## Planned: `hardened` Branch

A production-ready branch addressing all known security limitations.

### Phase 1: Critical Security Fixes

| Task                                                       | Priority | Status  |
|------------------------------------------------------------|----------|---------|
| Remove hardcoded credentials from `application.properties` | Critical | Planned |
| Implement environment variable configuration               | Critical | Planned |
| Upgrade to Spring Boot 3.x LTS                             | Critical | Planned |
| Target Java 17+ (LTS)                                      | Critical | Planned |
| Remove keystore from repository                            | Critical | Planned |

### Phase 2: Security Hardening

| Task                                                    | Priority | Status  |
|---------------------------------------------------------|----------|---------|
| Add Spring Security dependency                          | High     | Planned |
| Implement HTTPS-only with HTTP redirect                 | High     | Planned |
| Configure security headers (HSTS, CSP, X-Frame-Options) | High     | Planned |
| Add CSRF protection                                     | High     | Planned |
| Support CA-signed certificates                          | High     | Planned |

### Phase 3: Authentication & Authorization

| Task                              | Priority | Status  |
|-----------------------------------|----------|---------|
| Implement JWT authentication      | Medium   | Planned |
| Add role-based access control     | Medium   | Planned |
| Configure OAuth2 support          | Medium   | Planned |
| Add API key authentication option | Medium   | Planned |

### Phase 4: Operational Security

| Task                                              | Priority | Status  |
|---------------------------------------------------|----------|---------|
| Add structured logging (SLF4J + Logback)          | Medium   | Planned |
| Implement audit trail for security events         | Medium   | Planned |
| Add rate limiting                                 | Medium   | Planned |
| Configure proper error handling (no stack traces) | Medium   | Planned |
| Add health check endpoints                        | Low      | Planned |

### Phase 5: Testing & Quality

| Task                                              | Priority | Status  |
|---------------------------------------------------|----------|---------|
| Add unit tests for security components            | Medium   | Planned |
| Add integration tests                             | Medium   | Planned |
| Configure dependency-check to fail on CVSS >= 7.0 | Medium   | Planned |
| Add static code analysis (SpotBugs, PMD)          | Low      | Planned |
| Achieve 80%+ code coverage                        | Low      | Planned |

### Phase 6: Documentation & Deployment

| Task                                    | Priority | Status  |
|-----------------------------------------|----------|---------|
| Add Docker support                      | Low      | Planned |
| Create Kubernetes manifests             | Low      | Planned |
| Document deployment procedures          | Low      | Planned |
| Add API documentation (OpenAPI/Swagger) | Low      | Planned |

---

## Version History

| Version | Branch     | Description                        |
|---------|------------|------------------------------------|
| 0.1.0   | `main`     | Initial educational demo (CS-305)  |
| 1.0.0   | `hardened` | Production-ready release (planned) |

---

## Contributing

This project is primarily for educational purposes. However, contributions to the `hardened` branch are welcome:

1. Fork the repository
2. Create a feature branch from `hardened`
3. Submit a pull request with a clear description

Please ensure all security best practices are followed in contributions.
