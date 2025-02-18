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
- **Clients** can:
    - Publish new projects
    - View their active projects
    - Review freelancer applications for their projects
    - View outdated projects and delete them
    - Hire freelancers
    - Manage their profile (view and update personal details)
- **Freelancers** can:
    - Browse available projects
    - Submit applications for one or more projects
    - View their applications and check their status (Approved, Rejected, Under Review)
    - Manage their profile (view and update personal details)
- **Admins** can:
    - Confirm new project submissions from clients
    - Delete rejected or invalid projects 
    - Verify freelancer profiles
    - Delete freelancer profiles if necessary
    - Manage project publication requests
    - View project assignments (engagements)
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
git clone https://github.com/chrisklmt/DS-Project.git
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
