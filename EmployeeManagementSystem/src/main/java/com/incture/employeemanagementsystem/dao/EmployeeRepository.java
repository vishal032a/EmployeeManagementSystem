package com.incture.employeemanagementsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.incture.employeemanagementsystem.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	 @Query("SELECT e FROM Employee e WHERE "
	           + "(:department IS NULL OR e.department = :department) AND "
	           + "(:jobTitle IS NULL OR e.jobTitle = :jobTitle) AND "
	           + "(:phone IS NULL OR e.phone = :phone)")
	 
	List<Employee> findByParams(
				@Param("department") String department,
	            @Param("jobTitle") String jobTitle,
	            @Param("phone") String phone);
	
}

