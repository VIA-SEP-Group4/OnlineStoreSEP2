package Networking;

import Model.Order;
import Model.Product;
import Model.User;
import Utils.Subject;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Client extends Subject
{
  int getNumberOfUsers() ;
  void registerUser(User newUser);
  void loginUser(String username, String password) ;
  void startClient();
  ArrayList<Product> getProducts();
  String getID();
  void addProduct(Product p);
  void deleteProduct(Product p);
  User getLoggedUser();
  void processOrder(Order newOrder);
}
