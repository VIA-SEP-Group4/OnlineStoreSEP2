package View.Manager;

import Model.ManagerModel;
import Model.Models.Order;
import Model.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class OrderOverviewViewModel {
    private ManagerModel model;
    private ObservableList<Order> orders;
    private ObservableList<Product> products;
    public OrderOverviewViewModel(ManagerModel model) {
        this.model = model;
        orders= FXCollections.observableArrayList();
        products=FXCollections.observableArrayList();
        orders.setAll(model.getAllOrders());
    }
    public ObservableList<Order> getAllOrders() {

        return orders;
    }
    public void setProducts(Order o){
        products.setAll(o.getProducts());
    }
    public ObservableList<Product> getProducts() {
        return products;
    }
}
