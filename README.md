# 🚗 Car Rental Management System

Car Rental Management System built with Spring Boot and MySQL. The application handles customers, cars, rentals, and returns through a modern web interface.

## Live Demo

[Open Live Demo](#)

## Screenshots

### Web Interface
![Login](screenshots/web-login.png)
![Dashboard](screenshots/web-dashboard.png)
![Manage Cars](screenshots/web-cars.png)
![Rent a Car](screenshots/web-rent.png)
![Customers & Returns](screenshots/web-customers.png)

## Features

- Secure login for administrators.
- Dashboard with real-time metrics (customers, income, available cars).
- Add, update, delete, and view cars.
- Rent cars and calculate totals automatically.
- Return cars and track customer history.

## Tech Stack

- Java 17
- MySQL
- JDBC
- Maven
- Spring Boot
- HTML/JavaScript (Web Frontend)
- Docker

## Project Structure

```
├── pom.xml
├── Dockerfile
├── src/
│   ├── main/java/application/
│   │   ├── config/       (Database config)
│   │   ├── dao/          (Database queries)
│   │   ├── model/        (Data structures)
│   │   ├── service/      (Business logic)
│   │   ├── web/          (Spring Boot REST API)
│   │   └── CarRentalWebApplication.java
│   └── main/resources/
│       ├── application.properties
│       └── static/       (HTML, JS, CSS for web)
└── screenshots/
```

## Database

The MySQL database `rentcar` uses three tables:
- `admin`: Stores administrator login credentials (hashed with BCrypt).
- `car`: Stores vehicle inventory, pricing, and availability status.
- `customer`: Stores customer details, rental dates, and totals.

## Running Locally

First, execute `src/main/resources/database/schema.sql` on your MySQL server and configure your `.env` file based on `.env.example`.

Run the Spring Boot web server on port 8080:

```bash
mvn spring-boot:run
```

Visit `http://localhost:8080` in your browser.

## Docker

Build and run the web version using Docker:

```bash
docker build -t car-rental-app .
docker run --rm -p 8080:8080 -e PORT=8080 --network host -e DB_URL=jdbc:mysql://127.0.0.1:3307/rentcar -e DB_USERNAME=root -e DB_PASSWORD=your_password car-rental-app
```

## Architecture

**Controller (Spring REST)** → **Service** → **DAO** → **MySQL Database**

## Authentication

Passwords are encrypted using BCrypt before storing in the database.

## Author

**Tarachand Jakhar**
B.Tech Artificial Intelligence, SVNIT Surat

GitHub: [TARACHANDJAKHAR](https://github.com/TARACHANDJAKHAR)