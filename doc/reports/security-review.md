# Security Review Report

**Project:** Spring Boot SSL Demo
**Reviewer:** Justin Guida
**Date:** 2025-12-14
**Scope:** Full codebase security audit

---

## Executive Summary

This security review identified **15 findings** across the codebase. The application is an educational demo and contains intentional simplifications. These findings are documented for learning purposes and to demonstrate security awareness.

| Severity | Count |
|----------|-------|
| Critical | 4 |
| High | 4 |
| Medium | 4 |
| Low | 3 |

---

## Critical Findings

### CRIT-01: Hardcoded Keystore Password

**File:** `src/main/resources/application.properties:14`

**Finding:** Keystore password is hardcoded in plaintext.

```properties
server.ssl.key-store-password=Artemis2024!Secure
```

**Risk:** Password exposed in source code and version control. Anyone with repo access can decrypt the keystore.

**Recommendation:** Use environment variables:
```properties
server.ssl.key-store-password=${KEYSTORE_PASSWORD}
```

---

### CRIT-02: Keystore Committed to Repository

**File:** `src/main/resources/keystore.p12`

**Finding:** PKCS12 keystore containing private key is in the repository.

**Risk:** Combined with CRIT-01, private key is fully compromised.

**Recommendation:**
- Add `*.p12` to `.gitignore`
- Generate keystore at deployment time
- Use secrets manager (HashiCorp Vault, AWS Secrets Manager)

---

### CRIT-03: Outdated Spring Boot Version

**File:** `pom.xml:8`

**Finding:** Using Spring Boot 2.2.4.RELEASE (January 2020).

**Risk:** Contains 162+ known CVEs including:
- CVE-2022-22965 (Spring4Shell) - Remote Code Execution
- CVE-2020-1938 (Ghostcat) - Information Disclosure
- Multiple deserialization vulnerabilities

**Recommendation:** Upgrade to Spring Boot 3.x or latest 2.7.x.

---

### CRIT-04: Ineffective Dependency Check Threshold

**File:** `pom.xml:60`

**Finding:** CVSS threshold set to 11, but max CVSS score is 10.0.

```xml
<failBuildOnCVSS>11</failBuildOnCVSS>
```

**Risk:** Build never fails for vulnerabilities, giving false sense of security.

**Recommendation:** Set threshold to 7.0 or lower:
```xml
<failBuildOnCVSS>7</failBuildOnCVSS>
```

---

## High Findings

### HIGH-01: No Spring Security

**File:** Missing dependency

**Finding:** No authentication, authorization, CSRF protection, or security headers.

**Risk:**
- No user authentication
- No CSRF tokens on forms
- Missing security headers (HSTS, CSP, X-Frame-Options)
- All endpoints publicly accessible

**Recommendation:** Add Spring Security:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

---

### HIGH-02: Self-Signed Certificate

**File:** `src/main/resources/keystore.p12`

**Finding:** Using self-signed certificate for SSL/TLS.

**Risk:**
- Cannot be verified by clients
- Vulnerable to man-in-the-middle attacks
- Users trained to ignore certificate warnings

**Recommendation:** Use CA-signed certificate (Let's Encrypt for free).

---

### HIGH-03: HTML Response Without Escaping

**File:** `src/main/java/com/snhu/sslserver/ServerApplication.java:36-38`

**Finding:** Raw HTML returned without proper content type or escaping.

```java
return "<p>Data: " + DATA + "</p>"
        + "<p>Name of Cipher Algorithm Used: " + HASH_ALGORITHM + "</p>"
        + "<p>CheckSum Value: " + hashString + "</p>";
```

**Risk:** If `DATA` becomes user-controlled, vulnerable to XSS.

**Recommendation:**
- Return JSON with proper content type
- Or use template engine with auto-escaping

---

### HIGH-04: No TLS Version Configuration

**File:** `src/main/resources/application.properties`

**Finding:** TLS version not explicitly configured.

**Risk:** May negotiate to older TLS versions depending on JVM defaults.

**Recommendation:** Add explicit TLS configuration:
```properties
server.ssl.enabled-protocols=TLSv1.2,TLSv1.3
server.ssl.ciphers=TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
```

---

## Medium Findings

### MED-01: No HTTPS Redirect

**File:** `src/main/resources/application.properties`

**Finding:** No HTTP to HTTPS redirect configured.

**Risk:** Users could access via HTTP if server listens on port 8080.

**Recommendation:** Add HTTP redirect or disable HTTP entirely.

---

### MED-02: No Rate Limiting

**File:** `ServerApplication.java`

**Finding:** No rate limiting on `/hash` endpoint.

**Risk:** Endpoint could be abused for DoS.

**Recommendation:** Implement rate limiting with Spring Boot Actuator or custom filter.

---

### MED-03: No Input Validation Framework

**File:** `ServerApplication.java`

**Finding:** No input validation framework configured.

**Risk:** If endpoints accept user input in future, no validation infrastructure exists.

**Recommendation:** Add Bean Validation (JSR-380):
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

---

### MED-04: Exception Exposed in Response

**File:** `src/main/java/com/snhu/sslserver/ServerApplication.java:32`

**Finding:** `NoSuchAlgorithmException` thrown directly to client.

```java
public String myHash() throws NoSuchAlgorithmException {
```

**Risk:** Stack traces could leak sensitive information.

**Recommendation:** Add global exception handler with sanitized error messages.

---

## Low Findings

### LOW-01: Minimal Test Coverage

**File:** `src/test/java/com/snhu/sslserver/SslServerApplicationTests.java`

**Finding:** Only context load test exists.

**Risk:** Security regressions not caught.

**Recommendation:** Add security-focused unit tests.

---

### LOW-02: No Audit Logging

**File:** N/A

**Finding:** No logging of security events.

**Risk:** Cannot trace security incidents.

**Recommendation:** Add structured logging with SLF4J.

---

### LOW-03: Java 8 Bytecode Target

**File:** `pom.xml:18`

**Finding:** Targeting Java 8 bytecode.

```xml
<java.version>1.8</java.version>
```

**Risk:** Missing newer security features and APIs.

**Recommendation:** Target Java 17+ (LTS).

---

## Dependency Vulnerabilities

OWASP Dependency-Check identified vulnerabilities in these libraries:

| Dependency | CVE Count | Highest Severity |
|------------|-----------|------------------|
| tomcat-embed-core-9.0.30 | 40+ | Critical |
| spring-core-5.2.3.RELEASE | 10 | Critical |
| jackson-databind-2.10.2 | 6 | High |
| snakeyaml-1.25 | 7 | High |
| logback-core-1.2.3 | 2 | High |
| hibernate-validator-6.0.18 | 3 | Medium |

Full report: `target/dependency-check-report.html`

---

## Recommendations Summary

### Immediate Actions
1. Remove hardcoded password from `application.properties`
2. Remove `keystore.p12` from repository
3. Set `failBuildOnCVSS` to 7 or lower

### Short-Term Actions
1. Upgrade to Spring Boot 3.x
2. Add Spring Security
3. Configure explicit TLS versions

### Long-Term Actions
1. Implement authentication/authorization
2. Add comprehensive logging
3. Increase test coverage

---

## Related Files

- [`src/main/java/com/snhu/sslserver/ServerApplication.java`](../../src/main/java/com/snhu/sslserver/ServerApplication.java)
- [`src/main/resources/application.properties`](../../src/main/resources/application.properties)
- [`pom.xml`](../../pom.xml)
- [`doc/adr/0005-known-limitations-not-production-ready.md`](../adr/0005-known-limitations-not-production-ready.md)
