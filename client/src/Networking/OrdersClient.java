package Networking;

import Model.Models.Order;
import Utils.Subject;

import java.util.ArrayList;

public interface OrdersClient extends Subject {
    void startClient();
    ArrayList<Order> getAllOrders();
    ArrayList<Order> getOrders(String status);
    void updateOrderState(Order order, String state);
    ArrayList<Order> getWorkerOrdersForManager(int workerID);
    void processOrder(Order newOrder);
    ArrayList<Order> getCustomerOrders(int customerId);
    void unregisterClient();
    void registerClient();
    ArrayList<Order> getWorkersOrders(int wwId);
}
