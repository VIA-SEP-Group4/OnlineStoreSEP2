package Model;

import Model.Models.Order;
import Networking.OrdersClient;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class OrdersModelManager implements OrdersModel, PropertyChangeListener {

    private OrdersClient ordersClient;
    private PropertyChangeSupport support;

    public OrdersModelManager(OrdersClient ordersClient) {
        support=new PropertyChangeSupport(this);
        this.ordersClient = ordersClient;
        ordersClient.startClient();
        ordersClient.addListener("newOrder",this);
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        return ordersClient.getAllOrders();
    }

    @Override public ArrayList<Order> getOrders(String status)
    {
        return ordersClient.getOrders(status);
    }

    @Override public void cancelOrder(Order order, String state)
    {
        ordersClient.updateOrderState(order,state);
    }

    @Override
    public void changeOrderAssignee(Order order, boolean toRemove, int workerId) {
        if(toRemove){
            order.setWorkerID(-1);
        }
        else {
            order.setWorkerID(workerId);
        }
        ordersClient.changeOrderAssignee(order);
    }

    @Override public void updateOrderState(Order order, String status)
    {
        ordersClient.updateOrderState(order, status);
    }

    @Override
    public ArrayList<Order> getWorkerOrdersForManager(int workerID) {
        return ordersClient.getWorkerOrdersForManager(workerID);
    }

    @Override
    public void processOrder(Order newOrder) {
        ordersClient.processOrder(newOrder);
    }

    @Override
    public ArrayList<Order> getCustomerOrders(int customerId) {
        return ordersClient.getCustomerOrders(customerId);
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt);
    }
}
