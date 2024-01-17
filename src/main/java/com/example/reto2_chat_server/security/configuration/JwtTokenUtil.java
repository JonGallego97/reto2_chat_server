package com.example.reto2_chat_server.security.configuration;

import java.sql.Blob;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.reto2_chat_server.department.repository.DepartmentDAO;
import com.example.reto2_chat_server.model.Role;
import com.example.reto2_chat_server.security.user.repository.UserDAO;

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
				.setSubject(userDAO.getEmail())
				.setIssuer("ADT_DAM")
				.setExpiration(new Date(System.currentTimeMillis()+ EXPIRE_DURATION))
				.claim("id", userDAO.getId())
				.claim("name", userDAO.getName())
				.claim("surname1", userDAO.getSurname1())
				.claim("surname2", userDAO.getSurname2())
				.claim("address", userDAO.getAddress())
				.claim("DNI", userDAO.getDNI())
				.claim("phoneNumber1", userDAO.getPhoneNumber1())
				.claim("phoneNumber2", userDAO.getPhoneNumber2())
				//.claim("image", userDAO.getImage())
				.claim("dual", userDAO.getDual())
				.claim("firstLogin", userDAO.getFirstLogin())
				.claim("listRoles", userDAO.getListRoles())
				.claim("department", userDAO.getDepartment())

	
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
		return (Boolean) parseClaims(token).get("dual");
	}
	public Boolean getFirstLogin(String token) {
		return (Boolean) parseClaims(token).get("firstLogin");
	}
	public List<Role> getUserRoles(String token) {
	    Object rolesObject = parseClaims(token).get("listRoles");

	    if (rolesObject instanceof List<?>) {
	        List<?> rolesList = (List<?>) rolesObject;

	        if (!rolesList.isEmpty() && rolesList.get(0) instanceof Role) {
	            return (List<Role>) rolesObject;
	        }
	    }

	    return Collections.emptyList();
	}

	public DepartmentDAO getUserDepartment(String token) {
		return (DepartmentDAO) parseClaims(token).get("department");
	}
	
	
	
	private Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}







}
