package Networking;

import Model.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginRemoteClient extends Remote {
    void setLoggedUser(Customer loggedCustomer) throws RemoteException;
}
