# Tutorial: Implementing SHA-256 Checksum Verification with HTTPS in Spring Boot

This step-by-step tutorial teaches you how to add secure checksum verification to a Spring Boot web application. By the end, you will have a working HTTPS endpoint that displays a SHA-256 hash of your data.

---

## What You Will Learn

After completing this tutorial, you will understand how to:

1. Generate a self-signed SSL certificate using Java's keytool
2. Configure a Spring Boot application to use HTTPS
3. Implement a REST endpoint that computes SHA-256 checksums
4. Verify that your secure connection is working

---

## Prerequisites

Before starting, make sure you have:

- **Java JDK 8 or higher** installed (includes the `keytool` command)
- **Maven** installed (or use the included Maven wrapper `./mvnw`)
- **A Spring Boot project** (this tutorial uses the provided ssl-server_student project)
- **A text editor or IDE** (Eclipse, IntelliJ, VS Code, etc.)

To verify Java is installed, open a terminal and run:

```bash
java -version
```

You should see output showing Java 8 or higher.

---

## Background: Why Do We Need This?

### What is a Checksum?

A **checksum** is a value computed from data that allows you to verify the data hasn't been modified. Think of it like a fingerprint for your data. If even one character changes, the checksum will be completely different.

### What is SHA-256?

**SHA-256** (Secure Hash Algorithm 256-bit) is a cryptographic hash function that:

- Takes any input and produces a fixed 256-bit (64 character hex) output
- Is a one-way function (you cannot reverse it to get the original data)
- Is collision-resistant (extremely hard to find two inputs with the same hash)

### What is HTTPS?

**HTTPS** (HTTP Secure) encrypts the communication between your browser and the server. This prevents attackers from reading or modifying the data in transit. HTTPS requires an SSL/TLS certificate.

### Why Use a Self-Signed Certificate?

For **local development and learning**, a self-signed certificate is sufficient. In **production**, you would use a certificate from a trusted Certificate Authority (CA) like Let's Encrypt.

> **Important:** This tutorial hard codes configuration for local development only. Never use hard-coded passwords in production. Use environment variables, runtime arguments, or secrets managers instead.

---

## Step 1: Generate a Self-Signed SSL Certificate

In this step, you will create an SSL certificate that allows your application to use HTTPS.

### 1.1 Open Your Terminal

Navigate to your project's root directory:

```bash
cd /path/to/ssl-server_student
```

### 1.2 Run the Keytool Command

Java includes a tool called `keytool` for managing certificates. Run this command:

```bash
keytool -genkeypair -alias selfsigned -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 365
```

**What each flag means:**

| Flag                     | Purpose                                                |
|--------------------------|--------------------------------------------------------|
| `-genkeypair`            | Generate a public/private key pair                     |
| `-alias selfsigned`      | Name for this certificate (used to reference it later) |
| `-keyalg RSA`            | Use the RSA algorithm for the key                      |
| `-keysize 2048`          | Key size in bits (2048 is standard for security)       |
| `-storetype PKCS12`      | Modern keystore format (replaces older JKS format)     |
| `-keystore keystore.p12` | Output filename for the keystore                       |
| `-validity 365`          | Certificate valid for 365 days                         |

### 1.3 Answer the Interactive Prompts

The tool will ask you several questions. Here's an example with sample answers:

```
Enter keystore password: ********
Re-enter new password: ********
What is your first and last name?
  [Unknown]:  Justin Guida
What is the name of your organizational unit?
  [Unknown]:  SNHU
What is the name of your organization?
  [Unknown]:  Artemis Financial
What is the name of your City or Locality?
  [Unknown]:  Your City
What is the name of your State or Province?
  [Unknown]:  Your State
What is the two-letter country code for this unit?
  [Unknown]:  US
Is CN=Justin Guida, OU=SNHU, O=Artemis Financial, L=Your City, ST=Your State, C=US correct?
  [no]:  yes
```

**Important:** Remember the password you entered. You will need it in Step 2.

### 1.4 Verify the Certificate Was Created

Check that `keystore.p12` now exists in your project root:

```bash
ls -la keystore.p12
```

You should see the file with a size of about 2-3 KB.

### 1.5 Export the Certificate as a CER File

To export a readable certificate file:

```bash
keytool -exportcert -alias selfsigned -keystore keystore.p12 -file certificate.cer -storetype PKCS12
```

