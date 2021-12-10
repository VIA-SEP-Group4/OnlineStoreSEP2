package Networking;

import Model.CredentialsModel;
import Model.Models.Customer;
import Model.Models.Employee;

import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CredentialsServer implements CredentialsServerRemote {
    private CredentialsModel modelManager;
    private ArrayList<CredentialsClientRemote> clients;

    public CredentialsServer(CredentialsModel modelManager) {
        super();
        this.modelManager = modelManager;
        clients=new ArrayList<>();
        modelManager.addListener("AdminReply",this::employeeUpdate);
        modelManager.addListener("ManagerReply",this::employeeUpdate);
    }
    public void start() throws RemoteException, MalformedURLException
    {
        UnicastRemoteObject.exportObject(this, 1099);
        Naming.rebind("credentialsServer", this);
        System.out.println("Credentials server started");
    }
    private void employeeUpdate(PropertyChangeEvent event) {
        for(CredentialsClientRemote client: clients){
            try {
                client.receiveUpdatedEmployees(event.getNewValue());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String registerUser(Customer newCustomer) throws RemoteException {
        String reply;
        try
        {
            modelManager.registerCustomer(newCustomer);
            reply = "approved";
        }
        catch (RuntimeException e){
            reply = e.getMessage();
        }

        return reply;
    }

    @Override public String loginCustomer(String username, String password, CredentialsClientRemote client) throws RemoteException
    {
        String reply;
        try
        {
            Customer loggedCustomer = modelManager.loginCustomer(username,password);
            reply = "successful login ";
            client.setLoggedCustomer(loggedCustomer);
        }catch (RuntimeException e){
            reply = e.getMessage();
        }
        return reply;
    }

    @Override
    public String loginEmployee(int id, int pin, CredentialsClientRemote client) throws RemoteException {
        String reply;
        try
        {
            Employee loggedEmployee = modelManager.loginEmployee(id,pin);
            reply = "successful login "+loggedEmployee.getType();
            client.setLoggedEmployee(loggedEmployee);
            System.out.println(reply);

        }catch (RuntimeException e){
            reply = e.getMessage();
        }
        return reply;
    }


    @Override
    public ArrayList<Employee> getManagerEmployees() {
        return modelManager.getManagers();
    }


    @Override
    public void editEmployee(Employee e) {
        modelManager.editEmployee(e);
    }

    @Override public String deleteCustomer(int customerId) throws RemoteException
    {
        String reply;
        try {
            modelManager.deleteCustomer(customerId);
            reply = "Account deleted successfully";
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            reply = e.getMessage();
        }
        return reply;
    }

    @Override public String editCustomer(Customer editedCustomer, CredentialsClientRemote loginClient) throws RemoteException
    {
        String reply = "Customer's information edited successfully";
        try
        {
            modelManager.editCustomer(editedCustomer);
            loginClient.setLoggedCustomer(editedCustomer);
        }catch (RuntimeException ex){
            reply = ex.getMessage();
        }
        return reply;
    }

    @Override
    public ArrayList<Employee> getWorkers() throws RemoteException {
        return modelManager.getWorkers();
    }

    @Override
    public String addEmployee(Employee e) throws RemoteException {
        String reply;
        try
        {
            modelManager.registerEmployee(e);
            reply = "approved";
        }
        catch (RuntimeException re){
            reply = re.getMessage();
        }

        return reply;
    }

    @Override
    public void removeEmployee(Employee e) throws RemoteException {
        modelManager.removeEmployee(e);
    }

    @Override
    public void registerClient(CredentialsClientRemote client) {
        clients.add(client);
    }

    @Override
    public void removeClient(CredentialsClientRemote client) {
        clients.remove(client);
    }
}
