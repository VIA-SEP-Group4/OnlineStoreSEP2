package Networking;

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
  String getID() throws RemoteException;
  void addProduct(Product p) throws RemoteException;
  void deleteProduct(Product p) throws RemoteException;
}
