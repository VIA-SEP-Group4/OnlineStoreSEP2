package Networking;

import Model.Models.Customer;
import Model.Models.Employee;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginRemoteClient extends Remote {
    void setLoggedCustomer(Customer loggedUser) throws RemoteException;
    void setLoggedEmployee(Employee loggedEmployee) throws RemoteException;
}
