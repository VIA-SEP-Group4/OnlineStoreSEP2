package Networking;

import Model.Models.Employee;
import Model.Models.Order;
import Model.Models.Product;
import Model.Models.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServer_Remote extends Remote
{
  int userCount() throws RemoteException;
  String registerUser(Customer newCustomer)throws RemoteException;
  String loginCustomer(String username, String password, LoginRemoteClient client) throws RemoteException;
  String loginEmployee(int id, int pin, LoginRemoteClient client) throws RemoteException;

  void registerClient(Remote client) throws RemoteException;
  void removeClient(Remote client) throws RemoteException;

  ArrayList<Product> getProducts() throws RemoteException;
  void addProduct(Product product) throws RemoteException;
  void deleteProduct(Product p) throws RemoteException;
  void editProduct(Product p) throws RemoteException;
  void addNewOrder(Order newOrder) throws RemoteException;
  ArrayList<Order> getOrders(int customerId) throws RemoteException;
  ArrayList<Order> getAllOrders() throws RemoteException;
  ArrayList<Order> getWorkerOrdersForManager(int workerID) throws RemoteException;
  ArrayList<Order> getOrdersForWorker(int workerID) throws RemoteException;
  void changeOrderAssignee(Order order) throws RemoteException;
  void updateOrderStatus(Order order, String status) throws RemoteException;

  void addToCart(Product p, int desiredQuantity) throws RemoteException;

  ArrayList<Employee> getManagerEmployees() throws RemoteException;
  ArrayList<Employee> getWorkers() throws RemoteException;

  String addManager(Employee manager) throws RemoteException;
  String addWorker(Employee e) throws RemoteException;

  void removeWorker(Employee e) throws RemoteException;
  void removeManager(Employee manager) throws  RemoteException;

  void editEmployee(Employee e) throws RemoteException;


  //TODO ... added 9.12.12:50
  String deleteCustomer(int customerId) throws RemoteException;
  String editCustomer(Customer editedCustomer, LoginRemoteClient loginClient) throws RemoteException;
}
