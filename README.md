# Spring Boot SSL Demo

[![Java](https://img.shields.io/badge/Java-8%2B-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.2.4-green)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-Educational-blue)](#license)

> **⚠️ EDUCATIONAL PROJECT - NOT PRODUCTION READY**
>
> This project was developed for **CS-305 Software Security** at SNHU. It contains intentional security simplifications for educational purposes including hardcoded credentials, self-signed certificates, and outdated dependencies. See [Known Limitations](doc/adr/0005-known-limitations-not-production-ready.md) for details and the [Roadmap](doc/guides/roadmap.md) for planned improvements.

---

## About

A Spring Boot REST API demonstrating secure software development concepts for **Artemis Financial**, a fictional financial consulting company requiring secure data transmission and verification.

### Features

- **HTTPS/TLS** - Encrypted communications on port 8443
- **SHA-256 Checksum** - Cryptographic hash verification endpoint
- **PKCS12 Keystore** - Industry-standard certificate storage
- **Architecture Decision Records** - Documented security decisions

---

## Quick Start

```bash
git clone https://github.com/jguida941/spring-boot-ssl-demo.git
cd spring-boot-ssl-demo
./mvnw spring-boot:run
```

Open: `https://localhost:8443/hash`

> **Note:** Accept the browser security warning (self-signed certificate).

See [Quick Start Guide](doc/guides/quickstart.md) for prerequisites and troubleshooting.

---

## Documentation

| Document | Description |
|:---------|:------------|
| [Quick Start](doc/guides/quickstart.md) | Setup and run instructions |
| [Implementation Guide](doc/guides/implementation-instructions.md) | Step-by-step implementation details |
| [Security Report](doc/reports/secure-software-report.md) | Security analysis and findings |
| [Roadmap](doc/guides/roadmap.md) | Planned improvements |
| [Requirements](doc/guides/requirements.md) | Original assignment specifications |

### Architecture Decision Records

| ADR | Decision |
|:----|:---------|
| [0001](doc/adr/0001-use-sha256-for-checksum.md) | SHA-256 for Checksum Verification |
| [0002](doc/adr/0002-use-aes-gcm-cipher.md) | AES/GCM/NoPadding for Encryption |
| [0003](doc/adr/0003-use-pkcs12-keystore.md) | PKCS12 Keystore Format |
| [0004](doc/adr/0004-use-tls-https.md) | TLS 1.3/1.2 for HTTPS |
| [0005](doc/adr/0005-known-limitations-not-production-ready.md) | Known Limitations |

---

## Tech Stack

| Technology | Version |
|:-----------|:--------|
| Java | 8+ (17+ recommended) |
| Spring Boot | 2.2.4.RELEASE |
| Maven | 3.6+ (wrapper included) |
| Tomcat | 9.0.30 (embedded) |

---

## Security Audit

A comprehensive security audit identified **15 issues**. See [ADR-0005](doc/adr/0005-known-limitations-not-production-ready.md) for the complete breakdown and remediation plan.

| Severity | Count |
|:---------|:------|
| Critical | 4 |
| High | 4 |
| Medium | 4 |
| Low | 3 |

---

## Project Structure

```
spring-boot-ssl-demo/
├── src/main/
│   ├── java/com/snhu/sslserver/
│   │   └── ServerApplication.java
│   └── resources/
│       └── application.properties
├── doc/
│   ├── adr/           # Architecture decisions
│   ├── guides/        # Documentation
│   ├── images/        # Screenshots
│   └── reports/       # Security reports
└── pom.xml
```

---

## License

Educational project for CS-305 at SNHU.

## Author

**Justin Guida**
