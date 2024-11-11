package com.incture.employeemanagementsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incture.employeemanagementsystem.entities.User;





public interface UserRepository extends JpaRepository <User,Long>{
	User findByUsername(String username);
}
