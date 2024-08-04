package com.jwttokensecurity.app.service;

import com.jwttokensecurity.app.dto.JwtAuthenticationResponse;
import com.jwttokensecurity.app.dto.RefreshTokenRequest;
import com.jwttokensecurity.app.dto.SiginRequest;
import com.jwttokensecurity.app.dto.SignUpRequest;
import com.jwttokensecurity.app.entities.User;

public interface AuthenticationService {
	User signUp(SignUpRequest signUpRequest);
	
	JwtAuthenticationResponse siginin(SiginRequest siginRequest);
	
	JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
