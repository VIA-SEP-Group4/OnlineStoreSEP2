package Networking;

import Enums.EmployeeType;
import Model.Models.Customer;
import Model.Models.Employee;
import View.AccountSettings.AccountDeletedExceptionReply;
import View.AccountSettings.AccountEditedExceptionReply;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CredentialsClientImpl implements CredentialsClient, CredentialsClientRemote {
    private CredentialsServerRemote credentialsServer;
    private PropertyChangeSupport support;
    private Customer loggedCustomer = null;
    private Employee loggedEmployee = null;

    public CredentialsClientImpl() {
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void startClient() {
        try {
            //export client
            UnicastRemoteObject.exportObject(this, 0);

            //lookup server stub
            credentialsServer = (CredentialsServerRemote) Naming.lookup("rmi://localhost:1099/credentialsServer");
            credentialsServer.registerClient(this);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println("failed to initialize client-object ...[CredentialsClientImpl.startClient()]");
        }
    }


    @Override
    public void loginEmployee(int ID, int pin)  {
        String reply = "denied";
        try {
            reply = credentialsServer.loginEmployee(ID,pin, this);
        } catch (RemoteException | RuntimeException e) {
            System.err.println("Server error! Customer logging failed! [RMIClient.registerUser()]");
            e.printStackTrace();
        }
        support.firePropertyChange("LoginReply",null, reply);
    }

    /**
     * Method requesting to check logging user's presence in the database and if password matches.
     * @param username inserted username
     * @param password inserted password
     */
    @Override
    public void loginCustomer(String username, String password) {
        String reply = "denied";
        try {
            reply = credentialsServer.loginCustomer(username, password, this);
        } catch (RemoteException | RuntimeException e) {
            System.err.println("Server error! Customer logging failed! [RMIClient.registerUser()]");
            e.printStackTrace();
        }
        support.firePropertyChange("LoginReply",null, reply);
    }



    @Override
    public void editEmployee(Employee e) {
        try {
            credentialsServer.editEmployee(e);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addEmployee(Employee e) {
        String reply="Denied";
        try {
            reply= credentialsServer.addEmployee(e);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        if(e.getType()== EmployeeType.WAREHOUSE_WORKER)
            support.firePropertyChange("AddedWorker",null,reply);
        else if(e.getType()==EmployeeType.MANAGER) support.firePropertyChange("ManagerAddReply",null,reply);
    }

    @Override
    public void removeEmployee(Employee e) {
        try {
            credentialsServer.removeEmployee(e);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<Employee> getEmployees(String type) {
        if(type.equals("Worker")) {
            try {
                return credentialsServer.getWorkers();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("Manager")){
            try {
                return credentialsServer.getManagerEmployees();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override public void deleteCustomer()
    {
        String reply;
        try {
            reply = credentialsServer.deleteCustomer(loggedCustomer.getCustomerId());
        } catch (RemoteException e) {
            reply = "Account deleting failed";
        }
        throw new AccountDeletedExceptionReply(reply);
    }

    @Override public void editCustomer(Customer editedCustomer)
    {
        //add orders and ID to the edited customer
        Customer tempCustomer = new Customer(editedCustomer.getUsername(),
            editedCustomer.getPassword(), editedCustomer.getEmail(),
            editedCustomer.getFirstName(), editedCustomer.getLastName(),
            loggedCustomer.getCustomerId());
        tempCustomer.setOrders(loggedCustomer.getOrders());


        String reply;
        try {
            reply = credentialsServer.editCustomer(tempCustomer, this);
        } catch (RemoteException e) {
            e.printStackTrace();
            reply = "Customer's information editing failed";
        }
        throw new AccountEditedExceptionReply(reply);
    }

    @Override public void logOutCustomer()
    {
        try
        {
            credentialsServer.removeClient(this);
            loggedCustomer = null;
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override public void logOutEmployee()
    {
        try
        {
            credentialsServer.removeClient(this);
            loggedEmployee = null;
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);
    }

    /**
     * Method requesting the server to register new user.
     * @param newCustomer new user to be added to the database.
     */
    @Override public void registerUser(Customer newCustomer)
    {
        try {
            String reply = credentialsServer.registerCustomer(newCustomer);
            System.out.println("Server reply: " + reply);
            support.firePropertyChange("RegistrationReply",null,reply);
        } catch (RemoteException e) {
            System.err.println("Customer registration failed! [RMIClient.registerUser()]");
            e.printStackTrace();
        }
    }



    @Override public void setLoggedCustomer(Customer loggedCustomer) throws RemoteException {
        this.loggedCustomer = loggedCustomer;
        support.firePropertyChange("LoggedCustomerObj", null, loggedCustomer);
    }

    public Customer getLoggedCustomer() {
        return loggedCustomer;
    }


    public Employee getLoggedEmployee() {
        return loggedEmployee;
    }

    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = loggedEmployee;
    }

    @Override
    public void receiveUpdatedEmployees(Object employees) throws RemoteException {
        PropertyChangeEvent event=(PropertyChangeEvent) employees;
        support.firePropertyChange(event);
    }

    @Override
    public void receiveUpdatedEmployeesDelete(PropertyChangeEvent event) throws RemoteException {
        support.firePropertyChange(event);
    }
}
