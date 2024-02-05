package com.example.reto2_chat_server.security.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.reto2_chat_server.security.user.controller.AuthPutModel;
import com.example.reto2_chat_server.security.user.repository.UserDAO;
import com.example.reto2_chat_server.security.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(username)
				.orElseThrow(
						() -> new UsernameNotFoundException(username + " Not Found"));
	}
	
	public UserServiceModel updateUserFirstLogin(AuthPutModel request) {
		UserDAO user =(UserDAO) loadUserByUsername(request.getEmail());
	        UserDAO existingUserDAO = fromAuthPutModelToDAO(request);
	        existingUserDAO.setListRoles(user.getListRoles());
	        existingUserDAO.setDepartment(user.getDepartment());

	        userRepository.save(existingUserDAO);
	        return existingUserDAO.convertFromDAOtoService(existingUserDAO);
	    
	}

	
	public UserDAO fromAuthPutModelToDAO(AuthPutModel request) {
		UserDAO response = new UserDAO(
				request.getId(),
				request.getEmail(),
				request.getNewPassword(), 
				request.getName(), 
				request.getSurname1(), 
				request.getSurname2(), 
				request.getDni(),
				request.getAddress(),
				request.getPhone1(), 
				request.getPhone2(),
				request.getPhoto(),
				request.getDual(),
				request.getFirstLogin());
		
		return response;
	}

	

}
