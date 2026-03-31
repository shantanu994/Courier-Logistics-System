# Courier Logistics System

A Java Swing + JDBC project for managing courier operations. Built using Java, Swing for the UI, and MySQL for the database.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Database](#database)
- [Installation](#installation)
- [Configuration](#configuration)
- [How to Run](#how-to-run)
- [Usage](#usage)
- [Status](#status)
- [License](#license)

## Features

- Customer management (add, update, view customers)
- Employee management with different roles (Admin, Manager, Courier)
- Shipment tracking system
- Tracking history for each shipment
- Billing system with tax calculations
- Role-based access control
- Multiple package types (Document, Parcel, Fragile, Heavy)
- Payment status tracking

## Tech Stack

- Frontend: Java Swing
- Backend: Java
- Database: MySQL
- JDBC Driver: MySQL Connector/J
- JDK: 11 or higher

## Project Structure

```
Courier-Logistics-System/
├── src/com/courier/
│   ├── Main.java
│   ├── dao/
│   │   ├── CustomerDAO.java
│   │   ├── CustomerDAOImpl.java
│   │   ├── EmployeeDAO.java
│   │   ├── EmployeeDAOImpl.java
│   │   ├── ShipmentDAO.java
│   │   └── ShipmentDAOImpl.java
│   ├── db/
│   │   ├── DBConnection.java
│   │   └── TestConnection.java
│   ├── model/
│   │   ├── Bill.java
│   │   ├── Customer.java
│   │   ├── Employee.java
│   │   ├── Shipment.java
│   │   └── TrackingHistory.java
│   ├── ui/panels/
│   └── util/
│       └── DatabaseException.java
├── bin/
├── lib/
├── database/
│   └── database.sql
├── README.md
└── LICENSE
```

## Database

The project has 5 main tables:

**customers** - Customer information (name, email, phone, address, city, state, pincode)

**employees** - Employee records (name, email, phone, role, salary, hire_date, status)

**shipments** - Shipment tracking (tracking_number, sender_id, receiver_id, courier_id, weight, status, estimated_delivery, etc)

**tracking_history** - Updates for each shipment (shipment_id, status, location, remarks)

**billing** - Bill information (shipment_id, customer_id, amounts, payment_status, payment_method)

## Installation

### What you need:

- Java JDK 11 or higher
- MySQL Server 8.0 or higher
- MySQL JDBC driver (in lib/ folder)

### Steps:

1. Install and start MySQL

2. Create the database:

```bash
mysql -u root -p courier_logistics_system < database/database.sql
```

3. Update the database credentials in `src/com/courier/db/DBConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/courier_logistics_system";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

## Configuration

Edit `DBConnection.java` to set your database connection:

- Host: localhost
- Port: 3306
- Database: courier_logistics_system
- User: root
- Password: your password

## How to Run

### Command line:

```bash
cd Courier-Logistics-System

# Compile
javac -d bin src/com/courier/**/*.java

# Run
java -cp bin:lib/mysql-connector-java.jar com.courier.Main
```

### Using IDE:

1. Open the project in your IDE
2. Add MySQL JDBC driver to libraries
3. Set database connection in DBConnection.java
4. Run Main.java

## Usage

The database comes with sample data:

- 3 employees (1 admin, 1 manager, 1 courier)
- 3 sample customers

You can use these to test the shipment tracking and billing features.

## Status

Still working on this project:

- Database schema: Done
- Models: Done
- Database connection: Done
- DAO layer (Customer, Employee, Shipment): Done
- DAO layer: In progress
- UI components: Still to do
- Business logic: Still to do
- Testing: Still to do

## License

MIT License - Copyright 2026 Shantanu K
