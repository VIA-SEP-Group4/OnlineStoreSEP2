package Networking;

import Model.Models.Order;
import Utils.Subject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface WorkerClient extends Subject {
    ArrayList<Order> getAllOrders();
    void changeOrderAssignee(Order order);
    void startClient();
}
