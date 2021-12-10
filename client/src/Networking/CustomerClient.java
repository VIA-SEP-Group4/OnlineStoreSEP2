package Networking;

import Model.Models.Order;
import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface CustomerClient extends Subject {

    ArrayList<Product> getProducts();
    ArrayList<Product> getProducts(int page, int pagQuant);
    ArrayList<Product> getFilterProd(int page, int pagQuant, String type);
    void startClient();
    void processOrder(Order newOrder);
    void updateOrderStatus(Order order, String status);
    ArrayList<Order> getOrders(int customerId);
    void updateStock(Product p, int desiredQuantity);

}
