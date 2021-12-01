package Networking;

import Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginRemoteClient extends Remote {
    void setLoggedUser(User loggedUser) throws RemoteException;
}
