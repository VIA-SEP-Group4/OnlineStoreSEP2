package Networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServer_Remote extends Remote
{
  void messageToServer(String message) throws RemoteException;
  void messageToServer(RMIClient_Remote client, String message) throws RemoteException;

  String toUpperCase(String text) throws RemoteException;;
}
