# Spring Boot SSL Demo

[![Java](https://img.shields.io/badge/Java-8%2B-orange?logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.2.4-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-C71A36?logo=apachemaven)](https://maven.apache.org/)
[![HTTPS](https://img.shields.io/badge/HTTPS-TLS%201.2%2F1.3-blue?logo=letsencrypt)](https://en.wikipedia.org/wiki/HTTPS)
[![SHA-256](https://img.shields.io/badge/Hash-SHA--256-purple)](https://en.wikipedia.org/wiki/SHA-2)

> **Educational Demo**
>
> Learn how to implement SHA-256 hashing, generate SSL certificates, and run HTTPS on localhost.
> For local development only. Production-ready `hardened` branch coming soon. See [Roadmap](doc/requirements/roadmap.md).

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

| Document                                                          | Description                         |
|:------------------------------------------------------------------|:------------------------------------|
| [Quick Start](doc/guides/quickstart.md)                           | Setup and run instructions          |
| [Implementation Guide](doc/guides/implementation-instructions.md) | Step-by-step implementation details |
| [Security Report](doc/reports/secure-software-report.md)          | Security analysis and findings      |
| [Roadmap](doc/requirements/roadmap.md)                            | Planned improvements                |
| [Requirements](doc/requirements/requirements.md)                  | Original assignment specifications  |

### Architecture Decision Records

| ADR                                                            | Decision                          |
|:---------------------------------------------------------------|:----------------------------------|
| [0001](doc/adr/0001-use-sha256-for-checksum.md)                | SHA-256 for Checksum Verification |
| [0002](doc/adr/0002-use-aes-gcm-cipher.md)                     | AES/GCM/NoPadding for Encryption  |
| [0003](doc/adr/0003-use-pkcs12-keystore.md)                    | PKCS12 Keystore Format            |
| [0004](doc/adr/0004-use-tls-https.md)                          | TLS 1.3/1.2 for HTTPS             |
| [0005](doc/adr/0005-known-limitations-not-production-ready.md) | Known Limitations                 |
| [0006](doc/adr/0006-use-github-actions-cicd.md)                | GitHub Actions CI/CD              |

---

## CI/CD

GitHub Actions workflow runs on every push:

- **Build** - Compiles the project with Maven
- **Test** - Runs unit tests
- **Security Scan** - OWASP Dependency-Check for CVEs

Workflow: [`.github/workflows/security-scan.yml`](.github/workflows/security-scan.yml)

---

## Tech Stack

| Technology  | Version                 |
|:------------|:------------------------|
| Java        | 8+ (17+ recommended)    |
| Spring Boot | 2.2.4.RELEASE           |
| Maven       | 3.6+ (wrapper included) |
| Tomcat      | 9.0.30 (embedded)       |

---

## Security Audit

A security audit identified **15 issues** documented for educational purposes. See [ADR-0005](doc/adr/0005-known-limitations-not-production-ready.md) for the full breakdown and remediation plan.

| Severity | Count |
|:---------|:------|
| Critical | 4     |
| High     | 4     |
| Medium   | 4     |
| Low      | 3     |

---

## Project Structure

```
spring-boot-ssl-demo/
├── .github/
│   └── workflows/
│       └── security-scan.yml  # CI/CD pipeline
├── src/main/
│   ├── java/com/snhu/sslserver/
│   │   └── ServerApplication.java
│   └── resources/
│       ├── application.properties
│       └── keystore.p12        # Generated (see Quick Start)
├── doc/
│   ├── adr/           # Architecture decisions
│   ├── guides/        # Implementation guides
│   ├── images/        # Screenshots
│   ├── reports/       # Security reports
│   └── requirements/  # Requirements and roadmap
└── pom.xml
```

---

## Developer

**Justin Guida**
