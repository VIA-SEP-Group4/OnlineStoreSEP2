package Networking;

import Model.User;

import java.rmi.RemoteException;

public interface Client
{
  int getNumberOfUsers() ;
  void registerUser(User newUser);
  void loginUser(User loggingUser) ;
}
