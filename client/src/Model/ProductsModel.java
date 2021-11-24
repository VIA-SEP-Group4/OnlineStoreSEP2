package Model;

import Utils.Subject;

import java.util.ArrayList;

public interface ProductsModel extends Subject {
    ArrayList<Product> getProducts(int index);
    ArrayList<Product> getBasket();
    void addProduct(Product p);
    void addBasket(Product product);
    String getId();
}
