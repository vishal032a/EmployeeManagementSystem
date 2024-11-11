package com.incture.employeemanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.incture.employeemanagementsystem.entities.User;
import com.incture.employeemanagementsystem.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@GetMapping
    public List<User> getAllUsers() {
		logger.info("Getting all the users ");
        return userService.getAllUsers();
    }
	@PostMapping
    public User addUser(@RequestBody User user) {
		logger.info("Adding a user : "+ user.toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.addUser(user);
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable Long id, @RequestBody User user) {
		logger.info("Updating a user with a ID: "+id);
    	User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
	 @DeleteMapping("/{id}")
	    public void deleteUser(@PathVariable Long id) {
		 logger.info("Deleting a user with a ID: "+id);
	        userService.deleteUser(id);
	    }
	
}
