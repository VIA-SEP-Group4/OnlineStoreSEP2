package Model;

import Utils.Subject;

import java.util.ArrayList;

public interface LoginModel extends Subject
{
  void login(String username, String password);
  void registerUser(User newUser);
  int getNumberOfUsers();
  void addBasket(Product product);
  String getId();
  ArrayList<Product> getProducts(int index);
  ArrayList<Product> getBasket();
}
