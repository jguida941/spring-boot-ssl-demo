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
class ServerController{
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