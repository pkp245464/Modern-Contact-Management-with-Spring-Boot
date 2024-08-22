# Modern Contact Management with Spring Boot

## Overview

Modern Contact Management is a robust Spring Boot application designed to streamline contact management for users. This full-stack solution offers a range of features including manual account creation, Cloudinary image storage, comprehensive contact management, and secure OAuth2 authentication via GitHub and Google.

![Modern Contact Management](https://github.com/pkp245464/Modern-Contact-Management-with-Spring-Boot/raw/main/Project%20Screenshots/MainHomePage.jpg)

## Features

- **Contact Management**: Add and manage contacts with detailed information:
  - Name
  - Phone number
  - About section
  - Address
  - Profile image
- **Favorites**: Mark and organize favorite contacts
- **OAuth2 Authentication**: Secure login via GitHub or Google
- **Manual Account Creation**: Option to create and use a manual account
- **Cloud Image Storage**: Profile images securely stored on Cloudinary
- **Secure Database**: Reliable storage of contact and user data

## Tech Stack

- **Backend**: Spring Boot
- **Frontend**: Thymeleaf, Tailwind CSS
- **Database**: MySQL
- **Authentication**: Spring Security, OAuth2
- **Image Storage**: Cloudinary
- **Build Tool**: Maven

## Project Dependencies

- spring-boot-starter-data-jpa
- spring-boot-starter-thymeleaf
- spring-boot-starter-validation
- spring-boot-starter-web
- spring-boot-starter-security
- spring-boot-starter-oauth2-client
- spring-boot-starter-test
- spring-boot-devtools
- mysql-connector-j
- cloudinary-http44
- lombok

## Prerequisites

Before you begin, ensure you have the following installed:
- Java Development Kit (JDK)
- Apache Maven
- Node.js and npm
- MySQL Database
- IDE (e.g., Visual Studio Code, Eclipse, IntelliJ IDEA)

## Project Setup

1. **Java Setup**:
   - Install Java compiler and Java Development Kit

2. **Maven Setup**:
   - Install Apache Maven

3. **Database Configuration**:
   - Create a MySQL database
   - Configure database properties in `application.properties`

4. **Authentication Setup**:
   - Google Cloud Console:
     - Obtain Client ID and Client Secret
     - Set up required scopes (email, profile)
   - GitHub Console:
     - Obtain Client ID and Client Secret
     - Configure for email and profile access

5. **Cloudinary Setup**:
   - Create a Cloudinary account
   - Note your Cloud Name, API Key, and API Secret

6. **Frontend Setup**:
   - Install Node.js and npm

## Running the Application

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/modern-contact-management.git
   cd modern-contact-management
   ```

2. Configure application properties in `src/main/resources/application.properties`

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the Spring Boot application:
   - Use your IDE's run feature or
   - Execute: `mvn spring-boot:run`

5. In a separate terminal, run Tailwind CSS:
   ```
   npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --watch
   ```
6. Access the application:

The application will be accessible at the URL and port specified in your application.properties file.
If you haven't specified a port, Spring Boot typically defaults to http://localhost:8080
Check your console output when starting the application for the exact URL.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
