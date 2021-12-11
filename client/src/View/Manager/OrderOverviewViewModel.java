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
        model.addListener("updatedOrderStatus", this::updateOrders);
    }

    private void updateOrders(PropertyChangeEvent event) {
        Order order= (Order) event.getNewValue();
        boolean found=false;
        for(int i=0;i<orders.size();i++){
            if(orders.get(i).getOrderId()== order.getOrderId()){
                orders.set(i,order);
                found=true;
                break;
            }
        }
        if(!found){
            orders.add(order);
        }
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
