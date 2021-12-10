package Model;

import Model.Models.Order;
import Utils.Subject;

import java.util.ArrayList;

public interface OrdersModel extends Subject {
    ArrayList<Order> getAllOrders();
    ArrayList<Order> getOrders(String status);
    void cancelOrder(Order order,String state);
    void changeOrderAssignee(Order order, boolean toRemove);
    void updateOrderState(Order order, String state);
    ArrayList<Order> getWorkerOrdersForManager(int workerID);
    void processOrder(Order newOrder);
    ArrayList<Order> getCustomerOrders(int customerId);
}
