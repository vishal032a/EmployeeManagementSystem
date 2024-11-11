package com.incture.employeemanagementsystem.exceptions;




import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	 // Handle IllegalArgumentException (e.g., employee being null)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle DataIntegrityViolationException (e.g., duplicate data like unique constraint violation)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>("Error: Duplicate data error - " + ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Handle ConstraintViolationException (e.g., null value violation for a non-nullable field)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>("Error: Constraint violation - " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle TransactionSystemException (e.g., errors in transaction processing)
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> handleTransactionSystemException(TransactionSystemException ex) {
        return new ResponseEntity<>("Error: Transaction system error - " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle JpaSystemException (e.g., issues with the JPA system, like persistence errors)
    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<String> handleJpaSystemException(JpaSystemException ex) {
        return new ResponseEntity<>("Error: Database system error - " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex){
    	return new ResponseEntity<>("Employee Not found - "+ ex.getMessage(),HttpStatus.NO_CONTENT);
    }
    

    // General exception handler (catch-all for unexpected errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
     
	
}
