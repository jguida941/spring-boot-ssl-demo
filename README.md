# Spring Boot SSL Demo

A Spring Boot application demonstrating SSL/TLS secure communications and SHA-256 checksum verification.

---

> **⚠️ EDUCATIONAL PROJECT - NOT PRODUCTION READY ⚠️**
>
> This project was created for a **university software security course (CS-305)**. It contains **intentional security simplifications** for educational purposes:
>
> - **Hardcoded credentials** in configuration files
> - **Self-signed certificate** included in repository
> - **Outdated dependencies** with known vulnerabilities (162+ CVEs)
> - **No authentication or authorization**
>
> **DO NOT deploy this to production.** See [ADR-0005](doc/adr/0005-known-limitations-not-production-ready.md) for the complete list of limitations and a production readiness roadmap.
>
> A `hardened` branch with production-ready code is planned for future development.

---

## Overview

This application demonstrates secure software development concepts for Artemis Financial, a fictional financial consulting company. It implements:

- **HTTPS/TLS** encrypted communications on port 8443
- **SHA-256** cryptographic hash algorithm for data verification
- **PKCS12** keystore for certificate and private key storage
- **Self-signed certificate** for local development

## Quick Start

```bash
# Clone the repository
git clone https://github.com/jguida941/spring-boot-ssl-demo.git
cd spring-boot-ssl-demo

# Build and run
./mvnw spring-boot:run

# Access the application
# Open: https://localhost:8443/hash
```

See the [Quick Start Guide](doc/guides/quickstart.md) for detailed instructions and troubleshooting.

## Documentation

### Guides

| Document | Description |
|----------|-------------|
| [Quick Start](doc/guides/quickstart.md) | Get running in 5 minutes |
| [Implementation Instructions](doc/guides/implementation-instructions.md) | Step-by-step implementation guide |
| [Requirements](doc/guides/requirements.md) | Original assignment requirements (CS-305) |

### Architecture Decision Records (ADRs)

| ADR | Decision |
|-----|----------|
| [ADR-0001](doc/adr/0001-use-sha256-for-checksum.md) | Use SHA-256 for Checksum Verification |
| [ADR-0002](doc/adr/0002-use-aes-gcm-cipher.md) | Use AES/GCM/NoPadding for Encryption |
| [ADR-0003](doc/adr/0003-use-pkcs12-keystore.md) | Use PKCS12 Keystore Format |
| [ADR-0004](doc/adr/0004-use-tls-https.md) | Use TLS 1.3/1.2 for HTTPS Communications |
| [ADR-0005](doc/adr/0005-known-limitations-not-production-ready.md) | Known Limitations - Not Production Ready |

### Reports

| Document | Description |
|----------|-------------|
| [Secure Software Report](doc/reports/secure-software-report.md) | Security analysis and implementation documentation |

## Tech Stack

| Technology | Version | Notes |
|------------|---------|-------|
| Java | 8+ (17+ recommended) | Bytecode targets Java 8; runtime can be newer |
| Spring Boot | 2.2.4.RELEASE | Outdated - see [limitations](doc/adr/0005-known-limitations-not-production-ready.md) |
| Maven | 3.6+ | Wrapper included (`./mvnw`) |
| Tomcat | 9.0.30 (embedded) | Via Spring Boot |

## Security Audit Summary

A security audit identified **15 issues** across the codebase:

| Severity | Count | Examples |
|----------|-------|----------|
| Critical | 4 | Hardcoded password, outdated Spring Boot, keystore in repo |
| High | 4 | Self-signed cert, no security headers, no CSRF protection |
| Medium | 4 | No HTTPS enforcement, no rate limiting |
| Low | 3 | Minimal tests, no authentication, no logging |

See [ADR-0005](doc/adr/0005-known-limitations-not-production-ready.md) for the full breakdown and remediation plan.

## Project Structure

```
spring-boot-ssl-demo/
├── src/
│   └── main/
│       ├── java/com/snhu/sslserver/
│       │   └── ServerApplication.java    # Main app + /hash endpoint
│       └── resources/
│           ├── application.properties    # SSL configuration
│           └── keystore.p12              # Self-signed certificate
├── doc/
│   ├── adr/                              # Architecture Decision Records
│   ├── guides/                           # Implementation guides
│   ├── images/                           # Screenshots
│   └── reports/                          # Security reports
├── pom.xml                               # Maven configuration
└── README.md                             # This file
```

## Roadmap

- [ ] **`hardened` branch** - Production-ready version with:
  - [ ] Environment variable configuration (no hardcoded secrets)
  - [ ] Spring Boot 3.x upgrade
  - [ ] Spring Security integration
  - [ ] CA-signed certificate support
  - [ ] Proper logging and audit trails
  - [ ] Input validation and rate limiting
  - [ ] Comprehensive test coverage

## License

This project was created for educational purposes as part of CS-305 at SNHU.

## Author

Justin Guida
