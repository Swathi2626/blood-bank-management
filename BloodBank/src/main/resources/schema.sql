-- Create the central database
CREATE DATABASE IF NOT EXISTS blood_bank_db;
USE blood_bank_db;

-- User authentication
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
) ENGINE=InnoDB;

-- Blood inventory
CREATE TABLE IF NOT EXISTS blood_inventory (
    unit_id INT AUTO_INCREMENT PRIMARY KEY,
    blood_group VARCHAR(20) NOT NULL,
    component VARCHAR(20) NOT NULL,
    donor_phone VARCHAR(20),
    collection_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE'
) ENGINE=InnoDB;

-- Patients
CREATE TABLE IF NOT EXISTS patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100) NOT NULL,
    blood_group VARCHAR(20) NOT NULL,
    contact_number VARCHAR(20),
    hospital_name VARCHAR(100)
) ENGINE=InnoDB;

-- Reservations
CREATE TABLE IF NOT EXISTS reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    unit_id INT,
    reservation_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'RESERVED',
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (unit_id) REFERENCES blood_inventory(unit_id)
) ENGINE=InnoDB;

-- Hospital blood requests
CREATE TABLE IF NOT EXISTS hospital_requests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    hospital_name VARCHAR(100) NOT NULL,
    blood_group VARCHAR(20) NOT NULL,
    component VARCHAR(20) NOT NULL,
    units_requested INT NOT NULL,
    urgency_level VARCHAR(20),
    status VARCHAR(20) DEFAULT 'PENDING'
) ENGINE=InnoDB;

-- Donors
CREATE TABLE IF NOT EXISTS donors (
    donor_id INT AUTO_INCREMENT PRIMARY KEY,
    donor_name VARCHAR(100) NOT NULL,
    blood_group VARCHAR(20) NOT NULL,
    phone_number VARCHAR(20) UNIQUE,
    age INT,
    gender VARCHAR(10)
) ENGINE=InnoDB;

-- Donation records
CREATE TABLE IF NOT EXISTS donation_records (
    donation_id INT AUTO_INCREMENT PRIMARY KEY,
    donor_id INT,
    donation_date DATE NOT NULL,
    quantity_ml INT DEFAULT 350,
    FOREIGN KEY (donor_id) REFERENCES donors(donor_id)
) ENGINE=InnoDB;
