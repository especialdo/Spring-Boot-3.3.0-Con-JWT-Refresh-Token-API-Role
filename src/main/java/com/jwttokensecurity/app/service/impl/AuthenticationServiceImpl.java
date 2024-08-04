package com.jwttokensecurity.app.service.impl;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwttokensecurity.app.dto.JwtAuthenticationResponse;
import com.jwttokensecurity.app.dto.SiginRequest;
import com.jwttokensecurity.app.dto.SignUpRequest;
import com.jwttokensecurity.app.entities.Role;
import com.jwttokensecurity.app.entities.User;
import com.jwttokensecurity.app.repository.UserRepository;
import com.jwttokensecurity.app.service.AuthenticationService;
import com.jwttokensecurity.app.service.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final JWTService jwtService;

	@Override
	public User signUp(SignUpRequest signUpRequest) {
		User user = new User();
		
		user.setEmail(signUpRequest.getEmail());
		user.setFirstname(signUpRequest.getFirstName());
		user.setSecondName(signUpRequest.getSecondName());
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public JwtAuthenticationResponse siginin(SiginRequest siginRequest) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(siginRequest.getEmail(), 
				siginRequest.getPassword());
		
		authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		var user = userRepository.findByEmail(siginRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("invalid email or password"));
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.refreshToken(new HashMap<>(), user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		return jwtAuthenticationResponse;
	}

}
