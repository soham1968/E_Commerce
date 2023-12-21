package com.example.E_commerce;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;
    VBox body;
    Button signIn;
    Customer customerLogin;
    Button homeButton = new Button("");
    Button logout = new Button("Log Out");
    Button yourOrder = new Button("Your Orders");
    Label welcomeLabel;
    ProductList productList = new ProductList();
    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
    VBox productPage;
    OrderList orderList = new OrderList();
    VBox ordersPage;
    Button placeOrder = new Button("Place Order");
    Button removeItem = new Button("Remove Item");
    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(800,600);
        root.setTop(headerBar);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage = productList.getAllProducts();
        body.getChildren().add(productPage);
        root.setBottom(footerBar);
        return root;
    }
    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }
    private void createLoginPage(){
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField();
        PasswordField password = new PasswordField();
        password.setPromptText("");
        Label msg = new Label("");
        Button loginButton = new Button("Login");

        loginPage = new GridPane();
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText, 0, 0);
        loginPage.add(userName, 1, 0);
        loginPage.add(passwordText, 0, 1);
        loginPage.add(password, 1, 1);
        loginPage.add(msg,0,2);
        loginPage.add(loginButton, 1,2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();
                Login login = new Login();
                customerLogin = login.customerLogin(name,pass);
                if(customerLogin != null){
                    welcomeLabel.setText("Welcome : " + customerLogin.getName());
                    headerBar.getChildren().addAll(welcomeLabel,logout);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                    footerBar.setVisible(true);
                }else{
                    msg.setText("wrong credentials");
                }
            }
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                customerLogin = null;
                headerBar.getChildren().removeAll(welcomeLabel,logout);
                headerBar.getChildren().add(signIn);
                body.getChildren().clear();
                productPage = productList.getAllProducts();
                body.getChildren().add(productPage);

                footerBar.setVisible(true);
            }
        });
    }

    private void createHeaderBar(){
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");
        Button searchButton = new Button("Search");
        Button cartButton = new Button("Cart");
        signIn = new Button("Sign In");
        welcomeLabel = new Label();

        headerBar = new HBox(20);
        headerBar.setStyle("-fx-background-color:grey;");
        headerBar.setPadding(new Insets(10));
        Image image = new Image("C:\\Users\\HP\\IdeaProjects\\Major_pro\\src\\icon.jpg");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(100);
        homeButton.setGraphic(imageView);
//        Button orderButton = new Button("Orders");
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton,searchBar,searchButton,signIn,cartButton,yourOrder);

        signIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(loginPage);
                headerBar.getChildren().remove(signIn);

            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemsInCart);
                prodPage.setSpacing(10);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.getChildren().addAll(placeOrder,removeItem);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
            }
        });

        yourOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(customerLogin == null){
                    showDialog("Please Login before you check your order history");
                    return;
                }
                ordersPage = orderList.getAllOrders();
                body.getChildren().clear();
                body.getChildren().add(ordersPage);
            }
        });

        placeOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(itemsInCart == null){
                    showDialog("Please select a product first before you continue");
                    return;
                }
                if(customerLogin == null){
                    showDialog("Please Login before you continue");
                    return;
                }
                int count = Order.placeMultipleOrder(customerLogin,itemsInCart);
                if(count != 0){
                    showDialog("Order for "+count+" products placed Successfully");
                }else{
                    showDialog("Order not received");
                }
            }
        });

        removeItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(itemsInCart == null){
                    showDialog("Nothing to remove");
                    return;
                }
                Product product = productList.getSelectedProduct();
                if(product == null){
                    showDialog("Please select a product to remove from cart");
                    return;
                }
                itemsInCart.remove(product);
                showDialog("Selected item has been removed from cart successfully");
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                productPage = productList.getAllProducts();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
            }
        });
//        orderButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                body.getChildren().clear();
//                ordersPage = orderList.getAllOrders();
//                ordersPage.setSpacing(10);
//                ordersPage.setAlignment(Pos.CENTER);
////                ordersPage.getChildren().add(placeOrder);
//                body.getChildren().add(ordersPage);
//                footerBar.setVisible(false);
//            }
//        });
    }

    private void createFooterBar(){
        Button buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add to cart");

        footerBar = new HBox(20);
        footerBar.setStyle("-fx-background-color:green;");
        footerBar.setPadding(new Insets(10));
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    showDialog("Please select a product first before you continue");
                    return;
                }
                if(customerLogin == null){
                    showDialog("Please Login before you continue");
                    return;
                }
                boolean status = Order.placeOrder(customerLogin,product);
                if(status == true){
                    showDialog("Order placed Successfully");
                }else{
                    showDialog("Order not received");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    showDialog("Please select a product before you add to cart");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected item has been added to cart successfully");
            }
        });
    }
    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("message");
        alert.showAndWait();
    }
}
