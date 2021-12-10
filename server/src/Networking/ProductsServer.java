package Networking;

import Model.Models.Product;
import Model.ProductsModel;

import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ProductsServer implements ProductsServerRemote {
    private ProductsModel modelManager;
    private ArrayList<ProductsClientRemote> clients;

    public ProductsServer(ProductsModel modelManager) {
        super();
        this.modelManager = modelManager;
        clients = new ArrayList<>();
        modelManager.addListener("ProductReply", this::productsUpdate);
    }
    public void start() throws RemoteException, MalformedURLException {
        UnicastRemoteObject.exportObject(this, 1099);
        Naming.rebind("productsServer", this);
        System.out.println("Products server started");
    }
    private void productsUpdate(PropertyChangeEvent event) {
        for (ProductsClientRemote client : clients) {
            try {
                client.receiveUpdatedProducts(event.getNewValue());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public ArrayList<Product> getProducts() throws RemoteException {

        return modelManager.getProducts();
    }


    @Override
    public void addProduct(Product product) throws RemoteException {
        modelManager.addProduct(product);
    }

    @Override public void deleteProduct(Product p) throws RemoteException
    {
        modelManager.deleteProduct(p);
    }

    @Override
    public void editProduct(Product p) throws RemoteException {
        modelManager.editProduct(p);
    }

    @Override
    public void updateStock(Product p, int prodQuantity) throws RemoteException {
        modelManager.updateStock(p, prodQuantity);
    }
    @Override public ArrayList<Product> getProducts(int page, int pagQuant) throws RemoteException
    {
        return modelManager.getProducts(page,pagQuant);
    }

    @Override public ArrayList<Product> getFilterProd(int page, int pagQuant, String type) throws RemoteException
    {
        return modelManager.getFilterProd(page,pagQuant,type);
    }
    @Override
    public void registerClient(ProductsClientRemote client) throws RemoteException {
        clients.add(client);
    }

    @Override
    public void removeClient(ProductsClientRemote client) throws RemoteException {
        clients.remove(client);
    }
}
