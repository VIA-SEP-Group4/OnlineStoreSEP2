package Networking;

import Model.Models.Order;
import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface CustomerClient extends Subject {

    ArrayList<Product> getProducts();
    void startClient();
    void processOrder(Order newOrder);
    ArrayList<Order> getOrders(int customerId);
    void addToCart(Product p, int desiredQuantity);
}
