# EmployeeManagementSystem
The **Employee Management System** is a Spring Boot application that provides a REST API for managing employee information. It includes functionalities for adding, updating, retrieving, and deleting employees, along with user authentication and role-based access control.

## 2. Technologies Used

- **Backend Framework**: Spring Boot
- **Database**: MySQL (or any SQL-compatible database)
- **Security**: Spring Security with BCrypt for password encryption
- **Build Tool**: Maven
- **IDE**: Eclipse or IntelliJ IDEA
- **API Testing**: Postman
- **Version Control**: Git

## 3. Project Setup

### Prerequisites

- JDK 11 or higher
- MySQL or any other SQL-compatible database
- Maven

### Setup Instructions

1. Clone the project repository:
    
    ```bash
    bash
    Copy code
    git clone https://github.com/your-repo/employee-management-system.git
    cd employee-management-system
    
    ```
    
2. Configure MySQL Database:
    - Create a MySQL database, e.g., `employee_db`.
    - Update `application.properties` with your database connection details:
        
        ```
        properties
        Copy code
        spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
        spring.datasource.username=your_db_username
        spring.datasource.password=your_db_password
        
        ```
        
3. Run the application:
    
    ```bash
    bash
    Copy code
    mvn spring-boot:run
    
    ```
    
4. Test the API using Postman or any other API testing tool.

## 4. Application Architecture

The application follows a layered architecture with the following layers:

- **Controller Layer**: Contains REST API endpoints.
- **Service Layer**: Contains business logic.
- **Repository Layer**: Communicates with the database.
- **Entity Layer**: Represents database tables.

## 5. Functional Modules

### Employee Module

- Add, update, retrieve, and delete employee records.
- Search employees by multiple parameters.

### User Authentication Module

- User registration with encrypted passwords.
- Role-based access control.

## 6. API Endpoints

### Employee Endpoints

| Endpoint | Method | Description | Who Can Access |
| --- | --- | --- | --- |
| `/api/employees` | POST | Add a new employee | MANAGER |
| `/api/employees` | GET | Get all employees | MANAGER |
| `/api/employees/{id}` | GET | Get employee by ID | MANAGER |
| `/api/employees/{id}` | PUT | Update employee by ID | MANAGER |
| `/api/employees/{id}` | DELETE | Delete employee by ID | MANAGER |

### User Endpoints

| Endpoint | Method | Description | Who Can Access |
| --- | --- | --- | --- |
| `/api/user` | POST | Register a new user | Admin |
| `/api/authenticate` | POST | User login | Everyone  |

## 7. Security Configuration

The application uses **Spring Security** for authentication and authorization.

- **BCrypt Password Encoding** is used for encrypting user passwords.
- **Role-based access control** ensures that only authenticated users can access specific endpoints.

```java

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

```

## 8. Error Handling

Custom error handling is implemented in a global exception handler using the `@ControllerAdvice` annotation to handle validation errors and other exceptions gracefully.

### Example

```java

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
        return new ResponseEntity<>("Error: " + ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
     
	
}

```

## 9. Custom Exception Handling

Custom exceptions improve error readability and user experience. Below are some of the custom exceptions used in this application:

- **EmployeeNotFoundException**: Thrown when an employee is not found.
- UserNotFoundException: Thrown when an User is not found.

## 10. Database Design

### Tables

### Employee Table

| Field | Type | Constraints |
| --- | --- | --- |
| id | BIGINT | Primary Key |
| name | VARCHAR(100) | NOT NULL |
| department | VARCHAR(100) | NOT NULL |
| jobTitle | VARCHAR(100) | NOT NULL |
| email | VARCHAR(100) | UNIQUE, NOT NULL |
| phone | VARCHAR(15) | UNIQUE, NOT NULL |

### User Table

| Field | Type | Constraints |
| --- | --- | --- |
| id | BIGINT | Primary Key |
| username | VARCHAR(100) | UNIQUE, NOT NULL |
| password | VARCHAR(255) | NOT NULL |
| role | VARCHAR(50) | NOT NULL |

## 11. Deployment

### Local Deployment

- The application can be run locally with `mvn spring-boot:run`.

### Server Deployment

1. Package the application:
    
    ```bash
    bash
    Copy code
    mvn clean package
    
    ```
    
2. Deploy the generated JAR file to your server.

## 13. Future Improvements

- Implement user roles with finer-grained permissions.
- Add pagination and filtering to employee search functionality.
- Add Swagger for API documentation.
