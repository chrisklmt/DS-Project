# Freelancer Project Management System

---

## Overview
This project is a Freelancer Project Management
System that allows clients to create and manage
projects while freelancers can browse and apply
for available tasks. Admins oversee the platform
by verifying users and managing assignments.

## Features
- Authentication & Authorization
- User login & registration (Client, Freelancer, Admin)
- Role-based access control with Spring Security

### Project Management
- Clients can create, edit, and manage projects
- Freelancers can browse and apply for projects
- Admins approve project listings and assignments

### Freelancer Management
- Freelancers submit applications for projects
- Clients review applications and assign freelancers
- Admins verify freelancer profiles

### Admin Capabilities
- Review & approve projects
- Manage disputes between clients and freelancers
- Remove invalid projects and requests

---

### Tech Stack
- <b>Backend:</b> Spring Boot (Java), Hibernate (JPA), MySQL/PostgreSQL
- <b>Frontend:</b> Thymeleaf (integrated template engine)
- <b>Security:</b> Spring Security (JWT-based authentication)
- <b>Build Tools:</b> Maven / Gradle

---

### 1. Installation & Setup
- Clone the Repository
```sh
git clone https://github.com/chrisklmt/DS-Project2024.git
cd /to/project/folder
```

### 2. Configure the Database
Modify the application.properties file with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://cu53tvpu0jms73fe7edg-a.frankfurt-postgres.render.com:5432/dsdb_gwq1
spring.datasource.username=dsuser
spring.datasource.password=ez5fuekyswnNelefC8mOif0s6ZpVUsmf
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build & Run the Application
```sh
mvn clean install
mvn spring-boot:run
```

---

## API Endpoints

### Full API documentation is available in the project report.

### All users have password "user", and admin has password "admin"
### CLIENTS:     "sunny"     ,  "bluefox"
### FREELANCERS: "happystar" ,  "coolbuddy" 
