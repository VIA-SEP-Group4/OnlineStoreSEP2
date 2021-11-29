package Networking;

import Model.Product;
import Model.User;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Class responsible for communication with the server.
 */
public class RMIClient implements Client, RMIClient_Remote
{
  private RMIServer_Remote serverStub;
  private String clientID;
  private PropertyChangeSupport support;

  public RMIClient()
  {
    clientID= "";
    support=new PropertyChangeSupport(this);
  }

  /**
   * Method exporting the client-object and gaining reference of server-stub object
   */
  @Override
  public void startClient() {
    try {
      //export client
      UnicastRemoteObject.exportObject(this, 0);

      //lookup server stub
      serverStub = (RMIServer_Remote) Naming.lookup("rmi://localhost:1099/server");
      serverStub.registerClient(this);
    } catch (NotBoundException | MalformedURLException | RemoteException e) {
      System.err.println("failed to initialize client-object ...[RMIClient.RMIClient()]");
    }
  }

  /**
   * Method used to request products' data from the database
   * @return list of products
   */
  @Override public ArrayList<Product> getProducts()
  {
    try
    {
      return serverStub.getProducts();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    } return null;
  }

  @Override public void receiveReply(String reply) throws RemoteException
  {
    support.firePropertyChange("LoginReply",null,reply);
  }

  @Override
  public String getID() throws RemoteException {
    return clientID;
  }

  /**
   * Method used to send request to the server requesting adding new product to the database
   * @param p new product to be added.
   * @throws RemoteException
   */
  @Override
  public void addProduct(Product p) throws RemoteException {
    serverStub.addProduct(p);
  }

  /**
   * Method used to delete one product from the database
   * @param p the product to be deleted
   * @throws RemoteException
   */
  @Override public void deleteProduct(Product p) throws RemoteException
  {
    serverStub.deleteProduct(p);
  }

  /**
   * Method requesting number of registered users.
   * @return
   */
  @Override public int getNumberOfUsers()
  {
    int numOfUsers = 0;
    try
    {
      numOfUsers = serverStub.userCount();
    }
    catch (RemoteException e)
    {
      System.err.println("User count failed! [RMIClient.userCount()]");
    }
    return numOfUsers;
  }

  /**
   * Method requesting the server to register new user.
   * @param newUser new user to be added to the database.
   */
  @Override public void registerUser(User newUser)
  {
    try {
      String reply = serverStub.registerUser(newUser);
      System.out.println("Server reply: " + reply);
      support.firePropertyChange("RegistrationReply",null,reply);
    } catch (RemoteException e) {
      System.err.println("User registration failed! [RMIClient.registerUser()]");
      e.printStackTrace();
    }
  }

  /**
   * Method requesting to check logging user's presence in the database and if password matches.
   * @param username inserted username
   * @param password inserted password
   */
  @Override public void loginUser(String username, String password)
  {
    String reply = "denied";
    try {
      reply = serverStub.loginUser(username, password);
    } catch (RemoteException | RuntimeException e) {
      System.err.println("Server error! User logging failed! [RMIClient.registerUser()]");
      e.printStackTrace();
    }
    support.firePropertyChange("LoginReply",null, reply);
  }


  @Override
  public void addListener(String eventName, PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName,listener);
  }

  @Override
  public void removeListener(String eventName, PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName,listener);
  }
}
