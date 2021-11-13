package Networking;

import Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServer_Remote extends Remote
{
  void messageToServer(String message) throws RemoteException;
  void messageToServer(RMIClient_Remote client, String message) throws RemoteException;

  String toUpperCase(String text) throws RemoteException;

  int userCount() throws RemoteException;
  String registerUser(User newUser)throws RemoteException;
  String loginUser(User loggingUser) throws RemoteException;
}
