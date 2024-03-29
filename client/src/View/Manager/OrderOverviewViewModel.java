package View.Manager;

import Model.Models.Order;
import Model.Models.Product;
import Model.OrdersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class OrderOverviewViewModel implements PropertyChangeListener
{
    private OrdersModel model;
    private ObservableList<Order> orders;
    private ObservableList<Product> products;

    public OrderOverviewViewModel(OrdersModel model) {
        this.model = model;
        model.activateListeners();

        orders= FXCollections.observableArrayList();
        products=FXCollections.observableArrayList();
        orders.setAll(model.getAllOrders());
    }

    public void activateListeners(){
        model.addListener("newOrder",this);
        model.addListener("updatedOrderStatus", this);
        model.activateListeners();
    }
    public void deactivateListeners(){
        model.removeListener("newOrder",this);
        model.removeListener("updatedOrderStatus", this);
        model.deactivateListeners();
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


    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        Order order= (Order) evt.getNewValue();
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
}
