package com.example.reto2_chat_server.security.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.reto2_chat_server.security.user.service.UserServiceModel;

public interface UserRepository extends CrudRepository<UserDAO, Integer> {
	Optional <UserDAO> findByEmail(String email);
	
	@Query("SELECT u.email FROM UserDAO u JOIN u.listRoles r WHERE r.id = 2")
    List<String> findEmailsByRoleId();

}
