package com.example.reto2_chat_server.security.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.reto2_chat_server.department.service.DepartmentServiceModel;
import com.example.reto2_chat_server.model.Role;
import com.example.reto2_chat_server.model.RoleServiceModel;
import com.example.reto2_chat_server.security.configuration.JwtTokenUtil;
import com.example.reto2_chat_server.security.user.repository.UserDAO;
import com.example.reto2_chat_server.security.user.service.UserServiceImpl;
import com.example.reto2_chat_server.security.user.service.UserServiceModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserServiceImpl userService;

	@Autowired
	JwtTokenUtil tokenUtil;

	@PostMapping("auth/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
		try {
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
					);
			UserDAO userDAO = (UserDAO) authentication.getPrincipal();
			UserServiceModel userServiceModel = convertFromDAOtoService(userDAO);

			String accessToken = tokenUtil.generateAccessToken(userServiceModel);
			AuthResponse response = new AuthResponse(userServiceModel.getEmail(), accessToken);
			return ResponseEntity.ok().body(response);
		} catch (BadCredentialsException ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}


	}
	
	@PutMapping("auth/register")
	public ResponseEntity<?> register(Authentication authentication, @RequestBody AuthPutModel request){
		UserServiceModel userDetails = (UserServiceModel) authentication.getPrincipal();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCryptVersion.$2Y,12);
		String encodedPassword = passwordEncoder.encode(request.getNewPassword());
		request.setNewPassword(encodedPassword);
		request.setFirstLogin(false);
		request.setDual(userDetails.getDual());
		request.setAddress(userDetails.getAddress());


		UserServiceModel response = userService.updateUserFirstLogin(request);
		
		return new ResponseEntity<>(response, HttpStatus.OK);

		
	}

	@GetMapping("/auth/me")
	public ResponseEntity<?>getUserInfo(Authentication authentication){
			UserServiceModel userDetails = (UserServiceModel) authentication.getPrincipal();			
			return ResponseEntity.ok().body(userDetails);
		
	}
	


	public UserServiceModel convertFromDAOtoService(UserDAO userDAO) {

		
		List <RoleServiceModel> listRoles = new ArrayList<RoleServiceModel>();

		UserServiceModel response = new UserServiceModel(
				userDAO.getId(),
				userDAO.getEmail(),
				userDAO.getName(),
				userDAO.getSurname1(),
				userDAO.getSurname2(),
				userDAO.getDNI(),
				userDAO.getAddress(),
				userDAO.getPhoneNumber1(),
				userDAO.getPhoneNumber2(),
				userDAO.isDual(),
				userDAO.getFirstLogin(),
				userDAO.getImage());

		try {
			for(Role role : userDAO.getListRoles()) {
				RoleServiceModel roleServiceModel = new RoleServiceModel(role.getId(), role.getName());
				listRoles.add(roleServiceModel);


			}
			response.setRoles(listRoles);

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if(userDAO.getDepartment() != null) {
			DepartmentServiceModel departmentServiceModel = new DepartmentServiceModel(
					userDAO.getDepartment().getId(),
					userDAO.getDepartment().getName());
			response.setDepartment(departmentServiceModel);
		}

		return response;


	}

}
