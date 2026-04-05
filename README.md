# 📦 Courier Logistics System

A comprehensive **Java Swing + MySQL** desktop application for managing courier and logistics operations. This system streamlines customer management, employee coordination, shipment tracking, and billing operations through an intuitive graphical interface.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Database Setup](#database-setup)
- [Configuration](#configuration)
- [How to Run](#how-to-run)
- [Usage Guide](#usage-guide)
- [Architecture](#architecture)
- [Component Descriptions](#component-descriptions)
- [Troubleshooting](#troubleshooting)
- [License](#license)

---

## 🎯 Overview

The Courier Logistics System is a full-featured desktop application designed to manage all aspects of courier delivery operations. Built with clean architecture using the **DAO pattern** and **MVC principles**, it provides:

- Complete customer and employee information management
- Real-time shipment tracking with status updates
- Comprehensive billing and payment tracking
- Full audit trail via tracking history
- Role-based user authentication (Admin, Manager, Courier)

---

## ✨ Features

### **🧑‍💼 Customer Management**

- Add, update, delete, and search customer records
- Store complete contact information and delivery addresses
- Email and phone validation
- View all customers in a sortable, filterable table

### **👨‍💻 Employee Management**

- Register and manage employees with role assignment
- Support for three roles: **Admin**, **Manager**, **Courier**
- Track employment status and salary information
- Hire date and role-based tracking

### **📦 Shipment Management**

- Create and track shipments with unique tracking numbers
- Support multiple package types: **Document**, **Parcel**, **Fragile**, **Heavy**
- Track sender and receiver information
- Monitor status progression: **Pending → Picked Up → In Transit → Out for Delivery → Delivered**
- Assign couriers to shipments
- Weight and delivery date tracking

### **📍 Tracking History**

- Complete audit trail of all shipment status changes
- Location tracking for each milestone
- Historical remarks and notes for tracking progression
- Timestamp tracking for all updates

### **💳 Billing System**

- Automatic bill generation for shipments
- Payment tracking: **Pending**, **Paid**, **Overdue**
- Multiple payment methods: **Cash**, **Card**, **UPI**, **Net Banking**
- Automatic tax calculations
- Payment date reconciliation

### **🔐 Security & Authentication**

- Secure login with credential validation
- Role-based access control (RBAC)
- Session management for data integrity

### **📊 Dashboard & Reports**

- Professional tabular views for all entities
- Real-time data updates
- Search and filter capabilities
- Organized UI panels for each module

---

## 🛠️ Tech Stack

| Component           | Technology                 |
| ------------------- | -------------------------- |
| **Frontend**        | Java Swing (GUI Framework) |
| **Backend**         | Java 11+ (OOP, JDBC)       |
| **Database**        | MySQL 8.0+                 |
| **Database Driver** | MySQL Connector/J (JDBC)   |
| **JDK Version**     | 11 or higher               |
| **Architecture**    | MVC Pattern                |
| **Design Patterns** | DAO, Singleton, Observer   |
| **Build Tool**      | Java Compiler (javac)      |

---

## 📂 Project Structure

```
Courier-Logistics-System/
│
├── README.md                        # Project documentation
├── LICENSE                          # MIT License
│
├── bin/                             # Compiled .class files
│   └── com/courier/...              # Compiled Java bytecode
│
├── database/
│   └── database.sql                 # Complete database schema & initialization
│
├── lib/                             # External libraries
│   └── mysql-connector-java-*.jar   # MySQL JDBC Driver
│
└── src/com/courier/
    │
    ├── Main.java                    # Application entry point
    │
    ├── model/                       # Entity/Data Models
    │   ├── Customer.java            # Customer entity
    │   ├── Employee.java            # Employee entity
    │   ├── Shipment.java            # Shipment entity
    │   ├── Bill.java                # Billing entity
    │   └── TrackingHistory.java     # Tracking history entity
    │
    ├── dao/                         # Data Access Objects (JDBC implementation)
    │   ├── CustomerDAO.java         # Customer DAO interface
    │   ├── CustomerDAOImpl.java      # Customer DAO implementation
    │   ├── EmployeeDAO.java         # Employee DAO interface
    │   ├── EmployeeDAOImpl.java      # Employee DAO implementation
    │   ├── ShipmentDAO.java         # Shipment DAO interface
    │   ├── ShipmentDAOImpl.java      # Shipment DAO implementation
    │   ├── BillingDAO.java          # Billing DAO interface
    │   └── BillingDAOImpl.java       # Billing DAO implementation
    │
    ├── db/
    │   └── DBConnection.java        # Database connection (Singleton Pattern)
    │
    ├── ui/
    │   ├── LoginPanel.java          # Login authentication UI
    │   ├── MainFrame.java           # Main application frame
    │   └── panels/                  # Feature-specific UI panels
    │       ├── CustomerPanel.java   # Customer CRUD UI interface
    │       ├── EmployeePanel.java   # Employee CRUD UI interface
    │       ├── ShipmentPanel.java   # Shipment CRUD UI interface
    │       └── BillingPanel.java    # Billing CRUD UI interface
    │
    └── util/
        └── DatabaseException.java   # Custom database exception
```

---

## 📋 Prerequisites

Before setting up this project, ensure you have:

1. **Java Development Kit (JDK)** - Version 11 or higher
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify: `java -version`

2. **MySQL Server** - Version 8.0 or higher
   - Download from: https://www.mysql.com/downloads/
   - Ensure MySQL service is running

3. **MySQL JDBC Driver** - mysql-connector-java-8.0.x.jar (included in lib/)

4. **IDE (Optional)** - IntelliJ IDEA, Eclipse, NetBeans, or VS Code

---

## 💿 Installation & Setup

### Step 1: Clone/Download Project

```bash
git clone <repository-url>
cd Courier-Logistics-System
```

### Step 2: Verify Prerequisites

```bash
java -version          # Check Java (v11+)
mysql --version        # Check MySQL (v8.0+)
```

### Step 3: Create Database

```bash
mysql -u root -p < database/database.sql
```

### Step 4: Update Configuration

Edit `src/com/courier/db/DBConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/courier_logistics";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

---

## 🔧 Database Setup

### Schema Overview

- **customers** - Customer contact information (8 fields)
- **employees** - Employee records with roles (8 fields)
- **shipments** - Shipment tracking (14+ fields)
- **tracking_history** - Status update history (6 fields)
- **billing** - Invoice and payment data (10 fields)

### Sample Data Included

- 3 pre-configured employees (Admin, Manager, Courier)
- 3 sample customer records
- Test shipments and billing records

---

## ⚙️ Configuration

Update `DBConnection.java` with your credentials:

- **Host:** localhost
- **Port:** 3306
- **Database:** courier_logistics
- **User:** root (or your MySQL user)
- **Password:** your MySQL password

---

## 🚀 How to Run

### Command Line (Linux/Mac)

```bash
# Compile
javac -d bin src/com/courier/**/*.java

# Run
java -cp bin:lib/mysql-connector-java.jar com.courier.Main
```

### Command Line (Windows)

```cmd
javac -d bin src\com\courier\*.java src\com\courier\dao\*.java
java -cp bin;lib/mysql-connector-java.jar com.courier.Main
```

### Using IDE

1. Open project in IDE
2. Add mysql-connector-java.jar to classpath
3. Right-click Main.java → Run

---

## 📖 Usage Guide

### Login Process

1. Start application → Login panel displays
2. Enter employee credentials
3. Select role and authenticate
4. Access main dashboard

### Core Workflows

**Add New Customer:** Customer Panel → Enter details → Click "Add"

**Create Shipment:** Shipment Panel → Fill form → Click "Create"

**Track Shipment:** Enter tracking number → View status and history

**Generate Bill:** Billing Panel → Select shipment → Click "Create Bill"

**Mark Payment Paid:** Billing Panel → Select bill → Click "Mark Paid"

---

## 🏗️ Architecture

### Design Patterns

**MVC Pattern:** Model (entities) + View (UI) + Controller (DAO)

**DAO Pattern:** Separates persistence logic from business logic

**Singleton Pattern:** Single database connection instance in DBConnection.java

**Observer Pattern:** Table listeners for automatic form updates

---

## 📝 Component Descriptions

### Model Classes

- **Customer.java:** Contact information (name, email, phone, address)
- **Employee.java:** Staff records with roles (Admin, Manager, Courier)
- **Shipment.java:** Package tracking (tracking#, status, sender, receiver)
- **Bill.java:** Billing records (amount, payment status, method)
- **TrackingHistory.java:** Status update audit trail

### DAO Classes

All follow Interface + Implementation pattern with CRUD operations

### UI Components

- **LoginPanel.java:** Credential validation
- **MainFrame.java:** Application container
- **Feature Panels:** CRUD interfaces for each entity
- **DBConnection.java:** Connection management (Singleton)

---

## ❓ Troubleshooting

### Common Issues

| Issue                   | Solution                                     |
| ----------------------- | -------------------------------------------- |
| Class not found         | Compile util package first                   |
| MySQL connection failed | Verify MySQL is running, check credentials   |
| Table doesn't exist     | Re-run database.sql initialization           |
| Access denied           | Update DB credentials in DBConnection.java   |
| No suitable driver      | Ensure mysql-connector-java.jar in classpath |

---

## ✅ Pre-Launch Checklist

- [ ] Java JDK 11+ installed
- [ ] MySQL Server running
- [ ] Database created
- [ ] Config updated in DBConnection.java
- [ ] JDBC driver in lib/
- [ ] Source compiled to bin/
- [ ] No port conflicts

---

## 🎉 Project Status

**✅ COMPLETE AND PRODUCTION READY** - All modules fully implemented and tested.

Features:

- ✅ Database & Schema
- ✅ Model Classes
- ✅ DAO Layer
- ✅ Authentication
- ✅ Customer Management
- ✅ Employee Management
- ✅ Shipment Tracking
- ✅ Billing System
- ✅ Tracking History
- ✅ UI Components
- ✅ Error Handling
- ✅ Documentation

---

## 📜 License

**MIT License** © 2026 Shantanu K

Permission is granted to use, modify, and distribute this software.

---

**Version:** 1.0.0 | **Status:** Production Ready | **Last Updated:** April 5, 2026

🚀 Ready for deployment!
