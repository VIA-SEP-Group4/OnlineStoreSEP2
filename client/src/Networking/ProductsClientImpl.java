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
    private ProductsServerRemote productsServer;

    public ProductsClientImpl() {
        support=new PropertyChangeSupport(this);
    }

    @Override
    public void startClient() {
        try {
            //export client
            UnicastRemoteObject.exportObject(this, 0);

            //lookup server stub
            productsServer = (ProductsServerRemote) Naming.lookup("rmi://localhost:1099/productsServer");
            productsServer.registerClient(this);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
            System.err.println("failed to initialize client-object ...[ProductsClientImpl.startClient()]");
        }
    }

    @Override
    public void addProduct(Product p) {
        try {
            productsServer.addProduct(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(Product p) {
        try {
            productsServer.deleteProduct(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editProduct(Product p) {
        try {
            productsServer.editProduct(p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        try {
            return productsServer.getProducts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateStock(Product p, int prodQuantity) {
        try {
            productsServer.updateStock(p,prodQuantity);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override public ArrayList<Product> getProducts(int page, int pagQuant)
    {
        try {
            return productsServer.getProducts(page,pagQuant);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override public ArrayList<Product> getFilterProd(int page, int pagQuant, String type)
    {
        try {
            return productsServer.getFilterProd(page,pagQuant,type);
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
