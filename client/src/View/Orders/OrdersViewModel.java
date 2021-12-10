package View.Orders;

import Model.Models.Order;
import Model.Models.Product;

import Model.OrdersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class OrdersViewModel
{
    private OrdersModel model;
    private ObservableList<Order> openOrders;
    private ObservableList<Product> openOrdersDetail;
    private ObservableList<Order> myOrders;
    private ObservableList<Product> myOrdersDetail;

    public OrdersViewModel(OrdersModel ordersModel){
        this.model = ordersModel;
        openOrders = FXCollections.observableArrayList();
        openOrdersDetail = FXCollections.observableArrayList();
        myOrders = FXCollections.observableArrayList();
        myOrdersDetail = FXCollections.observableArrayList();
    }

    // TODO: 10/12/2021 get order based on worker ID - where to get that?  
    public void getOrders(){
        ArrayList<Order> orders = model.getOrders("WAITING");
        for (Order o : orders)
        {
            openOrders.add(o);
        }
    }

//    public void getOrders(){
//        ArrayList<Order> orders = model.getAllOrders();
//        for (Order o : orders)
//        {
//            openOrders.add(o);
//        }
//    }

    void changeOrderAssignee(Order order, boolean toRemove){
        model.changeOrderAssignee(order, toRemove);
    }

    public ObservableList<Order> getOpenOrders()
    {
        return openOrders;
    }

    public ObservableList<Product> getOpenOrdersDetail()
    {
        return openOrdersDetail;
    }

    public ObservableList<Order> getMyOrders()
    {
        return myOrders;
    }

    public ObservableList<Product> getMyOrdersDetail()
    {
        return myOrdersDetail;
    }

    public void showOpenOrderDetails(int id){
        openOrdersDetail.clear();
        for (Order o : openOrders){
            if (o.getOrderId() == id)
                openOrdersDetail.addAll(o.getProducts());
        }
    }

    public void showMyOrderDetails(int id){
        myOrdersDetail.clear();
        for (Order o : myOrders){
            if (o.getOrderId() == id)
                myOrdersDetail.addAll(o.getProducts());
        }
    }

    public void pickOrder(int id){
        openOrdersDetail.clear();
        List<Order> toRemove = new ArrayList<>();
        for (Order o : openOrders){
            if (o.getOrderId() == id){
                o.setStatus("IN_PROCESS");
                toRemove.add(o);
            }
        }
        openOrders.remove(toRemove.get(0));
        myOrders.add(toRemove.get(0));
        model.updateOrderStatus(toRemove.get(0), toRemove.get(0).getState());
        toRemove.clear();
    }

    public void removeOrder(int id){
        myOrdersDetail.clear();
        List<Order> toRemove = new ArrayList<>();
        for (Order o : myOrders){
            if (o.getOrderId() == id){
                o.setStatus("WAITING");
                toRemove.add(o);
            }
        }
        myOrders.remove(toRemove.get(0));
        openOrders.add(toRemove.get(0));
        model.updateOrderStatus(toRemove.get(0), toRemove.get(0).getState());
        toRemove.clear();
    }

    public void completeOrder(int id){
        for (Order o : myOrders){
            if (o.getOrderId() == id){
                o.setStatus("READY");
                model.updateOrderStatus(o, o.getState());
            }
        }
    }
}
