package Networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ManagerRemoteClient extends Remote {
    void receiveUpdatedProducts(Object products) throws RemoteException;
}
