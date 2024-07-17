package com.jwttokensecurity.app.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwttokensecurity.app.repository.UserRepository;
import com.jwttokensecurity.app.service.UserService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Getter
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	public UserDetailsService userDetailService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
			}
		};
	}

}
