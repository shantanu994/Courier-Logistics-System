package com.courier.model;

public class Customer {

    private int    customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String pincode;

    // no-arg constructor
    public Customer() {}

    // parameterized constructor
    public Customer(int customerId, String name, String email,
                    String phone, String address,
                    String city, String state, String pincode) {
        this.customerId = customerId;
        this.name       = name;
        this.email      = email;
        this.phone      = phone;
        this.address    = address;
        this.city       = city;
        this.state      = state;
        this.pincode    = pincode;
    }

    // getters
    public int    getCustomerId() { return customerId; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getPhone()      { return phone; }
    public String getAddress()    { return address; }
    public String getCity()       { return city; }
    public String getState()      { return state; }
    public String getPincode()    { return pincode; }

    // setters
    public void setCustomerId(int customerId)   { this.customerId = customerId; }
    public void setName(String name)             { this.name = name; }
    public void setEmail(String email)           { this.email = email; }
    public void setPhone(String phone)           { this.phone = phone; }
    public void setAddress(String address)       { this.address = address; }
    public void setCity(String city)             { this.city = city; }
    public void setState(String state)           { this.state = state; }
    public void setPincode(String pincode)       { this.pincode = pincode; }

    @Override
    public String toString() {
        return customerId + " - " + name + " (" + city + ")";
    }
}