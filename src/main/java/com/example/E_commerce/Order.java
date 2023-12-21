package com.example.E_commerce;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty quantity;
    private final SimpleStringProperty date;
    private final SimpleStringProperty status;

    public Order(int id, String name, int quantity, String date, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.date = new SimpleStringProperty(date);
        this.status = new SimpleStringProperty(status);
    }
    public static ObservableList<Order> getAllOrders(){
        String selectAllOrders = "SELECT orders.id, product.name, orders.quantity, orders.order_date, orders.order_status FROM ecommerce.orders inner join product where orders.product_id = product.id;";
        return fetchOrderData(selectAllOrders);
    }
    public static ObservableList<Order> fetchOrderData(String query){
        ObservableList<Order> data = FXCollections.observableArrayList();
        DbConnection dbConnection = new DbConnection();
        try{
            ResultSet rs = dbConnection.getQueryTable(query);
            while(rs.next()) {
                Order order = new Order(rs.getInt("id"), rs.getString("order_date"), rs.getInt("quantity"), rs.getString("order_date"), rs.getString("order_status"));
                data.add(order);
            }
            return data;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static boolean placeOrder(Customer customer,Product product){
        String groupOrder = "SELECT max(group_order_id)+1 id FROM orders";
        DbConnection dbConnection = new DbConnection();
        try{
            ResultSet rs = dbConnection.getQueryTable(groupOrder);
            if(rs.next()){
                String placeOrder = "INSERT INTO ORDERS(group_order_id, customer_id, product_id) VALUES("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return dbConnection.updateDatabase(placeOrder) != 0;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static int placeMultipleOrder(Customer customer, ObservableList<Product> productList){
        String groupOrder = "SELECT max(group_order_id)+1 id FROM orders";
        DbConnection dbConnection = new DbConnection();
        try{
            ResultSet rs = dbConnection.getQueryTable(groupOrder);
            int count = 0;
            if(rs.next()){
                for (Product product : productList){
                    String placeOrder = "INSERT INTO ORDERS(group_order_id, customer_id, product_id) VALUES("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count += dbConnection.updateDatabase(placeOrder);
                }
                return count;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }


}
