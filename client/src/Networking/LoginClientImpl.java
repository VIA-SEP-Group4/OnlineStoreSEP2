package Networking;

import Model.Models.Customer;
import Model.Models.Employee;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginClientImpl implements LoginClient, LoginRemoteClient{
    private RMIServer_Remote serverStub;
    private String clientId;
    private PropertyChangeSupport support;
    private Customer loggedCustomer = null;
    private Employee loggedEmployee=null;
    private boolean started=false;

    public LoginClientImpl() {
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void startClient() {
        try {
            //export client
            UnicastRemoteObject.exportObject(this, 0);

            //lookup server stub
            serverStub = (RMIServer_Remote) Naming.lookup("rmi://localhost:1099/server");
            serverStub.registerClient(this);
            started = true;
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println("failed to initialize client-object ...[LoginClientImpl.startClient()]");
        }
    }

    /**
     * Method used to get the user that has just been successfully logged in
     * @return user as type Customer
     */
    @Override
    public void loginEmployee(int ID, int pin)  {
        String reply = "denied";
        try {
            reply = serverStub.loginEmployee(ID,pin, this);
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
            reply = serverStub.loginCustomer(username, password, this);
        } catch (RemoteException | RuntimeException e) {
            System.err.println("Server error! Customer logging failed! [RMIClient.registerUser()]");
            e.printStackTrace();
        }
        support.firePropertyChange("LoggedCustomerObj", null, loggedCustomer);
        support.firePropertyChange("LoginReply",null, reply);
    }

    @Override
    public boolean isStarted() {
        return started;
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
            String reply = serverStub.registerUser(newCustomer);
            System.out.println("Server reply: " + reply);
            support.firePropertyChange("RegistrationReply",null,reply);
        } catch (RemoteException e) {
            System.err.println("Customer registration failed! [RMIClient.registerUser()]");
            e.printStackTrace();
        }
    }



    @Override public void setLoggedCustomer(Customer loggedCustomer) throws RemoteException {
        this.loggedCustomer = loggedCustomer;
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
}
