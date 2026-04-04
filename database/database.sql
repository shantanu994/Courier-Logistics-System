CREATE DATABASE IF NOT EXISTS courier_logistics;
USE courier_logistics;

CREATE TABLE IF NOT EXISTS customers (
    customer_id  INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    phone        VARCHAR(15)  NOT NULL,
    address      TEXT         NOT NULL,
    city         VARCHAR(50)  NOT NULL,
    state        VARCHAR(50)  NOT NULL,
    pincode      VARCHAR(10)  NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS employees (
    employee_id  INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    phone        VARCHAR(15)  NOT NULL,
    role         ENUM('ADMIN','MANAGER','COURIER') NOT NULL,
    salary       DECIMAL(10,2) NOT NULL,
    hire_date    DATE NOT NULL,
    status       ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS shipments (
    shipment_id        INT AUTO_INCREMENT PRIMARY KEY,
    tracking_number    VARCHAR(20) NOT NULL UNIQUE,
    sender_id          INT NOT NULL,
    receiver_id        INT NOT NULL,
    courier_id         INT,
    origin_city        VARCHAR(50) NOT NULL,
    destination_city   VARCHAR(50) NOT NULL,
    weight             DECIMAL(8,2) NOT NULL,
    package_type       ENUM('DOCUMENT','PARCEL','FRAGILE','HEAVY') NOT NULL,
    status             ENUM('PENDING','PICKED_UP','IN_TRANSIT',
                            'OUT_FOR_DELIVERY','DELIVERED','RETURNED')
                       DEFAULT 'PENDING',
    estimated_delivery DATE,
    actual_delivery    DATE,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_sender
        FOREIGN KEY (sender_id)   REFERENCES customers(customer_id),
    CONSTRAINT fk_receiver
        FOREIGN KEY (receiver_id) REFERENCES customers(customer_id),
    CONSTRAINT fk_courier
        FOREIGN KEY (courier_id)  REFERENCES employees(employee_id)
);

CREATE TABLE IF NOT EXISTS tracking_history (
    tracking_id  INT AUTO_INCREMENT PRIMARY KEY,
    shipment_id  INT NOT NULL,
    status       VARCHAR(50)  NOT NULL,
    location     VARCHAR(100) NOT NULL,
    remarks      TEXT,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (shipment_id) REFERENCES shipments(shipment_id)
);

CREATE TABLE IF NOT EXISTS billing (
    bill_id         INT AUTO_INCREMENT PRIMARY KEY,
    shipment_id     INT NOT NULL UNIQUE,
    customer_id     INT NOT NULL,
    base_amount     DECIMAL(10,2) NOT NULL,
    tax_amount      DECIMAL(10,2) NOT NULL,
    total_amount    DECIMAL(10,2) NOT NULL,
    payment_status  ENUM('PENDING','PAID','OVERDUE') DEFAULT 'PENDING',
    payment_method  ENUM('CASH','CARD','UPI','NET_BANKING'),
    bill_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_date       TIMESTAMP NULL,
    FOREIGN KEY (shipment_id) REFERENCES shipments(shipment_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);