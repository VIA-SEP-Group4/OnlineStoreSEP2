package Model;

import Model.Models.Employee;
import Model.Models.Order;
import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface ManagerModel extends Subject {
    ArrayList<Order> getAllOrders();
    ArrayList<Order> getWorkerOrdersForManager(int workerID);
    ArrayList<Product> getProducts();
    ArrayList<Employee> getWorkers();

    void addProduct(Product p);
    void deleteProduct(Product p);
    void addWorker(Employee employee);
    void removeWorker(Employee employee);

    Employee getLoggedEmployee();

}
