package Networking;

import Model.Models.Order;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class WorkerClientImpl implements WorkerClient, WorkerRemoteClient {

    private RMIServer_Remote serverStub;
    private PropertyChangeSupport support;
    private boolean started = false;

    public WorkerClientImpl(){
        support = new PropertyChangeSupport(this);
    }

    @Override public void startClient()
    {
        try {
            //export client
            UnicastRemoteObject.exportObject(this, 0);

            //lookup server stub
            serverStub = (RMIServer_Remote) Naming.lookup("rmi://localhost:1099/server");
            serverStub.registerClient(this);
            started = true;
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println("worker failed to initialize client-object ...[WorkerClientImpl.startClient()]");
        }
    }

    @Override public boolean isStarted()
    {
        return started;
    }

    @Override public ArrayList<Order> getAllOrders()
    {
        try
        {
            return serverStub.getAllOrders();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override public void changeOrderAssignee(Order order)
    {
        try
        {
            serverStub.changeOrderAssignee(order);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {

    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {

    }
}
