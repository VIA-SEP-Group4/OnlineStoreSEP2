package Model;

import Utils.Subject;

import java.util.ArrayList;

public interface Model extends Subject
{
  void registerUser(User newUser);
  User loginUser(String username, String password, String selectedUserType);
  int userCount();

  ArrayList<Product> getProducts();
  void addProduct(Product p);
  void deleteProduct(Product p);

  void addNewOrder(Order newOrder);
  ArrayList<Order> getOrders(int customerId);
}
