package Networking;

import Model.Models.Product;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ProductsClientImpl implements ProductsClient,ProductsClientRemote {
    private PropertyChangeSupport support;
    private ProductsServerRemote serverStub;

    public ProductsClientImpl() {
        support=new PropertyChangeSupport(this);
    }

    @Override
    public void startClient() {
        try {
            //export client
            UnicastRemoteObject.exportObject(this, 0);

            //lookup server stub
            serverStub = (ProductsServerRemote) Naming.lookup("rmi://localhost:1099/productsServer");
            serverStub.registerClient(this);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
            System.err.println("failed to initialize client-object ...[ProductsClientImpl.startClient()]");
        }
    }

    @Override
    public void addProduct(Product p) {
        try {
            serverStub.addProduct(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(Product p) {
        try {
            serverStub.deleteProduct(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editProduct(Product p) {
        try {
            serverStub.editProduct(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public void addToCart(Product p, int desiredQuantity) {
        try {
            serverStub.addToCart(p,desiredQuantity);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName,listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName,listener);
    }

    @Override
    public void receiveUpdatedProducts(Object products) throws RemoteException {
        support.firePropertyChange("ProductsReply",null,products);
    }
}
