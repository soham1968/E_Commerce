package com.example.E_commerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnection {
    private final String dbUrl = "jdbc:mysql://127.0.0.1:3306/ecommerce";
    private final String Username = "root";
    private final String pass = "Soham@123";
    private Statement getStatement(){
        try{
            Connection connection = DriverManager.getConnection(dbUrl,Username,pass);
            return connection.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getQueryTable(String query){
        try{
            Statement statement = getStatement();
            return statement.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public int updateDatabase(String query){
        try {
            Statement statement = getStatement();
            return statement.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) {
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQueryTable("SELECT * FROM ecommerce.ecom");
        if(rs != null){
            System.out.println("Success");
        }else{
            System.out.println("not sucess");
        }
    }
}
