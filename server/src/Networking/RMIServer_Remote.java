package Networking;

import Model.Order;
import Model.Product;
import Model.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServer_Remote extends Remote
{
  int userCount() throws RemoteException;
  String registerUser(Customer newUser)throws RemoteException;
  String loginUser(String username, String password, String loggingClient,LoginRemoteClient client) throws RemoteException;

  void registerClient(Remote client) throws RemoteException;
  void removeClient(Remote client) throws  RemoteException;

  ArrayList<Product> getProducts() throws RemoteException;
  void addProduct(Product product) throws RemoteException;
  void deleteProduct(Product p) throws RemoteException;

  void addNewOrder(Order newOrder) throws RemoteException;
  ArrayList<Order> getOrders(int customerId) throws RemoteException;

  void addToCart(Product p, int desiredQuantity) throws RemoteException;
}
