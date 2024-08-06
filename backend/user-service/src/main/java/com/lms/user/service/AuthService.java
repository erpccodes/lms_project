package com.lms.user.service;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.lms.user.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {
	
	  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	 @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtUtil jwtUtil;

	    public String authenticate(String username, String password) throws AuthenticationException {
	    	logger.debug("Attempting to authenticate user: {}", username);
	    	try {
	        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
	        Authentication authentication = authenticationManager.authenticate(authenticationToken);
	        logger.debug("Authentication successful for user: {}", username);
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        return jwtUtil.generateToken(userDetails.getUsername());
	    	 } catch (AuthenticationException e) {
	             logger.error("Authentication failed for user: {}", username, e);
	             throw e;
	    	 }
	    }
}
