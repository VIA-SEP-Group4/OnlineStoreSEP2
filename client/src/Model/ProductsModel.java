package Model;

import Utils.Subject;

import java.util.ArrayList;

public interface ProductsModel extends Subject {
    ArrayList<Product> getProducts();
    ArrayList<Product> getBasket();
    void addProduct(Product p);
    void deleteProduct(Product p);
    void addBasket(Product product);
    String getId();
    User getLoggedUser();
  void processOrder(Order newOrder);
}
