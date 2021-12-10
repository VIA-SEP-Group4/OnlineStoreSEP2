package Networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OrdersClientRemote extends Remote {
    void receiveUpdatedOrders(Object orders) throws RemoteException;
}
