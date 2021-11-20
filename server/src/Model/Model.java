package Model;

import Utils.Subject;

import java.util.ArrayList;

public interface Model extends Subject
{
  void registerUser(User newUser);
  void loginUser(String username, String password);
  int userCount();
  ArrayList<Product> getProducts(int index);
  void addProduct(Product p);
}
