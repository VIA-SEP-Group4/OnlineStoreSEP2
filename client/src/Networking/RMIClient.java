package Networking;

import Model.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements Client, RMIClient_Remote
{

  private RMIServer_Remote serverStub;

  public RMIClient()
  {
    try {
      //export client
      UnicastRemoteObject.exportObject(this, 0);
      //lookup server stub
      serverStub = (RMIServer_Remote) Naming.lookup("rmi://localhost:1099/server");
    } catch (NotBoundException | MalformedURLException | RemoteException e) {
      System.err.println("failed to initialize client-object ...[RMIClient.RMIClient()]");
    }
  }


  @Override public void receiveReply(String reply) throws RemoteException
  {
    System.out.println("Received reply: " + reply);
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
    } catch (RemoteException e) {
      System.err.println("User registration failed! [RMIClient.registerUser()]");
      e.printStackTrace();
    }
  }

  @Override public void loginUser(User loggingUser)
  {
    try {
      String reply = serverStub.loginUser(loggingUser);
      System.out.println("Server reply: " + reply);
    } catch (RemoteException e) {
      System.err.println("User login failed! [RMIClient.loginUser()]");
    }
  }

}
