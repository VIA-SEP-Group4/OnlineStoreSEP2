package Networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProductsClientRemote extends Remote {
    void receiveUpdatedProducts(Object products) throws RemoteException;
}
