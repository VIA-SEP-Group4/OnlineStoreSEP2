package Networking;

import Model.Product;
import Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIClient_Remote extends Remote
{
  void receiveReply(String reply) throws RemoteException;
  String getID() throws RemoteException;
  void receiveUpdatedProducts(Object products) throws RemoteException;

  void setLoggedUser(User loggedUser) throws RemoteException;
}
