package Networking;

import Model.Models.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ProductsServerRemote extends Remote {
    ArrayList<Product> getProducts() throws RemoteException;
    void addProduct(Product product) throws RemoteException;
    void deleteProduct(Product p) throws RemoteException;
    void editProduct(Product p) throws RemoteException;
    void addToCart(Product p, int desiredQuantity) throws RemoteException;
    void registerClient(ProductsClientRemote client) throws RemoteException;
    void removeClient(ProductsClientRemote client) throws RemoteException;
}
