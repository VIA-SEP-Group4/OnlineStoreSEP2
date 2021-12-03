package Networking;

import Model.Customer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginClientImpl implements LoginClient, LoginRemoteClient{
    private RMIServer_Remote serverStub;
    private String clientID;
    private PropertyChangeSupport support;
    private Customer loggedCustomer = null;

    public LoginClientImpl() {
        support=new PropertyChangeSupport(this);
    }

    @Override
    public void startClient() {
        try {
            //export client
            UnicastRemoteObject.exportObject(this, 0);

            //lookup server stub
            serverStub = (RMIServer_Remote) Naming.lookup("rmi://localhost:1099/server");
            serverStub.registerClient(this);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println("failed to initialize client-object ...[RMIClient.RMIClient()]");
        }
    }

    /** Method used to get the user that has just been succesfully logged in
     *
     * @return user as type Customer
     */
    @Override
    public Customer getLoggedUser() {
        return loggedCustomer;
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
    /**
     * Method requesting to check logging user's presence in the database and if password matches.
     * @param username inserted username
     * @param password inserted password
     */
    @Override
    public void loginUser(String username, String password,String type) {
        String reply = "denied";
        try {
            reply = serverStub.loginUser(username, password,type, this);
        } catch (RemoteException | RuntimeException e) {
            System.err.println("Server error! Customer logging failed! [RMIClient.registerUser()]");
            e.printStackTrace();
        }
        support.firePropertyChange("LoginReply",null, reply);
    }


    @Override
    public void setLoggedUser(Customer loggedCustomer) throws RemoteException {
        this.loggedCustomer = loggedCustomer;
    }


}