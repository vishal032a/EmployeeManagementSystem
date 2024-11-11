package com.incture.employeemanagementsystem.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(String message) {
		super(message);
	}
	
	public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
