# ADR-0006: Use GitHub Actions for CI/CD

**Status:** Accepted | **Date:** 2025-12-14 | Developer: Justin Guida

## Context

The project needs automated security scanning and build verification to catch issues early. A CI/CD pipeline ensures that every push and pull request is validated.

## Decision

Use GitHub Actions to automate:

1. **OWASP Dependency-Check** - Scans for known CVEs in dependencies
2. **Build verification** - Ensures the project compiles without errors
3. **Test execution** - Runs unit tests

## Workflow Triggers

- Push to `main` or `hardened` branches
- Pull requests to `main` or `hardened` branches
- Weekly scheduled scan (Sundays at midnight)
- Manual trigger via workflow_dispatch

## Implementation

Workflow file: `.github/workflows/security-scan.yml`

```yaml
jobs:
  dependency-check:
    - Runs OWASP Dependency-Check
    - Uploads report as artifact

  build:
    - Builds with Maven
    - Runs tests
```

## Consequences

### Positive
- Automated vulnerability scanning on every commit
- Build failures caught before merge
- Security reports archived as artifacts
- No manual intervention needed

### Negative
- GitHub Actions has usage limits on free tier
- Dependency-check database updates can slow builds

## References

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [OWASP Dependency-Check](https://owasp.org/www-project-dependency-check/)

## Related Files

- [`.github/workflows/security-scan.yml`](../../.github/workflows/security-scan.yml) - CI/CD workflow
- [`pom.xml`](../../pom.xml) - OWASP plugin configuration