Enter your keystore password when prompted.

### 1.6 (Optional) View Certificate Details

To verify your certificate information:

```bash
keytool -printcert -file certificate.cer
```

**Sample output:**

```
Owner: CN=Justin Guida, OU=SNHU, O=Artemis Financial, L=Your City, ST=Your State, C=US
Issuer: CN=Justin Guida, OU=SNHU, O=Artemis Financial, L=Your City, ST=Your State, C=US
Serial number: 1234567890abcdef
Valid from: Fri Dec 13 00:00:00 EST 2024 until: Sat Dec 13 00:00:00 EST 2025
Certificate fingerprints:
         SHA1: AA:BB:CC:DD:EE:FF:00:11:22:33:44:55:66:77:88:99:AA:BB:CC:DD
         SHA256: 11:22:33:44:55:66:77:88:99:AA:BB:CC:DD:EE:FF:00:11:22:33:44:55:66:77:88:99:AA:BB:CC:DD:EE:FF:00
Signature algorithm name: SHA256withRSA
Subject Public Key Algorithm: 2048-bit RSA key
Version: 3
```

Notice that the **Owner** and **Issuer** are the same. This is what makes it "self-signed" (you signed your own certificate, rather than having a CA sign it).

---

## Step 2: Copy the Keystore to Resources

Spring Boot needs to find your keystore file. The easiest way is to place it in the resources folder.

### 2.1 Copy the Keystore File

```bash
cp keystore.p12 src/main/resources/
```

### 2.2 Verify the Copy

```bash
ls src/main/resources/
```

You should see `keystore.p12` listed alongside `application.properties`.

---

## Step 3: Configure HTTPS in application.properties

Now you need to tell Spring Boot to use your certificate for HTTPS.

### 3.1 Open the Properties File

Open `src/main/resources/application.properties` in your editor.

### 3.2 Add the SSL Configuration

Add these lines to the file:

```properties
# HTTPS/SSL Configuration for Artemis Financial
# Self-signed certificate for local development

server.port=8443
server.ssl.key-alias=selfsigned
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-type=PKCS12

# NOTE: Password is hardcoded here for local development only.
# In production, use environment variables instead:
#   server.ssl.key-store-password=${KEYSTORE_PASSWORD}
# Or pass at runtime:
#   --server.ssl.key-store-password=yourpassword
server.ssl.key-store-password=YOUR_PASSWORD_HERE
```

**What each property means:**

| Property                                      | Purpose                                                                  |
|-----------------------------------------------|--------------------------------------------------------------------------|
| `server.port=8443`                            | Use port 8443 (standard HTTPS alternate port; 443 requires admin rights) |
| `server.ssl.key-alias=selfsigned`             | The alias you used in keytool (-alias flag)                              |
| `server.ssl.key-store-password`               | The password you created for the keystore                                |
| `server.ssl.key-store=classpath:keystore.p12` | Location of keystore (`classpath:` means look in resources folder)       |
| `server.ssl.key-store-type=PKCS12`            | The keystore format                                                      |

### 3.3 Replace the Password

Change `YOUR_PASSWORD_HERE` to the actual password you created in Step 1.

> **Security Note:** The comments in the properties file remind you that hard-coding passwords is only acceptable for local development. In production, you would use environment variables or runtime arguments as shown in the comments.

---

## Step 4: Create the Hash Controller

Now you will write the Java code that computes and displays the SHA-256 checksum.

### 4.1 Open ServerApplication.java

Open `src/main/java/com/snhu/sslserver/ServerApplication.java` in your editor.

### 4.2 Replace the Contents

Replace the entire file with the following code:

```java
package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Standard charset constant (UTF-8) used when converting the String to bytes
import java.nio.charset.StandardCharsets;

// MessageDigest class used to compute the SHA-256 hash
import java.security.MessageDigest;

// Checked exception thrown if the requested hash algorithm (SHA-256) is not available
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}

@RestController
class ServerController {
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String DATA = "Justin Guida";

    @RequestMapping("/hash")
    public String myHash() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        byte[] hashBytes = digest.digest(DATA.getBytes(StandardCharsets.UTF_8));
        String hashString = bytesToHex(hashBytes);
        return "<p>Data: " + DATA + "</p>"
                + "<p>Name of Cipher Algorithm Used: " + HASH_ALGORITHM + "</p>"
                + "<p>CheckSum Value: " + hashString + "</p>";
    }

    // Helper method to convert byte array to hex string for older Java versions
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
```

