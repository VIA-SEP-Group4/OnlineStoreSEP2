package Networking;

import Model.User;
import Utils.Subject;

import java.rmi.RemoteException;

public interface Client extends Subject
{
  int getNumberOfUsers() ;
  void registerUser(User newUser);
  void loginUser(String username, String password) ;
  void startClient();
}
