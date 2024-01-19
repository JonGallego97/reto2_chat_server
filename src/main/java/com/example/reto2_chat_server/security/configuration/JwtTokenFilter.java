package com.example.reto2_chat_server.security.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.reto2_chat_server.security.user.service.UserServiceModel;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	@Autowired
	
	private JwtTokenUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(!hasAuthorizationBearer(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = getAccessToken(request);
		if(!jwtUtil.validateAccessToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		setAuthenticationContext(token, request);
		filterChain.doFilter(request, response);
		
	}
	
	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer ")) {

			return false;
		}
		return true;
	}
	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.split(" ")[1].trim();
		return token;
		
	}
	
	private void setAuthenticationContext(String token, HttpServletRequest request) {
		UserDetails userDetails = getUserDetails(token);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}
	
	private UserDetails getUserDetails(String token) {
		UserServiceModel userDetails = new UserServiceModel();
		userDetails.setId(jwtUtil.getUserId(token));
		userDetails.setEmail(jwtUtil.getUserEmail(token));
		userDetails.setName(jwtUtil.getUserName(token));
		userDetails.setSurname1(jwtUtil.getUserSurname1(token));
		userDetails.setSurname2(jwtUtil.getUserSurname2(token));
		userDetails.setDNI(jwtUtil.getUserDNI(token));
		userDetails.setAddress(jwtUtil.getUserAddress(token));
		userDetails.setPhoneNumber1(jwtUtil.getUserPhone1(token));
		userDetails.setPhoneNumber2(jwtUtil.getUserPhone2(token));
		//userDetails.setImage(jwtUtil.getUserImage(token));
		userDetails.setDual(jwtUtil.getUserDual(token));
		userDetails.setFirstLogin(jwtUtil.getFirstLogin(token));
		userDetails.setRoles(jwtUtil.getUserRoles(token));
		userDetails.setDepartment(jwtUtil.getUserDepartment(token));

		return userDetails;
	}

}
