package Networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClient_Remote extends Remote
{
  void receiveReply(String reply) throws RemoteException;
}
