# Daily Expenses Sharing Application

## Introduction
The Daily Expenses Sharing Application is designed to help users track and share their daily expenses. Users can add expenses and split them using three methods: equal amounts, exact amounts, and percentage splits. The application manages user details, validates inputs, and generates downloadable balance sheets.

## Technologies Used
- **Java**: Programming language for backend development.
- **Spring Boot**: Framework for building the application.
- **MS SQL**: Database for storing user and expense data.
- **JUnit**: Testing framework for unit tests.
- **Mockito**: Library for mocking dependencies in tests.

## Features
- **User Management**: Users can create accounts with email, name, and mobile number.
- **Expense Management**: Users can add expenses and split them using:
  - **Equal Split**: Split the total expense equally among participants.
  - **Exact Split**: Specify the exact amount each participant owes.
  - **Percentage Split**: Specify the percentage each participant owes.
- **Balance Sheet**: Users can download a balance sheet showing individual and overall expenses.

## Setup and Installation
1. **Clone the Repository**:
   ```bash
   git clone [https://github.com/prasadneha22/Daily-Expenses-Sharing-Application.git]
   cd daily-expenses-sharing-app

2. **Setup Database:

Create a new MS SQL database (e.g., db_daily_expense).
Update the database connection properties in src/main/resources/application.properties:
      spring.datasource.url=jdbc:sqlserver://DESKTOP-G6CEHB1;databaseName=databaseName;;encrypt=true;trustServerCertificate=true;
      spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
      spring.datasource.username=Username
      spring.datasource.password=Password

3. **Build and Run the Application:

    -Ensure you have JDK and Maven installed.
    -Run the following command to build the project:
        mvn clean install
   
   -Start the application:
       mvn spring-boot:run
   
4. **Access the Application:
     Open your browser and navigate to http://localhost:8080.

## API Endpoints

Create User: POST /users/createUser
example data: 

      {
        "email": "neha.prasad@example.com",
        "name": "Neha Prasad",
        "mobileNumber": "1234567890"
    }

- Retrieve User Details: GET /users/{id}
- Expense Endpoints
    > Add Expense: POST /expenses
        dummy data:
          {
            "description": "Dinner at restaurant",
            "totalAmount": 3000,
            "participantIds": [1, 2] // List of user IDs
        }

- Download Balance Sheet: GET /expenses/balance-sheet


## Unit Tests
The application includes unit tests for the service layer to ensure the functionality is working as expected. Tests cover the following areas:

- Adding an expense.
- Calculating exact amounts.
- Calculating percentage amounts.
- Calculating total expenses.
"# Daily-Expenses-Sharing-Application" 
