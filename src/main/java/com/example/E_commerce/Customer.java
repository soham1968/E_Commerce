package com.example.E_commerce;

public class Customer {
    private int id;
    private String name,mobile,email;
    public Customer(int id, String name, String email, String mobile){
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }
}
