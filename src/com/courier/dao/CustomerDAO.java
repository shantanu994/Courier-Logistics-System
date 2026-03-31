package com.courier.dao;

import com.courier.model.Customer;
import com.courier.util.DatabaseException;
import java.util.ArrayList;

public interface CustomerDAO {
    void addCustomer(Customer customer)         throws DatabaseException;
    ArrayList<Customer> getAllCustomers()        throws DatabaseException;
    Customer getCustomerById(int id)            throws DatabaseException;
    void updateCustomer(Customer customer)      throws DatabaseException;
    void deleteCustomer(int id)                 throws DatabaseException;
}