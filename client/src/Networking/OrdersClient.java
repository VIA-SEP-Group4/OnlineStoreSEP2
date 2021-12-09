package Networking;

import Model.Models.Order;
import Utils.Subject;

import java.util.ArrayList;

public interface OrdersClient extends Subject {
    void startClient();
    ArrayList<Order> getAllOrders();
    void changeOrderAssignee(Order order);
    ArrayList<Order> getWorkerOrdersForManager(int workerID);
    void processOrder(Order newOrder);
    ArrayList<Order> getCustomerOrders(int customerId);
}
