# Car Rental Management System

A dual-mode Car Rental Management System with a modern web dashboard and a classic JavaFX desktop client. Both modes share the same robust backend services, data access layer, and BCrypt security. 

## 🌐 Live Demo

**Coming soon — Live deployment on Render**

`https://YOUR-RENDER-URL.onrender.com/`

## Features
- **Dual-Mode Architecture:** Run as a standalone Desktop App (JavaFX) or a cloud-hosted Web App (Spring Boot + HTML/JS).
- **Secure Administrator Login:** BCrypt password hashing for secure authentication across both modes.
- **Interactive Dashboards:** Real-time metrics on total customers, income, and available cars.
- **Car Fleet Management:** Complete CRUD operations for the vehicle inventory via REST API or Desktop.
- **Rental Processing:** Automated total price calculation based on dynamic rental duration algorithms.
- **Return Management:** Streamlined processing to check cars back in and update availability.

## Tech Stack
- **Languages:** Java 17 LTS, HTML, CSS, JavaScript
- **Backend:** Spring Boot (REST APIs)
- **Frontend/Desktop:** JavaFX 17 (FXML) & Vanilla Web UI
- **Database:** MySQL 8.x (Compatible with Aiven/Render DB)
- **Build Tool & Deployment:** Maven, Docker

## Architecture
The application uses a shared business logic layer:
```
[JavaFX Desktop]       [Web Browser]
       │                     │
       ▼                     ▼
[Controllers]         [REST Controllers]
       │                     │
       └─────────┬───────────┘
                 ▼
            [Services]
                 ▼
              [DAOs]
                 ▼
             [MySQL]
```

## Screenshots

### Web UI Screenshots (Spring Boot Backend)

#### Admin Login
![Web Login](screenshots/web-login.png)

#### Live Dashboard
![Web Dashboard](screenshots/web-dashboard.png)

#### Manage Car Inventory
![Web Cars](screenshots/web-cars.png)

#### Rent a Car
![Web Rent](screenshots/web-rent.png)

#### Manage Customers & Returns
![Web Customers & Returns](screenshots/web-customers.png)

### Desktop UI Screenshots (JavaFX)

#### Desktop App Login
![Desktop Login](screenshots/login%20page.png)

#### Desktop Dashboard Overview
![Desktop Dashboard](screenshots/Dashboard%20overview.png)

#### Desktop Manage Cars
![Desktop Manage Cars](screenshots/Manage%20Cars.png)

#### Desktop Rent a Car
![Desktop Rent a Car](screenshots/Rent%20a%20Car.png)

#### Desktop Customers & Returns
![Desktop Customers & Returns](screenshots/Customers%20&%20Returns.png)

## Run Locally (Dual Mode)

### 1. Database Setup
1. Create a MySQL database named `rentcar` (or configure a remote Aiven DB).
2. Execute `src/main/resources/database/schema.sql`.
   *Default Web/Desktop login: Username `admin` / Password `admin123`*

### 2. Environment Configuration
Copy `.env.example` to `.env` and set your credentials:
```env
DB_URL=jdbc:mysql://localhost:3306/rentcar
DB_USERNAME=root
DB_PASSWORD=your_password_here
PORT=8080
```

### 3. Run Web Mode (Spring Boot)
Starts the embedded web server on port 8080 (or your configured `PORT`).
```bash
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 mvn spring-boot:run
```
Visit `http://localhost:8080` in your browser.

### 4. Run Desktop Mode (JavaFX)
Launches the standalone JavaFX UI.
```bash
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 mvn javafx:run
```

## Deployment (Render & Docker)
The repository includes a multi-stage `Dockerfile` optimized for Render Web Services.

1. Create a new Web Service on Render.
2. Connect this GitHub repository.
3. Environment variables required in Render Dashboard:
   - `DB_URL` (e.g., Aiven MySQL connection string: `jdbc:mysql://host:port/defaultdb?sslMode=REQUIRED`)
   - `DB_USERNAME`
   - `DB_PASSWORD`
   *(Note: Render assigns `PORT` automatically, which Spring Boot detects).*

## Limitations
- This is a portfolio/demo-grade application. The web authentication uses basic local storage tokens rather than enterprise JWT/Spring Security.
- The desktop charts are placeholders, while the web dashboard statistics are real-time.