package Model;

import Model.Models.Order;
import Utils.Subject;

import java.util.ArrayList;

public interface OrdersModel extends Subject {
    ArrayList<Order> getAllOrders();
    ArrayList<Order> getOrders(String status);
    void changeOrderAssignee(Order order, boolean toRemove);
    void updateOrderStatus(Order order, String status);
    ArrayList<Order> getWorkerOrdersForManager(int workerID);
    void processOrder(Order newOrder);
    ArrayList<Order> getCustomerOrders(int customerId);
}
