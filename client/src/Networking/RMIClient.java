package Networking;

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

  @Override public void sendMessage(String message)
  {
    try {
      serverStub.messageToServer(message);
      serverStub.messageToServer(this, message);
    } catch (RemoteException e) {
      System.out.println("Message failed to send ...[RMIClient.sendMessage()]");
    }
  }

  @Override public void toUpperCase(String text)
  {
    try {
      String uppercaseText = serverStub.toUpperCase(text);
      System.out.println("processed text: " + uppercaseText);
    } catch (RemoteException e)
    {
      System.out.println("Text failed to convert ...[RMIClient.toUppercase()]");
    }
  }

  @Override public void receiveReply(String reply) throws RemoteException
  {
    System.out.println("Received reply: " + reply);
  }


}
