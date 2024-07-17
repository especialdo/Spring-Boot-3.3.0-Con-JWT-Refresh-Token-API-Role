package com.jwttokensecurity.app.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
	UserDetailsService userDetailService();
}
