package Model;

import Model.Models.Employee;
import Model.Models.Order;
import Utils.Subject;

import java.util.ArrayList;

public interface WorkerModel extends Subject {
    ArrayList<Order> fetchWorkerOrders();
    void changeOrderStatus(Order order);
    void changeOrderAssignee(Order order);
    Employee getLoggedEmployee();
    ArrayList<Order> getAllOrders();
}
