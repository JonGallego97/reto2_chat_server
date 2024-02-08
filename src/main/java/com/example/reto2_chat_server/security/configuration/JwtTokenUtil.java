package com.example.reto2_chat_server.security.configuration;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.reto2_chat_server.department.service.DepartmentServiceModel;
import com.example.reto2_chat_server.model.RoleServiceModel;
import com.example.reto2_chat_server.security.user.service.UserServiceModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

	public String generateAccessToken(UserServiceModel userServiceModel) {
		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(userServiceModel.getEmail())
				.setIssuer("ADT_DAM")
				.setExpiration(new Date(System.currentTimeMillis()+ EXPIRE_DURATION))
				.claim("id", userServiceModel.getId())
				.claim("name", userServiceModel.getName())
				.claim("email", userServiceModel.getEmail())
				.claim("surname1", userServiceModel.getSurname1())
				.claim("surname2", userServiceModel.getSurname2())
				.claim("address", userServiceModel.getAddress())
				.claim("DNI", userServiceModel.getDNI())
				.claim("phoneNumber1", userServiceModel.getPhoneNumber1())
				.claim("phoneNumber2", userServiceModel.getPhoneNumber2())
				//.claim("image", userDAO.getImage())
				.claim("is_dual", userServiceModel.getDual())
				.claim("firstLogin", userServiceModel.getFirstLogin())
				.claim("listRoles", userServiceModel.getRoles())
				.claim("department", userServiceModel.getDepartment())

	
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
		

	}

	public Boolean validateAccessToken(String token) {
		try {
			
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);

			
			return true;
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


	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	public String getUserEmail(String token) {
		return (String) parseClaims(token).get("email");
	}
	public int getUserId(String token) {
		return (int) parseClaims(token).get("id");
	}
	public String getUserName(String token) {
		return (String) parseClaims(token).get("name");
	}
	public String getUserSurname1(String token) {
		return (String) parseClaims(token).get("surname1");
	}
	public String getUserSurname2(String token) {
		return (String) parseClaims(token).get("surname1");
	}
	public String getUserAddress(String token) {
		return (String) parseClaims(token).get("address");
	}
	public String getUserDNI(String token) {
		return (String) parseClaims(token).get("DNI");
	}
	public int getUserPhone1(String token) {
		return (int) parseClaims(token).get("phoneNumber1");
	}
	public int getUserPhone2(String token) {
		return (int) parseClaims(token).get("phoneNumber2");
	}
	
	public Blob getUserImage(String token) {
		return (Blob) parseClaims(token).get("image");
	}
	public Boolean getUserDual(String token) {
		return (Boolean) parseClaims(token).get("is_dual");
	}
	public Boolean getFirstLogin(String token) {
		return (Boolean) parseClaims(token).get("firstLogin");
	}
	public List<RoleServiceModel> getUserRoles(String token) {
		return (List<RoleServiceModel>) parseClaims(token).get("listRoles");
	}
	public DepartmentServiceModel getUserDepartment(String token) {
		Gson gson = new Gson();
		String json = gson.toJson(parseClaims(token).get("department"));
	    return gson.fromJson(json, new TypeToken<DepartmentServiceModel>() {}.getType());

	}
	
	
	
	private Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}







}
