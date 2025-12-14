# Quick Start Guide

Get the SSL server running in under 5 minutes.

## Prerequisites

- **Java 8+** (Java 17+ recommended for better TLS defaults)
- **Maven 3.6+** (or use included Maven wrapper)

Verify your Java installation:

```bash
java -version
```

## Clone the Repository

```bash
git clone https://github.com/jguida941/spring-boot-ssl-demo.git
cd spring-boot-ssl-demo
```

## Build the Project

Using the Maven wrapper (recommended):

```bash
./mvnw clean package
```

Or if you have Maven installed:

```bash
mvn clean package
```

## Run the Application

```bash
./mvnw spring-boot:run
```

You should see output ending with:

```
Tomcat started on port(s): 8443 (https)
Started ServerApplication in X.XXX seconds
```

## Access the Application

Open your browser and navigate to:

```
https://localhost:8443/hash
```

**Note:** You will see a browser security warning because the application uses a self-signed certificate. This is expected for local development.

- **Chrome:** Click "Advanced" → "Proceed to localhost (unsafe)"
- **Firefox:** Click "Advanced" → "Accept the Risk and Continue"
- **Safari:** Click "Show Details" → "visit this website"

## Expected Output

You should see a page displaying:

```
Data: Justin Guida

Name of Cipher Algorithm Used: SHA-256

CheckSum Value: [64-character hex string]
```

## Troubleshooting

### Port already in use

```
Web server failed to start. Port 8443 was already in use.
```

**Solution:** Stop the other process using port 8443, or change the port in `application.properties`:

```properties
server.port=8444
```

### Keystore not found

```
The Tomcat connector configured to listen on port 8443 failed to start.
```

**Solution:** Ensure `keystore.p12` exists in `src/main/resources/`. If missing, regenerate it:

```bash
keytool -genkeypair -alias selfsigned -keyalg RSA -keysize 2048 \
  -storetype PKCS12 -keystore src/main/resources/keystore.p12 \
  -validity 365 -storepass "YOUR_PASSWORD_HERE"
```

Replace `YOUR_PASSWORD_HERE` with the password configured in `application.properties`.

### Java version issues

If you get compilation errors, ensure your Java version is compatible:

```bash
java -version
./mvnw -version
```

## Next Steps

- Read the [Implementation Instructions](implementation-instructions.md) for detailed explanations
- Review the [Architecture Decision Records](../adr/README.md) for design rationale
- Check [Known Limitations](../adr/0005-known-limitations-not-production-ready.md) before any production use
