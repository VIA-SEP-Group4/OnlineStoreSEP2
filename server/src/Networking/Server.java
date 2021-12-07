package Networking;


import Model.*;
import Model.Models.Customer;
import Model.Models.Employee;
import Model.Models.Order;
import Model.Models.Product;

import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Class responsible for server side of the client-server communication, distributing the client's requests and sending back the responses,
 */
public class Server implements RMIServer_Remote{

  private Model serverModelManager;
  private ArrayList<Remote> clients;

  public Server(Model serverModelManager) throws RemoteException, MalformedURLException
  {
    super();
    clients=new ArrayList<>();
    this.serverModelManager = serverModelManager;
    serverModelManager.addListener("ProductReply",this::productsUpdate);
    serverModelManager.addListener("AdminReply",this::adminUpdate);
    serverModelManager.addListener("ManagerReply",this::managerUpdate);
    serverModelManager.addListener("newOrder",this::managerOrderUpdate);
  }

  private void managerOrderUpdate(PropertyChangeEvent event) {
    for(Remote client: clients){
      if(client instanceof ManagerRemoteClient){
        try {
          ((ManagerRemoteClient) client).receiveUpdatedOrders(event.getNewValue());
        } catch (RemoteException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void managerUpdate(PropertyChangeEvent event) {
    for(Remote client: clients){
      if(client instanceof ManagerRemoteClient)
        try {
          ((ManagerRemoteClient) client).receiveUpdatedManagers(event.getNewValue());
        } catch (RemoteException e) {
          e.printStackTrace();
        }
    }
  }

  private void adminUpdate(PropertyChangeEvent event) {
    for(Remote client: clients){
      if(client instanceof AdminRemoteClient){
        try {
          ((AdminRemoteClient) client).receiveUpdatedManagers(event.getNewValue());
        } catch (RemoteException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void productsUpdate(PropertyChangeEvent event) {
    for(Remote client: clients){
        if(client instanceof ManagerRemoteClient ) {
          try {
            ((ManagerRemoteClient) client).receiveUpdatedProducts(event.getNewValue());
          } catch (RemoteException e) {
            e.printStackTrace();
          }
        }
        else if(client instanceof CustomerRemoteClient) {
          try {
            ((CustomerRemoteClient) client).receiveUpdatedProducts(event.getNewValue());
          } catch (RemoteException e) {
            e.printStackTrace();
          }
        }
    }
  }

  public void start() throws RemoteException, MalformedURLException
  {
    UnicastRemoteObject.exportObject(this, 1099);
    Naming.rebind("server", this);
  }

  /**
   * Method forwarding the task to another object and sending the result back to the client.
   * @return number of registered users.
   * @throws RemoteException
   */
  @Override public int userCount() throws RemoteException
  {
    return serverModelManager.userCount();
  }

  @Override public String registerUser(Customer newUser) throws RemoteException
  {
    String reply;
    try
    {
      serverModelManager.registerCustomer(newUser);
      reply = "approved";
    }
    catch (RuntimeException e){
      reply = e.getMessage();
    }

    return reply;
  }

  @Override public String loginCustomer(String username, String password,LoginRemoteClient client) throws RemoteException
  {
    String reply;
    try
    {
      Customer loggedUser = serverModelManager.loginCustomer(username,password);
      reply = "successful login ";
      client.setLoggedUser(loggedUser);

    }catch (RuntimeException e){
      reply = e.getMessage();
    }
    return reply;
  }

  @Override
  public String loginEmployee(int id, int pin) throws RemoteException {
    String reply;
    try
    {
      Employee loggedUser = serverModelManager.loginEmployee(id,pin);
      reply = "successful login "+loggedUser.getType();
      System.out.println(reply);

    }catch (RuntimeException e){
      reply = e.getMessage();
    }
    return reply;
  }

  @Override
  public void registerClient(Remote client) {
    clients.add(client);
  }

  @Override
  public void removeClient(Remote client) {
    clients.remove(client);
  }

  @Override
  public ArrayList<Product> getProducts() throws RemoteException {

    return serverModelManager.getProducts();
  }


  @Override
  public void addProduct(Product product) throws RemoteException {
      serverModelManager.addProduct(product);
  }

  @Override public void deleteProduct(Product p) throws RemoteException
  {
    serverModelManager.deleteProduct(p);
  }

  @Override public void addNewOrder(Order newOrder)
  {
    serverModelManager.addNewOrder(newOrder);
  }

  @Override public ArrayList<Order> getOrders(int customerId)
  {
    return serverModelManager.getOrders(customerId);
  }

  @Override public void changeOrderAssignee(Order order) throws RemoteException
  {
    serverModelManager.changeOrderAssignee(order);
  }

  @Override public void addToCart(Product p, int desiredQuantity) throws RemoteException
  {
    serverModelManager.updateStock(p, desiredQuantity);
  }

  @Override
  public ArrayList<Employee> getManagerEmployees() {
    System.out.println(serverModelManager.getManagers());
    return serverModelManager.getManagers();
  }

  @Override
  public String addManager(Employee manager) throws RemoteException {
    String reply;
    try
    {
      serverModelManager.registerEmployee(manager);
      reply = "approved";
    }
    catch (RuntimeException e){
      reply = e.getMessage();
    }

    return reply;
  }

  @Override
  public void removeManager(Employee manager) throws RemoteException {
      serverModelManager.removeEmployee(manager);
  }

  @Override
  public ArrayList<Employee> getWorkers() throws RemoteException {
    return serverModelManager.getWorkers();
  }

  @Override
  public String addWorker(Employee e) throws RemoteException {
    String reply;
    try
    {
      serverModelManager.registerEmployee(e);
      reply = "approved";
    }
    catch (RuntimeException re){
      reply = re.getMessage();
    }

    return reply;
  }

  @Override
  public void removeWorker(Employee e) throws RemoteException {
    serverModelManager.removeEmployee(e);
  }

  @Override public ArrayList<Order> getAllOrders() throws RemoteException
  {
    return serverModelManager.getAllOrders();
  }

  @Override
  public ArrayList<Order> getWorkerOrdersForManager(int workerID) throws RemoteException {
    return serverModelManager.getWorkerOrdersForManager(workerID);
  }

  @Override
  public ArrayList<Order> getOrdersForWorker(int workerID) throws RemoteException {
    return null;
  }
}
