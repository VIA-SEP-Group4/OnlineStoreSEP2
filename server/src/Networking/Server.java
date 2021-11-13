package Networking;

import Model.Model;
import Model.User;
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

  @Override public String loginUser(User loggingUser) throws RemoteException
  {
    String reply;
    try
    {
      serverModelManager.loginUser(loggingUser);
      reply = "approved";
    }catch (RuntimeException e){
      reply = e.getMessage();
    }

    return reply;
  }
}
