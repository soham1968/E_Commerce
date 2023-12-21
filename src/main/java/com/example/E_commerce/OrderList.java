package com.example.E_commerce;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class OrderList {
    private TableView<Order> orderTable;
    public VBox createTable(ObservableList<Order> data){
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn quantity = new TableColumn("QUANTITY");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn date = new TableColumn("DATE");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn status = new TableColumn("STATUS");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        orderTable = new TableView<>();
        orderTable.setItems(data);
        orderTable.getColumns().addAll(id,name,quantity,date,status);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(orderTable);
        return vBox;
    }
    public VBox getAllOrders(){
        ObservableList<Order> data = Order.getAllOrders();
        return createTable(data);
    }

}
