package Networking;

import Model.Models.Employee;
import Model.Models.Order;
import Model.Models.Product;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ManagerClientImpl implements ManagerClient, ManagerRemoteClient {
    private RMIServer_Remote serverStub;
    private PropertyChangeSupport support;

    public ManagerClientImpl() {
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

    @Override
    public ArrayList<Order> getAllOrders() {
        try {
            return serverStub.getAllOrders();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        try {
            return serverStub.getProducts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Employee> getAllWorkers() {
        return null;
    }

    @Override
    public void addWorker(Employee employee) {

    }

    @Override
    public void removeWorker(Employee employee) {

    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);
    }

    @Override
    public void addProduct(Product p) {
        try
        {
            serverStub.addProduct(p);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(Product p) {
        try
        {
            serverStub.deleteProduct(p);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void receiveUpdatedProducts(Object products) throws RemoteException {
        support.firePropertyChange("ProductsReply",null,products);
    }
}
