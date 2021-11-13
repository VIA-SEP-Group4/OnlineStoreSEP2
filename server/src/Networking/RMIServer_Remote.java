package Networking;

import Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServer_Remote extends Remote
{
  int userCount() throws RemoteException;
  String registerUser(User newUser)throws RemoteException;
  String loginUser(User loggingUser) throws RemoteException;
}
