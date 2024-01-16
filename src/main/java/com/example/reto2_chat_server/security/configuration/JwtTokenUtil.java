package com.example.reto2_chat_server.security.configuration;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.reto2_chat_server.security.model.UserDAO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; 

	@Value("${app.jwt.secret}")
	private String SECRET_KEY;

	public String generateAccessToken(UserDAO userDAO) {
		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(null)//TODO EMAIL?, NUMERO DE TELEFONO?
				.setIssuer("ADT_DAM")
				.setExpiration(new Date(System.currentTimeMillis()+ EXPIRE_DURATION))
				//TODO añadir los campos del User

				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();

	}

	public Boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
			// TODO: handle exception
		} catch (ExpiredJwtException ex) {
			LOGGER.error("JWT expired", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
		} catch (MalformedJwtException ex) {
			LOGGER.error("JWT is invalid", ex);
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("JWT is not supported", ex);
		} catch (SignatureException ex) {
			LOGGER.error("Signature validation failed");
		}
		return false;
	}

	//TODO FALTA HACER los getters de los campos del user

	/*
	public String getSubject(String token) {
		return parseClaims(token.getSubject();
	}
	*/
	
	private Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}







}
