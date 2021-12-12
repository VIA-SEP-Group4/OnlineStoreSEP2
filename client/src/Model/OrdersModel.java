package Model;

import Model.Models.Order;
import Utils.Subject;

import java.util.ArrayList;

public interface OrdersModel extends Subject {
    ArrayList<Order> getAllOrders();
    ArrayList<Order> getWorkersOrders(String status);
    void cancelOrder(Order order,String state);
    void changeOrderAssignee(Order order, boolean toRemove, int workerId);
    void updateOrderState(Order order, String state);
    ArrayList<Order> getWorkerOrdersForManager(int workerID);
    void processOrder(Order newOrder);
    ArrayList<Order> getCustomerOrders(int customerId);
    void activateListeners();
    void deactivateListeners();
    ArrayList<Order> getWorkersOrders(int id);
}
