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
import java.util.Locale;
import java.util.UUID;

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

  @Override public ArrayList<Product> getProducts(int index)
  {
    try
    {
      return serverStub.getProducts(index);
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
