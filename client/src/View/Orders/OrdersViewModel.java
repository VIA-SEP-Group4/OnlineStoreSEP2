package View.Orders;

import Model.CredentialsModel;
import Model.Models.Order;
import Model.Models.Product;
import Model.OrdersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class OrdersViewModel implements PropertyChangeListener
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
        fetchOpenOrders();
        fetchWorkersOrders();
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
        ordersModel.addListener("newOrder", this);
        ordersModel.addListener("updatedOrderStatus", this);
        ordersModel.activateListeners();
    }
    private void deactivateListeners(){
        ordersModel.removeListener("newOrder", this);
        ordersModel.removeListener("updatedOrderStatus", this);
        ordersModel.deactivateListeners();
    }


    private void addNewOrder(Order newOrder)
    {
        credentialsModel.getLoggedEmployee().getOpenOrders().add(newOrder);
        openOrders.setAll(credentialsModel.getLoggedEmployee().getOpenOrders());

//        boolean inMyOrders = false;
//        boolean inOpenOrders = false;
//
//        for (Order o : myOrders){
//            if (o.getOrderId() == newOrder.getOrderId()){
//                myOrders.remove(o);
//                openOrders.add(newOrder);
//                inMyOrders = true;
//                break;
//            }
//        }
//        if (!inMyOrders){
//            for (Order o : openOrders){
//                if (o.getOrderId() == newOrder.getOrderId()){
//                    inOpenOrders = true;
//                    myOrders.add(newOrder);
//                    break;
//                }
//            }
//        }
//        if (!inOpenOrders && !inMyOrders){
//            openOrders.add(newOrder);
//        }
    }

    private void updateOrder(Order updatedOrder)
    {
        // 1.1) picked by ww ->status changed(to "in process") & wwId changed
        // when updatedOrder.status == "in process" =>order was picked
        if (updatedOrder.getState().equalsIgnoreCase("in process")){
            //remove from open orders
            credentialsModel.getLoggedEmployee().removeFromOpenOrders(updatedOrder.getOrderId());
            //add to my orders
            credentialsModel.getLoggedEmployee().getMyOrders().add(updatedOrder);
        }

        // 1.2) removed by ww ->status changed(to "waiting") & wwId changed
        // when updatedOrder.status == "waiting" =>order was removed by ww
        else if (updatedOrder.getState().equalsIgnoreCase("waiting")){
            //remove from my orders
            credentialsModel.getLoggedEmployee().removeFromMyOrders(updatedOrder.getOrderId());
            //add to open orders
            credentialsModel.getLoggedEmployee().getOpenOrders().add(updatedOrder);
        }

        //1.3) finished by ww ->status changed(to "ready")
        else if (updatedOrder.getState().equalsIgnoreCase("ready")){
            //update my orders
            credentialsModel.getLoggedEmployee().removeFromMyOrders(updatedOrder.getOrderId());
            //add to open orders
            credentialsModel.getLoggedEmployee().getMyOrders().add(updatedOrder);
        }

        // 2.1) cancelled by customer ->status changed(to "cancelled") & wwId same
        // when updatedOrder.status == "cancelled" =>order was cancelled by customer
        else if (updatedOrder.getState().equalsIgnoreCase("cancelled")){
            //update open orders
            credentialsModel.getLoggedEmployee().removeFromOpenOrders(updatedOrder.getOrderId());
            credentialsModel.getLoggedEmployee().getOpenOrders().add(updatedOrder);
        }

        // 2.2) picked by customer ->status changed(to "picked") & wwId same
        else if (updatedOrder.getState().equalsIgnoreCase("picked")){
            //update my orders
            credentialsModel.getLoggedEmployee().removeFromMyOrders(updatedOrder.getOrderId());
            credentialsModel.getLoggedEmployee().getMyOrders().add(updatedOrder);
        }

//        for(Order o: openOrders){
//            if(o.getOrderId()==updatedOrder.getOrderId()) {
//                openOrders.remove(o);
//                if(!updatedOrder.getState().equalsIgnoreCase("cancelled"))
//                {
//                    openOrders.add(updatedOrder);
//                }
//                break;
//            }
//        }
//        for(Order o: myOrders){
//            if(o.getOrderId()==updatedOrder.getOrderId()) {
//                myOrders.remove(o);
//                myOrders.add(updatedOrder);
//                break;
//            }
//        }
    }

    public void fetchOpenOrders(){
        ArrayList<Order> openOrders = ordersModel.getWorkersOrders("waiting");
        credentialsModel.getLoggedEmployee().setOpenOrders(openOrders);
        this.openOrders.setAll(openOrders);
    }

    public void fetchWorkersOrders(){
        ArrayList<Order> myOrders = ordersModel.getWorkersOrders(credentialsModel.getLoggedEmployee().getID());
        credentialsModel.getLoggedEmployee().setMyOrders(myOrders);
        this.myOrders.setAll(myOrders);
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


    public void pickOrderButton2(Order tempOrder)
    {
        openOrdersDetail.clear();

        //set worker id
        tempOrder.setWorkerID(credentialsModel.getLoggedEmployee().getID());
        ordersModel.updateOrderState(tempOrder, "in process");
    }


    public void removeOrder2(Order tempOrder)
    {
        myOrdersDetail.clear();
        //set worker id
        tempOrder.setWorkerID(-1);
        ordersModel.updateOrderState(tempOrder, "waiting");
    }

    public void completeOrder(Order tempOrder){
        ordersModel.updateOrderState(tempOrder, "ready");
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals("newOrder"))
            addNewOrder((Order) evt.getNewValue());
        else if (evt.getPropertyName().equals("updatedOrderStatus"))
            updateOrder((Order) evt.getNewValue());

        openOrders.clear();
        myOrders.clear();
        openOrders.setAll(credentialsModel.getLoggedEmployee().getOpenOrders());
        myOrders.setAll(credentialsModel.getLoggedEmployee().getMyOrders());
    }

}
