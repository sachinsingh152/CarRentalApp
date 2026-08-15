-- Database Schema for Car Rental Management System

-- Create admin table (modified to support longer BCrypt hashes)
CREATE TABLE IF NOT EXISTS admin (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

-- Seed default admin account (username: admin, password: admin123)
-- The password hash was generated using BCrypt with a standard workload of 10.
INSERT IGNORE INTO admin (username, password) VALUES ('admin', '$2a$10$vzC6mtI2geLb.Pyci.AE.uF0ImcqE/OKmlMMnbmIQ7HMc3GEoka3i');

-- Create car table
CREATE TABLE IF NOT EXISTS car (
    car_id VARCHAR(50) PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    date DATE
);

-- Create customer table
CREATE TABLE IF NOT EXISTS customer (
    car_id VARCHAR(50),
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    gender VARCHAR(20),
    total DECIMAL(10,2),
    date_rent DATE,
    date_return DATE,
    status VARCHAR(50)
);
