package Networking;

import Model.Models.Employee;
import Model.Models.Order;
import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface ManagerClient extends Subject {
    void addProduct(Product p);
    void deleteProduct(Product p);
    void startClient();

    ArrayList<Order> getAllOrders();
    ArrayList<Product> getAllProducts();

    ArrayList<Employee> getAllWorkers();

    void addWorker(Employee employee);

    void removeWorker(Employee employee);
}
