package Networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CustomerRemoteClient extends Remote {
    void receiveUpdatedProducts(Object products) throws RemoteException;
    void receiveUpdatedOrders(Object orders) throws RemoteException;
}
