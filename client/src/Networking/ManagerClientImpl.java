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
            System.err.println("failed to initialize client-object ...[ManagerClientImpl.startClient()]");
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
    public ArrayList<Order> getWorkerOrdersForManager(int workerID) {
        try {
            return serverStub.getWorkerOrdersForManager(workerID);
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
        try {
            return serverStub.getWorkers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addWorker(Employee employee) {

        String reply=null;
        try {
            reply=serverStub.addWorker(employee);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        support.firePropertyChange("AddedWorker",null,reply);
    }

    @Override
    public void removeWorker(Employee employee) {
        try {
            serverStub.removeWorker(employee);
        } catch (RemoteException e) {
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

    @Override
    public void addProduct(Product p) {
        String reply;
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

    @Override
    public void receiveUpdatedManagers(Object workers) throws RemoteException {
        support.firePropertyChange("ManagerWorkersReply",null,workers);
    }

    @Override
    public void receiveUpdatedOrders(Object orders) throws RemoteException {
        support.firePropertyChange("newOrder",null,orders);
    }
}
