package com.incture.employeemanagementsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.incture.employeemanagementsystem.AppConfig;
import com.incture.employeemanagementsystem.JwtRequestFilter;
import com.incture.employeemanagementsystem.service.UserService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserService  userService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired 
	private AppConfig appConfig;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                				.requestMatchers("/api/admin","/api/admin/**").hasRole("ADMIN")
                				.requestMatchers("/api/Manager","/api/Manager/**").hasRole("MANAGER")
                                .requestMatchers("/api/employees", "/api/employees/**").hasRole("MANAGER")
                                .requestMatchers("/api/user","api/user/**").hasRole("ADMIN")
                                .requestMatchers("/api/authenticate").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session-> session
                		.sessionCreationPolicy(SessionCreationPolicy.STATELESS));       // so that no session is created and each request treated independently and each time token is get checked 
                http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);



		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user =
//			 User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("password")
//				.roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userService);
		daoAuthenticationProvider.setPasswordEncoder(appConfig.passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
}
