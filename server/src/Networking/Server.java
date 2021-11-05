package Networking;

import Model.Model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements RMIServer_Remote{

  private Model serverModelManager;
  public Server(Model serverModelManager) throws RemoteException, MalformedURLException
  {
    super();
    this.serverModelManager = serverModelManager;
  }

  public void start() throws RemoteException, MalformedURLException
  {
    UnicastRemoteObject.exportObject(this, 1099);
    Naming.rebind("server", this);
  }


  @Override public void messageToServer(String message) throws RemoteException
  {
    System.out.println("Message received: " + message);
  }
  @Override public void messageToServer(RMIClient_Remote client, String message) throws RemoteException
  {
    System.out.println("Message received: " + message);
    client.receiveReply("callback (" + message + ")");
  }


  @Override public String toUpperCase(String text) throws RemoteException
  {
    System.out.println("Message received: " + text);
    return serverModelManager.toUpperCase(text);
  }
}
