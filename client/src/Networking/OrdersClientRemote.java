package Networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OrdersClientRemote extends Remote {
    void receiveUpdatedOrder(Object updatedOrder) throws RemoteException;
}
