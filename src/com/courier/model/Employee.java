package com.courier.model;

import java.sql.Date;

public class Employee {

    private int    employeeId;
    private String name;
    private String email;
    private String phone;
    private String role;       // ADMIN, MANAGER, COURIER
    private double salary;
    private Date   hireDate;
    private String status;     // ACTIVE, INACTIVE

    // no-arg constructor
    public Employee() {}

    // parameterized constructor
    public Employee(int employeeId, String name, String email,
                    String phone, String role,
                    double salary, Date hireDate, String status) {
        this.employeeId = employeeId;
        this.name       = name;
        this.email      = email;
        this.phone      = phone;
        this.role       = role;
        this.salary     = salary;
        this.hireDate   = hireDate;
        this.status     = status;
    }

    // getters
    public int    getEmployeeId() { return employeeId; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getPhone()      { return phone; }
    public String getRole()       { return role; }
    public double getSalary()     { return salary; }
    public Date   getHireDate()   { return hireDate; }
    public String getStatus()     { return status; }

    // setters
    public void setEmployeeId(int employeeId)   { this.employeeId = employeeId; }
    public void setName(String name)             { this.name = name; }
    public void setEmail(String email)           { this.email = email; }
    public void setPhone(String phone)           { this.phone = phone; }
    public void setRole(String role)             { this.role = role; }
    public void setSalary(double salary)         { this.salary = salary; }
    public void setHireDate(Date hireDate)       { this.hireDate = hireDate; }
    public void setStatus(String status)         { this.status = status; }

    @Override
    public String toString() {
        return employeeId + " - " + name + " [" + role + "]";
    }
}