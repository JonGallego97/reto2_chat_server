package com.example.reto2_chat_server.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.csrf( csrf -> csrf.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests((requests) -> requests
				//TODO Revisar si hay que permitir alguno más
				
				//.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
				//.requestMatchers(HttpMethod.GET, "/api/auth/me").permitAll()




				//.requestMatchers(HttpMethod.GET,"/api/sockets/chats").permitAll()
				//.requestMatchers(HttpMethod.GET,"/api/sockets/send-message").permitAll()
				//.requestMatchers(HttpMethod.POST,"/api/sockets//join-chat/{chat}/{idUser}").permitAll()
				

				
				.anyRequest().permitAll()
				
				).exceptionHandling((exceptionHandling) ->
				
				exceptionHandling.authenticationEntryPoint((requests,response,ex) ->{
					
					if(response.getStatus()<400) {
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					}
				})
						)
		.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
}
