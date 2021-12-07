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

//  private Model serverModelManager;

  private CredentialsModel credentialsModelManager;
  private ProductsModel productsModelManager;
  private OrdersModel ordersModelManager;

  private ArrayList<Remote> clients;

//  public Server(Model serverModelManager) throws RemoteException, MalformedURLException
//  {
//    super();
//    clients = new ArrayList<>();
//    this.serverModelManager = serverModelManager;
//    serverModelManager.addListener("ProductReply",this::productsUpdate);
//    serverModelManager.addListener("AdminReply",this::adminUpdate);
//    serverModelManager.addListener("ManagerReply",this::managerUpdate);
//    serverModelManager.addListener("newOrder",this::managerOrderUpdate);
//  }

  public Server(CredentialsModel cm ,ProductsModel pm, OrdersModel om) throws RemoteException, MalformedURLException
  {
    super();
    clients = new ArrayList<>();

    credentialsModelManager = cm;
    productsModelManager = pm;
    ordersModelManager = om;


    credentialsModelManager.addListener("AdminReply",this::adminUpdate);
    credentialsModelManager.addListener("ManagerReply",this::managerUpdate);
    credentialsModelManager.addListener("newOrder",this::managerOrderUpdate);

    productsModelManager.addListener("ProductReply",this::productsUpdate);

    ordersModelManager.addListener("newOrder",this::managerOrderUpdate);
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
    return credentialsModelManager.userCount();
  }

  @Override public String registerUser(Customer newUser) throws RemoteException
  {
    String reply;
    try
    {
      credentialsModelManager.registerCustomer(newUser);
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
      Customer loggedCustomer = credentialsModelManager.loginCustomer(username,password);
      reply = "successful login ";
      client.setLoggedCustomer(loggedCustomer);

    }catch (RuntimeException e){
      reply = e.getMessage();
    }
    return reply;
  }

  @Override
  public String loginEmployee(int id, int pin, LoginRemoteClient client) throws RemoteException {
    String reply;
    try
    {
      Employee loggedEmployee = credentialsModelManager.loginEmployee(id,pin);
      reply = "successful login "+loggedEmployee.getType();
      client.setLoggedEmployee(loggedEmployee);
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

    return productsModelManager.getProducts();
  }


  @Override
  public void addProduct(Product product) throws RemoteException {
      productsModelManager.addProduct(product);
  }

  @Override public void deleteProduct(Product p) throws RemoteException
  {
    productsModelManager.deleteProduct(p);
  }

  @Override public void addNewOrder(Order newOrder)
  {
    ordersModelManager.addNewOrder(newOrder);
  }

  @Override public ArrayList<Order> getOrders(int customerId)
  {
    return ordersModelManager.getOrders(customerId);
  }

  @Override public void changeOrderAssignee(Order order) throws RemoteException
  {
    ordersModelManager.changeOrderAssignee(order);
  }

  @Override public void updateOrderStatus(Order order, String status) throws RemoteException
  {
    ordersModelManager.updateOrderStatus(order,status);
  }

  @Override public void addToCart(Product p, int desiredQuantity) throws RemoteException
  {
    productsModelManager.updateStock(p, desiredQuantity);
  }

  @Override
  public ArrayList<Employee> getManagerEmployees() {
    return credentialsModelManager.getManagers();
  }

  @Override
  public String addManager(Employee manager) throws RemoteException {
    String reply;
    try
    {
      credentialsModelManager.registerEmployee(manager);
      reply = "approved";
    }
    catch (RuntimeException e){
      reply = e.getMessage();
    }

    return reply;
  }

  @Override
  public void removeManager(Employee manager) throws RemoteException {
      credentialsModelManager.removeEmployee(manager);
  }

  @Override
  public ArrayList<Employee> getWorkers() throws RemoteException {
    return credentialsModelManager.getWorkers();
  }

  @Override
  public String addWorker(Employee e) throws RemoteException {
    String reply;
    try
    {
      credentialsModelManager.registerEmployee(e);
      reply = "approved";
    }
    catch (RuntimeException re){
      reply = re.getMessage();
    }

    return reply;
  }

  @Override
  public void removeWorker(Employee e) throws RemoteException {
    credentialsModelManager.removeEmployee(e);
  }

  @Override public ArrayList<Order> getAllOrders() throws RemoteException
  {
    return ordersModelManager.getAllOrders();
  }

  @Override
  public ArrayList<Order> getWorkerOrdersForManager(int workerID) throws RemoteException {
    return ordersModelManager.getWorkerOrdersForManager(workerID);
  }

  @Override
  public ArrayList<Order> getOrdersForWorker(int workerID) throws RemoteException {
    return null;
  }
}
