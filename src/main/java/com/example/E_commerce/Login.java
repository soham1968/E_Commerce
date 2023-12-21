package com.example.E_commerce;

import java.sql.ResultSet;

public class Login  {
    public Customer customerLogin(String Username, String password){
        String querry = "SELECT * FROM customer where email = '"+Username+"' and password = '"+password+"'";
        DbConnection conn = new DbConnection();
        try{
            ResultSet rs = conn.getQueryTable(querry);
            if(rs.next()){
                return new Customer(rs.getInt("id"),
                        rs.getString("name"), rs.getString("email"),
                        rs.getString("mobile"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Login login = new Login();
        Customer customer = login.customerLogin("","");
        System.out.println(customer.getName());
    }
}
