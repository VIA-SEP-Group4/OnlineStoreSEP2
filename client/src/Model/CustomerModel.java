package Model;

import Model.Models.Customer;
import Model.Models.Order;
import Model.Models.Product;
import Utils.Subject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import java.util.ArrayList;

public interface CustomerModel  extends Subject {
    void processOrder(Order o);
    void cancelOrder(Order order, String canceled);
    void addToCart(Product p, int desiredQuantity);
    void removeFromCart(Product p, int desiredQuantity);

    ArrayList<Order> fetchCustomerOrders();
    ArrayList<Product> getCartProducts();
    ArrayList<Product> getProducts();
    ArrayList<Product> getProducts(int page, int pagQuant);
    ArrayList<Product> getFilterProd(int page, int pagQuant, String type);

    Customer getLoggedCustomer();
}
