# Scenario

You work as a developer for a software engineering company called **Global Rain**. Global Rain specializes in custom software design and development. The software is for entrepreneurs, businesses, and government agencies around the world. Part of the company's mission is *"Security is everyone's responsibility."* Global Rain has promoted you to its new agile scrum team.

At Global Rain, you work with a client, **Artemis Financial**. Artemis Financial is a consulting company that develops individualized financial plans for its customers. The financial plans include savings, retirement, investments, and insurance.

Artemis Financial wants to modernize its operations. As a crucial part of the success of its custom software, the company also wants to use the most current and effective software security. Artemis Financial has a public web interface. The company is seeking Global Rain's expertise on how to protect its client data and financial information.

Specifically, Artemis Financial wants to add a file verification step to its web application to ensure secure communications. When the web application is used to transfer data, the company will need a data verification step in the form of a checksum. You must take Artemis Financial's existing software application and add secure communication mechanisms to meet the company's software security requirements. You'll deliver a production-quality integrated application that includes secure coding protocols.

---

# Directions

You must examine Artemis Financial's software to address any security vulnerabilities. This examination will require you to refactor the Project Two Code Base linked in the Supporting Materials section to add functionality to meet software security requirements for Artemis Financial's application. Specifically, you must follow the steps outlined below to facilitate your findings and address and remedy all areas. Use the Project Two Template linked in the What to Submit section to document your work for your practices for secure software report. You will also submit zipped files that contain the refactored code. Review this module's Resources section to help you with this assignment.

## 1. Algorithm Cipher

Recommend an appropriate encryption algorithm cipher to deploy, given the security vulnerabilities, and justify your reasoning. Review the scenario and the supporting materials to support your recommendation. In your practices for secure software report, be certain to address the following actions:

- Provide a brief, high-level overview of the encryption algorithm cipher
- Discuss the hash functions and bit levels of the cipher
- Explain the use of random numbers, symmetric versus non-symmetric keys, and so on
- Describe the history and current state of encryption algorithms

## 2. Certificate Generation

Generate appropriate self-signed certificates using the Java Keytool in Eclipse.

Complete the following steps to demonstrate that the certificate was correctly generated:
- Export your certificates as a CER file
- Submit a screenshot of the CER file in your practices for secure software report

## 3. Deploy Cipher

Deploy and implement the cryptographic hash algorithm by refactoring code. Demonstrate functionality with a checksum verification.

- Submit a screenshot of the checksum verification in your practices for secure software report
- The screenshot must show your name and a unique data string that has been created

## 4. Secure Communications

Verify secure communication. In the application properties file, refactor the code to convert HTTP to the HTTPS protocol. Compile and run the refactored code. Once the server is running, type `https://localhost:8443/hash` in a new browser to demonstrate that the secure communication works.

- Create a screenshot of the web browser that shows a secure webpage and include it in your practices for secure software report

## 5. Secondary Testing

Run a secondary static testing of the refactored code using the dependency-check tool to make certain the code complies with software security enhancements. You need to focus only on the code you have added as part of the refactoring. Complete the dependency check and review the output to make certain you did not introduce additional security vulnerabilities.

In your practices for secure software report, include the following items:
- A screenshot of the refactored code executed without errors
- A screenshot of the report of the output from the dependency-check static tester

## 6. Functional Testing

Identify the software application's syntactical, logical, and security vulnerabilities by manually reviewing the code.

- Complete this functional testing and include a screenshot of the refactored code, executed without errors, in your practices for secure software report

> **What if I receive errors or new vulnerabilities?**
> You will need to iterate on your design and refactored code, address vulnerabilities, and retest until no new vulnerabilities are found.

---

# Summary

Discuss how the code has been refactored and complies with security testing protocols. In the summary of your practices for secure software report, be certain to address the following items:

- Refer to the vulnerability assessment process flow diagram in the Supporting Materials section. Highlight the areas of security that you addressed by refactoring the code.
- Discuss your process for adding layers of security to the software application.

## Industry Standard Best Practices

Explain how you applied industry standard best practices for secure coding to mitigate known security vulnerabilities. Be sure to address the following items:

- Explain how you used industry standard best practices to maintain the software application's existing security
- Explain the value of applying industry standard best practices for secure coding to the company's overall well-being