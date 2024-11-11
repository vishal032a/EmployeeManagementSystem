package com.incture.employeemanagementsystem.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "employees", uniqueConstraints = {
	    @UniqueConstraint(columnNames = "email"),
	    @UniqueConstraint(columnNames = "phone")
	})
public class Employee {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotNull(message = "Name cannot be null")
	    private String name;

	    @NotNull(message = "Department cannot be null")
	    private String department;

	    @NotNull(message = "Job title cannot be null")
	    private String jobTitle;

	    @NotNull(message = "Email cannot be null")
	    @Column(unique = true)
	    private String email;

	    @NotNull(message = "Phone cannot be null")
	    @Column(unique = true)
	    private String phone;

	    
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
		public String getJobTitle() {
			return jobTitle;
		}
		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
}
