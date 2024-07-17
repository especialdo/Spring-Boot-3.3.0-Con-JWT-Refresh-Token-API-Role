package com.jwttokensecurity.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwttokensecurity.app.entities.Role;
import com.jwttokensecurity.app.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	
	User findByRole(Role role);
}
