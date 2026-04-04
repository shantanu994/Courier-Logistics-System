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

- **Customer Management** - Add, update, view, and manage customer information
- **Employee Management** - Manage employees with different roles (Admin, Manager, Courier)
- **Shipment Tracking** - Track shipments in real-time with status updates
- **Tracking History** - Complete history of each shipment's status changes
- **Billing System** - Automated billing with tax calculations and payment tracking
- **Role-based Access Control** - Different permissions based on user role
- **Multiple Package Types** - Support for Document, Parcel, Fragile, and Heavy categories
- **Payment Status Tracking** - Monitor payment status for each shipment
- **Login System** - Secure login panel for user authentication
- **Dashboard UI** - Comprehensive UI panels for all major features

## Tech Stack

- **Frontend:** Java Swing (GUI framework)
- **Backend:** Java (Object-Oriented Design with DAO pattern)
- **Database:** MySQL 8.0 or higher
- **Database Driver:** MySQL Connector/J (JDBC)
- **JDK:** 11 or higher
- **Architecture:** MVC with DAO pattern
- **Design Patterns:** Data Access Object (DAO), Singleton (for DB Connection)

## Project Structure

```
Courier-Logistics-System/
├── src/com/courier/
│   ├── Main.java                    # Application entry point
│   ├── testd.java                   # Test/debug file
│   ├── dao/                         # Data Access Objects
│   │   ├── BillingDAO.java
│   │   ├── BillingDAOImpl.java
│   │   ├── CustomerDAO.java
│   │   ├── CustomerDAOImpl.java
│   │   ├── EmployeeDAO.java
│   │   ├── EmployeeDAOImpl.java
│   │   ├── ShipmentDAO.java
│   │   └── ShipmentDAOImpl.java
│   ├── db/                          # Database Connection
│   │   └── DBConnection.java
│   ├── model/                       # Data Model Classes
│   │   ├── Bill.java
│   │   ├── Customer.java
│   │   ├── Employee.java
│   │   ├── Shipment.java
│   │   └── TrackingHistory.java
│   ├── ui/                          # User Interface
│   │   ├── LoginPanel.java
│   │   ├── MainFrame.java
│   │   └── panels/
│   │       ├── BillingPanel.java
│   │       ├── CustomerPanel.java
│   │       ├── EmployeePanel.java
│   │       └── ShipmentPanel.java
│   └── util/                        # Utilities
│       └── DatabaseException.java
├── bin/                             # Compiled classes
├── lib/                             # External libraries
├── database/
│   └── database.sql                 # Database schema & sample data
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

After starting the application:

1. **Login Panel** - Authenticate with your employee credentials
2. **Main Frame** - Navigate to different modules using the menu
3. **Customer Panel** - Manage customer information
4. **Employee Panel** - View and manage employee records
5. **Shipment Panel** - Create and track shipments in real-time
6. **Billing Panel** - Generate and manage invoices

The database comes with sample data:

- 3 employees (1 Admin, 1 Manager, 1 Courier)
- 3 sample customers
- Sample shipments and billing records

Use these to test the complete workflow of shipment tracking and billing features.

## Status

Project development progress:

- Database schema: ✓ Done
- Models: ✓ Done
- Database connection: ✓ Done
- DAO layer (Customer, Employee, Shipment, Billing): ✓ Done
- UI components (Login, Main Frame, Panels): ✓ In Progress
- Business logic integration: In Progress
- Testing: In Progress

## License

MIT License - Copyright 2026 Shantanu K
