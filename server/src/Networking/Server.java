package Networking;

import Model.Model;
import Model.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements RMIServer_Remote, PropertyChangeListener {

  private Model serverModelManager;
  private ArrayList<RMIClient_Remote> clients;
  public Server(Model serverModelManager) throws RemoteException, MalformedURLException
  {
    super();
    clients=new ArrayList<>();
    this.serverModelManager = serverModelManager;
    serverModelManager.addListener("Validation",this);
  }

  public void start() throws RemoteException, MalformedURLException
  {
    UnicastRemoteObject.exportObject(this, 1099);
    Naming.rebind("server", this);
  }
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

  @Override public void loginUser(User loggingUser) throws RemoteException
  {
      serverModelManager.loginUser(loggingUser);
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
  public void propertyChange(PropertyChangeEvent evt) {
    for(RMIClient_Remote client : clients){

      try {
        System.out.println(client.getID());
        if(client.getID().equals(evt.getOldValue()))
          client.receiveReply(evt.getNewValue().toString());
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

  }
}
