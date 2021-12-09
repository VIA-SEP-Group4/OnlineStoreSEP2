package View.Manager;

import Model.Models.Order;
import Model.Models.Product;
import Model.OrdersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class OrderOverviewViewModel {
    private OrdersModel model;
    private ObservableList<Order> orders;
    private ObservableList<Product> products;
    public OrderOverviewViewModel(OrdersModel model) {
        this.model = model;
        orders= FXCollections.observableArrayList();
        products=FXCollections.observableArrayList();
        orders.setAll(model.getAllOrders());
        model.addListener("newOrder",this::updateOrders);
    }

    private void updateOrders(PropertyChangeEvent event) {
        ArrayList<Order> temp=(ArrayList<Order>) event.getNewValue();
        orders.setAll(temp);
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
