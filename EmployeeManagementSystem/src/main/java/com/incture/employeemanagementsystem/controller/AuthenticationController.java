package com.incture.employeemanagementsystem.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.incture.employeemanagementsystem.entities.AuthenticationRequest;
import com.incture.employeemanagementsystem.entities.AuthenticationResponse;
import com.incture.employeemanagementsystem.service.UserService;
import com.incture.employeemanagementsystem.util.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("api")
public class AuthenticationController {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;       // declaring Jwt Util
	
    @GetMapping("/login-success")
    public String loginSuccess(Authentication authentication) {
    	logger.info("Login Successfull");
        return "Login Successful! Welcome, " + authentication.getName();
    }
    
    
    
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
    	try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
			String jwt = jwtUtil.generateToken(userDetails.getUsername());     // generating and storing jwt token
			return ResponseEntity.ok(new AuthenticationResponse(jwt,null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new AuthenticationResponse(null,"Invalid username or password "+ e.getMessage()));
		}
    }
}