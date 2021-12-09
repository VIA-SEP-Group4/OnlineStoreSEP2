package Model;

import Model.Models.Order;
import Utils.Subject;

import java.util.ArrayList;

public interface OrdersModel extends Subject {
    ArrayList<Order> getAllOrders();
    void changeOrderAssignee(Order order, boolean toRemove);
    ArrayList<Order> getWorkerOrdersForManager(int workerID);
    void processOrder(Order newOrder);
    ArrayList<Order> getCustomerOrders(int customerId);
}
