package com.incture.employeemanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

	@GetMapping("/dashboard")
	public String adminDadhborad() {
		return "Welcome to manager dashboard";
	}
}
