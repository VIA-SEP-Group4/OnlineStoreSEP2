package Model;

import Utils.Subject;

import java.util.ArrayList;

public interface ProductsModel extends Subject {
    ArrayList<Product> getProducts();
    ArrayList<Product> getCartProducts();
    void addProduct(Product p);
    void deleteProduct(Product p);
    void addBasket(Product product);

  void processOrder(Order newOrder);
  ArrayList<Order> fetchOrders();
  void addToCart(Product p, int desiredQuantity);
}
