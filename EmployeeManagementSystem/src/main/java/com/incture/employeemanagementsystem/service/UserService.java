package com.incture.employeemanagementsystem.service;




import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.incture.employeemanagementsystem.dao.UserRepository;
import com.incture.employeemanagementsystem.entities.User;
import com.incture.employeemanagementsystem.exceptions.UserNotFoundException;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder; 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("User not found with username: "+username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
	} 
	
	 public List<User> getAllUsers() {
	        return this.userRepository.findAll();
	    }

    public User addUser(User user) {
    	
    	 if (user == null) {
    	        throw new IllegalArgumentException("user cannot be null");
    	    }
    	 
    	 return userRepository.save(user);
    }
    
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Employee not found"));
        user.setUsername(updatedUser.getUsername());
        String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(updatedUser.getRole());
        
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
    	if(id == null) {
    		throw new IllegalArgumentException("id is null");
    	}
    	userRepository.deleteById(id);
    }


}
