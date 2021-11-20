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
  ArrayList<Product> getProducts(int index);
  String getID() throws RemoteException;
}
