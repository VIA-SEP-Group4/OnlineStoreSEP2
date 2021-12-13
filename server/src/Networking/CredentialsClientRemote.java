package Networking;

import Model.Models.Customer;
import Model.Models.Employee;

import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CredentialsClientRemote extends Remote {
    void setLoggedCustomer(Customer loggedUser) throws RemoteException;
    void setLoggedEmployee(Employee loggedEmployee) throws RemoteException;
    void receiveUpdatedEmployees(Object employees) throws RemoteException;

    void receiveUpdatedEmployeesDelete(PropertyChangeEvent event) throws RemoteException;
}
