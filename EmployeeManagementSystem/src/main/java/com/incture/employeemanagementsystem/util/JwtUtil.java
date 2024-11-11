package com.incture.employeemanagementsystem.util;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private static final String secretKey = "Hello";

	public String generateToken(String username) {
		JwtBuilder jwt = Jwts.builder().subject(username).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, generateJwtSecretKey());
		return jwt.compact();
	}

	public SecretKey generateJwtSecretKey() {
		// Convert the static word to a byte array
		byte[] keyBytes = secretKey.getBytes();

		// Ensure the key length is compatible with the algorithm (HMAC SHA-256 requires
		// 32 bytes)
		byte[] keyBytesPadded = new byte[32];
		System.arraycopy(keyBytes, 0, keyBytesPadded, 0, Math.min(keyBytes.length, 32));

		// Generate the SecretKey using the static word
		return Keys.hmacShaKeyFor(keyBytesPadded);
	}
	
	//method to return the username from jwt token
	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}
	// get all the claims i.e subject issue and expiration
	public Claims getClaims(String token) {
		return Jwts.parser().verifyWith(generateJwtSecretKey()).build()
				.parseSignedClaims(token).getPayload();
	}
	
	//method to validate the token
	public boolean validateToken(String token,String username) {
		return (username.equals(extractUsername(token)) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		
		return getClaims(token).getExpiration().before(new Date());
	}
	
}