package Networking;

import Model.Models.Employee;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class AdminClientImpl implements AdminClient,AdminRemoteClient{
    private RMIServer_Remote serverStub;
    private PropertyChangeSupport support;
    public AdminClientImpl() {
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
            System.err.println("Admin Client failed to initialize client-object ...[RMIClient.RMIClient()]");
            e.printStackTrace();
        }
    }
    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public ArrayList<Employee> getManagers() {
        try {
            System.out.println(serverStub.getManagerEmployees());
            return serverStub.getManagerEmployees();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addManager(Employee manager) {
        String reply=null;
        try {
            reply=serverStub.addManager(manager);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        support.firePropertyChange("ManagerAddReply",null,reply);
    }

    @Override
    public void removeManager(Employee manager) {
        try {
            serverStub.removeManager(manager);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveUpdatedManagers(Object managers) throws RemoteException {
        support.firePropertyChange("AdminReply",null,managers);
    }
}
