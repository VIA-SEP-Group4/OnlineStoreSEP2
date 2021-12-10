package Networking;

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

public class CustomerClientImpl implements CustomerClient, CustomerRemoteClient {
    private RMIServer_Remote serverStub;
    private PropertyChangeSupport support;

    public CustomerClientImpl() {
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
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println("failed to initialize client-object ...[CustomerClient.startClient()]");
        }
    }

    @Override
    public void processOrder(Order newOrder) {
        try {
            serverStub.addNewOrder(newOrder);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override public void updateOrderStatus(Order order, String status)
    {
        try {
            serverStub.updateOrderStatus(order, status);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override public ArrayList<Order> getOrders(int customerId)
    {
        try
        {
            return serverStub.getOrders(customerId);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        return new ArrayList<Order>();
    }

    @Override public void updateStock(Product p, int desiredQuantity)
    {
        try {

            serverStub.updateStock(p, desiredQuantity);
        }
        catch (RemoteException e) {
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
    public ArrayList<Product> getProducts() {
        try {
            return serverStub.getProducts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override public ArrayList<Product> getProducts(int page, int pagQuant)
    {
        try {
            return serverStub.getProducts(page,pagQuant);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override public ArrayList<Product> getFilterProd(int page, int pagQuant, String type)
    {
        try {
            return serverStub.getFilterProd(page,pagQuant,type);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void receiveUpdatedProducts(Object products) throws RemoteException {
        support.firePropertyChange("ProductsReply",null,products);
    }

    @Override public void receiveUpdatedOrders(Object orders) throws RemoteException
    {
        support.firePropertyChange("newOrder", null, orders);
    }
}
