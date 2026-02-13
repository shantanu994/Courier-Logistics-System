CREATE DATABASE courier_logistics;
USE courier_logistics;

CREATE TABLE users(
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50),
  password VARCHAR(50)
);

INSERT INTO users VALUES (1,'admin','admin123');

CREATE TABLE courier(
  id INT AUTO_INCREMENT PRIMARY KEY,
  sender VARCHAR(100),
  receiver VARCHAR(100),
  address VARCHAR(200)
);

CREATE TABLE customer(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  phone VARCHAR(20)
);

CREATE TABLE employee(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  role VARCHAR(50)
);

CREATE TABLE shipment(
  id INT AUTO_INCREMENT PRIMARY KEY,
  tracking VARCHAR(50),
  status VARCHAR(50)
);
