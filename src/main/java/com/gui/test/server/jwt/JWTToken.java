package com.gui.test.server.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gui.test.server.database.Connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JWTToken {
	private static String key;
	

	public JWTToken() {
	}
	
	static {
		Properties properties = new Properties();
//		try (InputStream config = Files.newInputStream(Paths.get(PATH_TO_PROPERTIES))){
		try (InputStream config = Connector.class.getClassLoader().getResourceAsStream("config.properties")){
			properties.load(config);
			
			key = properties.getProperty("key");
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	public static String generateAccessToken(String login, String username, String password) throws UnsupportedEncodingException {
		Algorithm algorithm = Algorithm.HMAC256(key);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 1);
//		calendar.add(Calendar.SECOND, 5);
		String token = JWT.create()
				.withIssuer("UPDServer")
				.withIssuedAt(new Date())
				.withExpiresAt(calendar.getTime())
				.withClaim("login", login)
				.withClaim("username", username)
				.withClaim("password", password)
				.sign(algorithm);
		
		return token;
	}
	
	public static String generateRefreshToken(String login, String username, String password) throws UnsupportedEncodingException {
		Algorithm algorithm = Algorithm.HMAC256(key);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 120);
		
		String token = JWT.create()
				.withIssuer("UPDServer")
				.withSubject("ProductManagement")
				.withIssuedAt(new Date())
				.withExpiresAt(calendar.getTime())
				.withClaim("login", login)
				.withClaim("username", username)
				.withClaim("password", password)
				.sign(algorithm);
		
		return token;
	}
	
	public static String verifyAccessToken(String token) throws UnsupportedEncodingException, JWTVerificationException {
		Algorithm algorithm = Algorithm.HMAC256(key);
		
		DecodedJWT jwt = JWT.require(algorithm)
				.build()
				.verify(token);
		
		Map<String, Claim> claims = jwt.getClaims();
		
		return claims.get("login").asString();
	}
	
	public static HashMap<String, String> verifyRefreshToken(String token) throws UnsupportedEncodingException, JWTVerificationException {
		Algorithm algorithm = Algorithm.HMAC256(key);
		
		DecodedJWT jwt = JWT.require(algorithm)
				.build()
				.verify(token);
		
		Map<String, Claim> claims = jwt.getClaims();
		
		HashMap<String, String> result = new HashMap<>();
		result.put("login", claims.get("login").asString());
		result.put("username", claims.get("username").asString());
		result.put("password", claims.get("password").asString());
		
		return result;
	}
}