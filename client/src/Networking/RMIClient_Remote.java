package Networking;

import Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClient_Remote extends Remote
{
  void receiveReply(String reply) throws RemoteException;
  String getID() throws RemoteException;
  void receiveUpdatedProducts(Object products) throws RemoteException;

  void setLoggedUser(User loggedUser) throws RemoteException;
}
