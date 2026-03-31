package com.courier.dao;

import com.courier.model.Employee;
import com.courier.util.DatabaseException;
import java.util.ArrayList;

public interface EmployeeDAO {
    void addEmployee(Employee employee)             throws DatabaseException;
    ArrayList<Employee> getAllEmployees()            throws DatabaseException;
    ArrayList<Employee> getCouriers()               throws DatabaseException;
    Employee getEmployeeById(int id)                throws DatabaseException;
    void updateEmployee(Employee employee)          throws DatabaseException;
    void deleteEmployee(int id)                     throws DatabaseException;
}