### 4.3 Customize Your Name

Find this line and change it to your name:

```java
private static final String DATA = "Justin Guida";
```

---

## Step 5: Understanding the Code

Let's break down what each part of the code does.

### 5.1 The Imports

```java
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
```

- **StandardCharsets.UTF_8**: Ensures consistent byte encoding across all systems
- **MessageDigest**: Java's built-in class for computing cryptographic hashes
- **NoSuchAlgorithmException**: Thrown if the requested algorithm isn't available

### 5.2 The Controller Annotation

```java
@RestController
class ServerController {
```

The `@RestController` annotation tells Spring Boot that this class handles HTTP requests and returns data directly (not a view/template).

### 5.3 The Constants

```java
private static final String HASH_ALGORITHM = "SHA-256";
private static final String DATA = "Justin Guida";
```

Using constants makes the code easier to maintain. If you need to change the algorithm or data, you only change it in one place.

### 5.4 The Hash Endpoint

```java
@RequestMapping("/hash")
public String myHash() throws NoSuchAlgorithmException {
```

The `@RequestMapping("/hash")` annotation maps HTTP requests to `/hash` to this method.

### 5.5 Computing the Hash

```java
MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
byte[] hashBytes = digest.digest(DATA.getBytes(StandardCharsets.UTF_8));
```

1. `MessageDigest.getInstance("SHA-256")` - Gets a SHA-256 hash calculator
2. `DATA.getBytes(StandardCharsets.UTF_8)` - Converts the string to bytes using UTF-8 encoding
3. `digest.digest(...)` - Computes the hash and returns it as a byte array

### 5.6 Converting Bytes to Hex

```java
private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 2);
    for (byte b : bytes) {
        sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
}
```

The hash is returned as bytes, but we want to display it as a readable hex string. This method:

1. Creates a StringBuilder (more efficient than string concatenation)
2. Loops through each byte
3. Converts each byte to a 2-character hex string (`%02x` means 2 digits, padded with zeros)
4. The `& 0xff` ensures the byte is treated as unsigned (0-255 instead of -128 to 127)

---

## Step 6: Run the Application

### Option A: Run in Eclipse

1. In the Package Explorer, find `ServerApplication.java`
2. Right-click on the file
3. Select **Run As** > **Spring Boot App**

### Option B: Run from Terminal

```bash
./mvnw spring-boot:run
```

On Windows, use:

```bash
mvnw.cmd spring-boot:run
```

### 6.1 Watch for Startup Messages

Look for output similar to:

```
Tomcat started on port(s): 8443 (https)
Started ServerApplication in 2.5 seconds
```

The key indicator is `(https)` - this confirms HTTPS is enabled.

---

## Step 7: Test in Your Browser

### 7.1 Open Your Browser

Navigate to: `https://localhost:8443/hash`

### 7.2 Accept the Security Warning

Because you're using a self-signed certificate, your browser will show a warning. This is expected and safe for local development.

**In Chrome:**
1. Click "Advanced"
2. Click "Proceed to localhost (unsafe)"

**In Firefox:**
1. Click "Advanced..."
2. Click "Accept the Risk and Continue"

**In Safari:**
1. Click "Show Details"
2. Click "visit this website"

### 7.3 Verify the Output

You should see a page displaying:

- **Data:** Your name
- **Name of Cipher Algorithm Used:** SHA-256
- **CheckSum Value:** A 64-character hexadecimal string

**Example output:**

```
Data: Justin Guida
Name of Cipher Algorithm Used: SHA-256
CheckSum Value: 7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069
```

---

## Step 8: Verify the Hash is Correct

To prove your implementation works correctly, you can verify the hash using an online tool or command line.

### Using Command Line (macOS/Linux)

```bash
echo -n "Justin Guida" | shasum -a 256
```

The output should match your checksum value exactly.

### Using Command Line (Windows PowerShell)

```powershell
$bytes = [System.Text.Encoding]::UTF8.GetBytes("Justin Guida")
$hash = [System.Security.Cryptography.SHA256]::Create().ComputeHash($bytes)
[BitConverter]::ToString($hash) -replace '-',''
```

