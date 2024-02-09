package com.example.reto2_chat_server.security.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserDAO, Integer> {
	Optional <UserDAO> findByEmail(String email);
	
	@Query("SELECT u.email FROM UserDAO u JOIN u.listRoles r WHERE r.id = 2")
    List<String> findEmailsByRoleId();

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserDAO u JOIN u.listRoles r WHERE u.id = :userId AND r.id IN (1, 2)")
	boolean canSend(@Param("userId") int userId);


}
