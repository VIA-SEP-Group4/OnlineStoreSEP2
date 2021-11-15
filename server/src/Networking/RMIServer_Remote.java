package Networking;

import Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServer_Remote extends Remote
{
  int userCount() throws RemoteException;
  String registerUser(User newUser)throws RemoteException;
  void loginUser(User loggingUser) throws RemoteException;
  void registerClient(RMIClient_Remote client) throws RemoteException;
  void removeClient(RMIClient_Remote client) throws  RemoteException;
}