---

## Understanding Why SHA-256?

| Property        | Description                                                 |
|-----------------|-------------------------------------------------------------|
| **Output Size** | 256 bits (64 hex characters)                                |
| **Security**    | No known practical collisions                               |
| **Status**      | NIST approved, industry standard                            |
| **Use Cases**   | Data integrity, checksums, digital signatures, certificates |

### Key Hash Properties

- **Deterministic:** Same input always produces the same output
- **One-way:** Cannot reverse the hash to get the original data
- **Collision-resistant:** Extremely difficult to find two different inputs with the same hash
- **Avalanche effect:** A small change in input creates a completely different hash

---

## File Structure After Implementation


```
ssl-server_student/
├── .github/
│   └── workflows/
│       └── security-scan.yml           <-- CI/CD pipeline
├── src/
│   ├── main/
│   │   ├── java/com/snhu/sslserver/
│   │   │   └── ServerApplication.java  <-- UPDATED (contains controller)
│   │   └── resources/
│   │       ├── application.properties  <-- UPDATED (HTTPS config)
│   │       ├── keystore.p12            <-- GENERATED (SSL certificate)
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/com/snhu/sslserver/
│           └── SslServerApplicationTests.java
├── doc/
│   ├── adr/
│   │   ├── README.md                   <-- ADR index
│   │   ├── 0001-use-sha256-for-checksum.md
│   │   ├── 0002-use-aes-gcm-cipher.md
│   │   ├── 0003-use-pkcs12-keystore.md
│   │   ├── 0004-use-tls-https.md
│   │   ├── 0005-known-limitations-not-production-ready.md
│   │   └── 0006-use-github-actions-cicd.md
│   ├── guides/
│   │   ├── implementation-instructions.md
│   │   └── quickstart.md
│   ├── images/                         <-- Screenshots
│   ├── reports/
│   │   ├── secure-software-report.md
│   │   └── security-review.md
│   └── requirements/
│       ├── requirements.md
│       └── roadmap.md
├── certificate.cer                     <-- OPTIONAL (exported certificate)
├── pom.xml
└── README.md
```

> **Note:** The `keystore.p12` file is generated during setup (see Step 1). The `certificate.cer` file is optional and only needed if you want to view or share the certificate details.

---

## Troubleshooting

### "PKCS12 keystore not found"

**Cause:** Spring Boot cannot find the keystore file.

**Solution:**
1. Verify `keystore.p12` is in `src/main/resources/`
2. Check that `application.properties` uses `classpath:keystore.p12`

### "Password was incorrect"

**Cause:** The password in `application.properties` doesn't match the keystore password.

**Solution:** Double-check the password matches exactly what you typed when running keytool.

### Browser Shows "Not Secure" or Certificate Warning

**Cause:** This is expected behavior for self-signed certificates.

**Solution:** This is normal for development. Click through the warning as described in Step 7.2.

### Port 8443 Already in Use

**Cause:** Another application is using port 8443.

**Solution 1:** Find and stop the other process:

```bash
# macOS/Linux
lsof -i :8443
kill <PID>

# Windows
netstat -ano | findstr :8443
taskkill /PID <PID> /F
```

**Solution 2:** Use a different port by changing `server.port` in `application.properties` to 8444 or another available port.

### Application Won't Start

**Cause:** Various issues like missing dependencies or syntax errors.

**Solution:**
1. Check the console output for error messages
2. Verify all imports are correct in `ServerApplication.java`
3. Make sure Maven dependencies are downloaded: `./mvnw clean install`

---

## Summary

In this tutorial, you learned how to:

1. **Generate an SSL certificate** using Java's keytool command
2. **Configure Spring Boot for HTTPS** using application.properties
3. **Create a REST endpoint** that computes SHA-256 checksums
4. **Understand the code** that performs cryptographic hashing
5. **Test and verify** your secure implementation

These skills are fundamental for building secure web applications that protect data integrity and confidentiality.

---

## Next Steps

To continue learning, consider:

- Learning about CA-signed certificates for production use
- Exploring other hash algorithms (SHA-384, SHA-512, SHA-3)
- Implementing AES encryption for data at rest (see the secure-software-report.md for cipher recommendations)
- Adding input validation and error handling to the endpoint