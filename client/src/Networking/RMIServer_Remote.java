package Networking;

import Model.Product;
import Model.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServer_Remote extends Remote
{
  int userCount() throws RemoteException;
  String registerUser(User newUser)throws RemoteException;
  String loginUser(String username, String password) throws RemoteException;
  void registerClient(RMIClient_Remote client) throws RemoteException;
  void removeClient(RMIClient_Remote client) throws RemoteException;
  ArrayList<Product> getProducts(int index)throws RemoteException;
}
