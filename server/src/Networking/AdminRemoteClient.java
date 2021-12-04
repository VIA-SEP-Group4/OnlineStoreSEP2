package Networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AdminRemoteClient extends Remote {
    void receiveUpdatedManagers(Object managers) throws RemoteException;
}
