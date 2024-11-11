package com.incture.employeemanagementsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.incture.employeemanagementsystem.dao.EmployeeRepository;
import com.incture.employeemanagementsystem.entities.Employee;
import com.incture.employeemanagementsystem.exceptions.EmployeeNotFoundException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
    	
    	 if (employee == null) {
    	        throw new IllegalArgumentException("Employee cannot be null");
    	    }
    	 
    	 return employeeRepository.save(employee);
		
        
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setJobTitle(updatedEmployee.getJobTitle());
        employee.setName(updatedEmployee.getName());
        employee.setPhone(updatedEmployee.getPhone());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
    	if(id == null) {
    		throw new IllegalArgumentException("id is null");
    	}
    	employeeRepository.deleteById(id);
        
    }
    public Employee getEmployeeById(Long id) {
    	return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }
    
    public List<Employee> searchEmployees(String department, String jobTitle, String phone) {
        return employeeRepository.findByParams(department, jobTitle, phone);
    }
    
    
}

