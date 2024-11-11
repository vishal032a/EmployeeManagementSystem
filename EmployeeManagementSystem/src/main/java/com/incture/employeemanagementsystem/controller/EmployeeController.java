package com.incture.employeemanagementsystem.controller;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incture.employeemanagementsystem.entities.Employee;
import com.incture.employeemanagementsystem.service.EmployeeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
    	logger.info("Adding a new Employee: " + employee.toString());
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
    	logger.info("Updating user with ID: " + id);
    	Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }
    
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
    	logger.info("Deleting a employee with a ID: " + id);
        employeeService.deleteEmployee(id);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
    	logger.info("getting all the employees");
        return employeeService.getAllEmployees();
    }
    
    @GetMapping
    public Employee getEmployeeById(@PathVariable Long id) {
    	return employeeService.getEmployeeById(id);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) String phone) {
    	logger.info("Searching employee with parameters : "+ department +","+jobTitle+","+phone);
        List<Employee> employees = employeeService.searchEmployees(department, jobTitle, phone);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

}

