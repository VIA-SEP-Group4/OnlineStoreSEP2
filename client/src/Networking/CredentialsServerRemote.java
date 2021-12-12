package Networking;

import Model.Models.Customer;
import Model.Models.Employee;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface CredentialsServerRemote extends Remote {
    String registerCustomer(Customer newCustomer)throws RemoteException;
    String loginCustomer(String username, String password, CredentialsClientRemote client) throws RemoteException;
    String loginEmployee(int id, int pin, CredentialsClientRemote client) throws RemoteException;
    ArrayList<Employee> getManagerEmployees() throws RemoteException;
    ArrayList<Employee> getWorkers() throws RemoteException;

    String addEmployee(Employee e) throws RemoteException;
    void removeEmployee(Employee e) throws RemoteException;
    void editEmployee(Employee e) throws RemoteException;

    String deleteCustomer(int customerId) throws RemoteException;
    String editCustomer(Customer editedCustomer, CredentialsClientRemote loginClient) throws RemoteException;

    void registerClient(CredentialsClientRemote client) throws RemoteException;
    void removeClient(CredentialsClientRemote client) throws RemoteException;
}
