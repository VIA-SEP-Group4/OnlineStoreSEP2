package Networking;

import Model.Models.Order;
import Utils.Subject;

import java.util.ArrayList;

public interface OrdersClient extends Subject {
    void startClient();
    ArrayList<Order> getAllOrders();
    ArrayList<Order> getOrders(String status);
    void changeOrderAssignee(Order order);
    void updateOrderStatus(Order order, String status);
    ArrayList<Order> getWorkerOrdersForManager(int workerID);
    void processOrder(Order newOrder);
    ArrayList<Order> getCustomerOrders(int customerId);
}
