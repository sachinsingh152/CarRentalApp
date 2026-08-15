# Car Rental Management System

A robust web-based Car Rental Management System written in Java and powered by Spring Boot. This application simulates a complete car rental portal handling distinct operations for managing vehicles, tracking rentals, and calculating returns. It is powered by a relational MySQL database via JDBC.

## 🚀 Live Demo

[Live Application](#) *(Note: Because this is hosted on a free Render instance, the first request may take up to 50 seconds while the server wakes up from sleep.)*

## Features

**Admin Module:**
- Secure login for administrators using BCrypt hashing.
- Dashboard with real-time metrics (customers, income, available cars).

**Car Management Module:**
- Manage (Add, Update, Delete, View) vehicle inventory.
- Track pricing and availability status.

**Rental & Return Module:**
- Rent cars and automatically calculate totals based on duration.
- Process returns and securely track customer rental history.

## Screenshots

**Login Page**
![Login](screenshots/web-login.png)

**Admin Dashboard**
![Dashboard](screenshots/web-dashboard.png)

**Manage Cars**
![Manage Cars](screenshots/web-cars.png)

**Rent a Car**
![Rent a Car](screenshots/web-rent.png)

**Customers & Returns**
![Customers & Returns](screenshots/web-customers.png)

## Tech Stack

- **Backend:** Java 17, Spring Boot, Spring Web, JDBC
- **Frontend:** HTML5, CSS3, Vanilla JS
- **Database:** MySQL 8.x
- **Build/Container:** Maven, Docker

## Architecture

This project features a clean and modern web architecture:

- **Web Mode:** A Spring Boot REST API serving a modern Single Page Application (SPA) dashboard.
- **Database Layer:** Direct JDBC integration for lightweight, secure data queries.

## Local Setup

### 1. Database Configuration
Create a `.env` file in the root directory based on `.env.example`:

```env
DB_URL=jdbc:mysql://localhost:3306/rentcar
DB_USERNAME=root
DB_PASSWORD=your_password
PORT=8080
```
Import the `src/main/resources/database/schema.sql` file into your MySQL database to build the schema.

### 2. Running Web Mode
Compile and run the Spring Boot application:

```bash
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 mvn clean package -DskipTests
java -jar target/car-rental-app-1.0-SNAPSHOT.jar
```
Visit `http://localhost:8080` in your browser.

## Docker Setup

Build and run the multi-stage Docker container locally:

```bash
docker build -t car-rental-app .
docker run -p 8080:8080 -e DB_URL="jdbc:mysql://host.docker.internal:3306/rentcar" -e DB_USERNAME="root" -e DB_PASSWORD="password" car-rental-app
```

## Security Notes

- Environment variables (`.env`) are explicitly ignored in `.gitignore`.
- Source code contains no hardcoded production secrets.
- Administrator passwords are automatically hashed using BCrypt before storing in the database.

## Deployment

This application is Docker-ready and designed to be deployed to free hosting services such as Render.

**Preferred Architecture:** GitHub repository ➔ Render Web Service (Docker) ➔ Aiven MySQL.

*Note: Render requires applications to bind to 0.0.0.0 and read the PORT dynamically, which is natively supported in this project's Spring Boot configuration.*

## Project Structure

```
src/main/java/application/
├── web/          (REST APIs for the web layer)
├── service/      (Business logic and validation)
├── dao/          (Database queries)
src/main/resources/
├── static/       (Frontend HTML, CSS, and JS)
├── database/     (SQL schema dumps)
```

## Author

**Tarachand Jakhar**  
B.Tech Artificial Intelligence, SVNIT Surat  
GitHub: [TARACHANDJAKHAR](https://github.com/TARACHANDJAKHAR)