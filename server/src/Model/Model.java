package Model;

import Utils.Subject;

import java.util.ArrayList;

public interface Model extends Subject
{
  ArrayList<Employee> getManagers();
  ArrayList<Employee> getWorkers();
  void registerUser(Customer newUser);
  Customer loginCustomer(String username, String password);
  int userCount();
  Employee loginEmployee(int ID, int pin);
  ArrayList<Product> getProducts();
  void addProduct(Product p);
  void deleteProduct(Product p);

  void addNewOrder(Order newOrder);
  ArrayList<Order> getOrders(int customerId);

  void updateStock(Product p, int desiredQuantity);
}
