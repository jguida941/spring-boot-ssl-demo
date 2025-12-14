# ADR-0005: Known Limitations - Not Production Ready

**Status:** Accepted | **Date:** 2025-12-14 | Developer: Justin Guida

## Context

This project was developed as an educational assignment for CS-305 (Software Security) at SNHU. The goal was to demonstrate understanding of:

- SSL/TLS certificate generation and configuration
- Cryptographic hash algorithms (SHA-256)
- HTTPS secure communications
- Dependency vulnerability scanning

As an educational project, certain simplifications were made that would be unacceptable in a production environment. This ADR documents all known limitations for transparency and to demonstrate security awareness.

## Decision

We document all security limitations openly rather than hiding them. This demonstrates that we understand what would need to change for production deployment.

## Known Limitations

### Critical Severity

| Issue                           | Location                          | Risk                                                     | Production Fix                                    |
|---------------------------------|-----------------------------------|----------------------------------------------------------|---------------------------------------------------|
| **Hardcoded keystore password** | `application.properties:14`       | Password exposed in source code                          | Use environment variables: `${KEYSTORE_PASSWORD}` |
| **Keystore in repository**      | `src/main/resources/keystore.p12` | Private key accessible to anyone with repo access        | Generate at deployment, store in secrets manager  |
| **Outdated Spring Boot 2.2.4**  | `pom.xml:8`                       | 162+ CVEs in dependencies (including Spring4Shell)       | Upgrade to Spring Boot 3.x                        |
| **Java 8 bytecode target**      | `pom.xml:18`                      | Older language features, though runtime JVM may be newer | Target Java 17+ for LTS support                   |

### High Severity

| Issue                              | Location                       | Risk                                                                | Production Fix                                  |
|------------------------------------|--------------------------------|---------------------------------------------------------------------|-------------------------------------------------|
| **Self-signed certificate**        | `keystore.p12`                 | Cannot be verified by clients, MITM vulnerable                      | Use CA-signed certificate (Let's Encrypt, etc.) |
| **No Spring Security**             | Missing dependency             | No authentication, authorization, CSRF protection, security headers | Add `spring-boot-starter-security`              |
| **No security headers**            | No configuration               | XSS, clickjacking, MIME sniffing vulnerabilities                    | Configure HSTS, CSP, X-Frame-Options, etc.      |
| **HTML response without escaping** | `ServerApplication.java:36-38` | Potential XSS if data becomes user-controlled                       | Use proper templating or return JSON            |

### Medium Severity

| Issue                               | Location                 | Risk                                           | Production Fix                         |
|-------------------------------------|--------------------------|------------------------------------------------|----------------------------------------|
| **No HTTPS redirect**               | Missing config           | HTTP requests could succeed on port 8080       | Force HTTPS redirect or disable HTTP   |
| **No rate limiting**                | No implementation        | DoS vulnerability                              | Implement rate limiting middleware     |
| **No input validation**             | `ServerApplication.java` | Currently hardcoded, but pattern is vulnerable | Add validation if accepting user input |
| **Weak dependency-check threshold** | `pom.xml:60`             | `failBuildOnCVSS>11` never fails (max is 10.0) | Set to 7.0 or lower                    |

### Low Severity

| Issue                     | Location                         | Risk                         | Production Fix                      |
|---------------------------|----------------------------------|------------------------------|-------------------------------------|
| **Minimal test coverage** | `SslServerApplicationTests.java` | Only context load test       | Add security-focused tests          |
| **No authentication**     | No implementation                | Anyone can access endpoints  | Implement OAuth2/JWT authentication |
| **No audit logging**      | No implementation                | Cannot trace security events | Add structured security logging     |

## Production Readiness Roadmap

To make this application production-ready, the following changes would be required:

### Phase 1: Critical Security Fixes
1. Remove hardcoded credentials, use environment variables
2. Remove keystore from repository, generate at deployment
3. Upgrade to Spring Boot 3.x (latest LTS)
4. Target Java 17+ (current LTS)

### Phase 2: Security Hardening
1. Add Spring Security with proper configuration
2. Implement authentication (OAuth2/JWT)
3. Add security headers (HSTS, CSP, X-Frame-Options)
4. Configure HTTPS-only with HTTP redirect
5. Use CA-signed certificate

### Phase 3: Operational Security
1. Add comprehensive logging and audit trails
2. Implement rate limiting
3. Add input validation
4. Configure proper error handling (no stack traces)
5. Set up monitoring and alerting

### Phase 4: Testing & Compliance
1. Add security-focused unit tests
2. Add integration tests
3. Configure dependency-check to fail on CVSS >= 7.0
4. Document compliance with relevant standards

## Consequences

### Positive
- Transparent about limitations
- Demonstrates security awareness and knowledge
- Provides clear roadmap for production hardening
- Educational value for understanding security gaps

### Negative
- Application is NOT safe for production use as-is
- Could be misused if deployed without reading documentation

### Neutral
- Planned `hardened` branch will address these issues
- This serves as a baseline for demonstrating security improvements

## References

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [NIST SP 800-52 Rev 2 - TLS Guidelines](https://csrc.nist.gov/publications/detail/sp/800-52/rev-2/final)

## Related Files

- [`src/main/resources/application.properties`](../../src/main/resources/application.properties) - Contains hardcoded password
- [`pom.xml`](../../pom.xml) - Outdated Spring Boot version
- [`doc/requirements/roadmap.md`](../requirements/roadmap.md) - Production hardening plan
