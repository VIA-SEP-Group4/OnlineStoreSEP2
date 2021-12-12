package View.Orders;

import Model.CredentialsModel;
import Model.Models.Order;
import Model.Models.Product;
import Model.OrdersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class OrdersViewModel
{
    private OrdersModel ordersModel;
    private CredentialsModel credentialsModel;

    private ObservableList<Order> openOrders;
    private ObservableList<Product> openOrdersDetail;
    private ObservableList<Order> myOrders;
    private ObservableList<Product> myOrdersDetail;

    public OrdersViewModel(OrdersModel ordersModel, CredentialsModel credentialsModel){
        this.ordersModel = ordersModel;
        this.credentialsModel = credentialsModel;

        openOrders = FXCollections.observableArrayList();
        openOrdersDetail = FXCollections.observableArrayList();
        myOrders = FXCollections.observableArrayList();
        myOrdersDetail = FXCollections.observableArrayList();
    }

    public void load(){
        getWaitingOrders();
        getWorkersOrders();
        activateListeners();
    }
    public void end(){
        openOrders.clear();
        myOrders.clear();
        deactivateListeners();

        credentialsModel.logOutEmployee();
    }

    private void activateListeners()
    {
        ordersModel.addListener("newOrder", this::newOrder);
        ordersModel.addListener("updatedOrderStatus", this::updateOrder);
        ordersModel.activateListeners();
    }
    private void deactivateListeners(){
        ordersModel.removeListener("newOrder", this::newOrder);
        ordersModel.removeListener("updatedOrderStatus", this::updateOrder);
        ordersModel.deactivateListeners();
    }


    private void newOrder(PropertyChangeEvent evt)
    {
        Order updatedOrder = (Order) evt.getNewValue();

        boolean inMyOrders = false;
        boolean inOpenOrders = false;

        for (Order o : myOrders){
            if (o.getOrderId() == updatedOrder.getOrderId()){
                myOrders.remove(o);
                openOrders.add(updatedOrder);
                inMyOrders = true;
                break;
            }
        }

        if (!inMyOrders){
            for (Order o : openOrders){
                if (o.getOrderId() == updatedOrder.getOrderId()){
                    inOpenOrders = true;
                    myOrders.add(updatedOrder);
                    break;
                }
            }
        }

        if (!inOpenOrders){
            openOrders.add(updatedOrder);
        }

        System.out.println("After new order evt:\nopen orders: " + openOrders + "\n my orders: " + myOrders);

    }

    private void updateOrder(PropertyChangeEvent evt)
    {
        Order o= (Order) evt.getNewValue();

        for(Order order: openOrders){
            if(order.getOrderId()==o.getOrderId()) {
                openOrders.remove(order);
                openOrders.add(o);
                break;
            }
        }
        for(Order order: myOrders){
            if(order.getOrderId()==o.getOrderId()) {
                myOrders.remove(order);
                myOrders.add(o);
                break;
            }
        }
        System.out.println("After update order evt:\nopen orders: " + openOrders + "\n my orders: " + myOrders);
    }

    public void getWaitingOrders(){
        ArrayList<Order> orders = ordersModel.getWorkersOrders("waiting");
        openOrders.setAll(orders);
    }

    public void getWorkersOrders(){
//        ArrayList<Order> orders = ordersModel.getWorkerOrdersForManager(credentialsModel.getLoggedEmployee().getID());
        ArrayList<Order> orders = ordersModel.getWorkersOrders(credentialsModel.getLoggedEmployee().getID());
        myOrders.addAll(orders);
    }

    void changeOrderAssignee(Order order, boolean toRemove){
        ordersModel.changeOrderAssignee(order, toRemove, credentialsModel.getLoggedEmployee().getID());
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
                o.setStatus("in process");
                toRemove.add(o);
            }
        }
      openOrders.remove(toRemove.get(0));
//        myOrders.add(toRemove.get(0));
        ordersModel.updateOrderState(toRemove.get(0), toRemove.get(0).getState());
        toRemove.clear();
    }

    public void removeOrder(int id){
        myOrdersDetail.clear();
        List<Order> toRemove = new ArrayList<>();
        // TODO: 12/12/2021 remember to set back to myOrders
        for (Order o : openOrders){
            if (o.getOrderId() == id){
                o.setStatus("waiting");
                toRemove.add(o);
            }
        }
        //myOrders.remove(toRemove.get(0));
        //openOrders.add(toRemove.get(0));
        ordersModel.updateOrderState(toRemove.get(0), toRemove.get(0).getState());
        toRemove.clear();
    }

    public void completeOrder(int id){
        for (Order o : myOrders){
            if (o.getOrderId() == id){
                o.setStatus("ready");
                ordersModel.updateOrderState(o, o.getState());
            }
        }
    }


}
