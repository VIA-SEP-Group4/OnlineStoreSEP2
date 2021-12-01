package Networking;

import Model.Order;
import Model.Product;
import Utils.Subject;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public interface CustomerClient extends Subject {

    ArrayList<Product> getProducts();
    void startClient();
    void processOrder(Order newOrder);
}
