package Model;

import Model.Models.Customer;
import Model.Models.Employee;
import Model.Models.Order;
import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface Model extends Subject
{
  ArrayList<Employee> getManagers();
  ArrayList<Employee> getWorkers();
  void registerCustomer(Customer newUser);
  void registerEmployee(Employee employee);
  Customer loginCustomer(String username, String password);
  int userCount();
  Employee loginEmployee(int ID, int pin);
  ArrayList<Product> getProducts();
  void addProduct(Product p);
  void deleteProduct(Product p);
  void removeEmployee(Employee e);
  void addNewOrder(Order newOrder);
  ArrayList<Order> getOrders(int customerId);

  void updateStock(Product p, int desiredQuantity);
}
