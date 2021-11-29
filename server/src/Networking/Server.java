package Networking;

import Model.Model;
import Model.User;
import Model.Product;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Class responsible for server side of the client-server communication, distributing the client's requests and sending back the responses,
 */
public class Server implements RMIServer_Remote, PropertyChangeListener {

  private Model serverModelManager;
  private ArrayList<RMIClient_Remote> clients;

  public Server(Model serverModelManager) throws RemoteException, MalformedURLException
  {
    super();
    clients=new ArrayList<>();
    this.serverModelManager = serverModelManager;

  }

  /**
   * Method responsible for launching/=exporting the server object.
   * @throws RemoteException
   * @throws MalformedURLException
   */
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

  @Override public String registerUser(User newUser) throws RemoteException
  {
    String reply;
    try
    {
      serverModelManager.registerUser(newUser);
      reply = "approved";
    }
    catch (RuntimeException e){
      reply = e.getMessage();
    }

    return reply;
  }

  @Override public String loginUser(String username, String password) throws RemoteException
  {
    String reply;
    try
    {
      serverModelManager.loginUser(username,password);
      reply = "successful login";
    }catch (RuntimeException e){
      reply = e.getMessage();
    }
    return reply;
  }

  @Override
  public void registerClient(RMIClient_Remote client) {
    clients.add(client);
  }

  @Override
  public void removeClient(RMIClient_Remote client) {
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

  @Override
  public void propertyChange(PropertyChangeEvent evt) {


  }
}
