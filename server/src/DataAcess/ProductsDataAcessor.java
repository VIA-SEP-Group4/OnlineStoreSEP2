package DataAcess;

import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface ProductsDataAcessor extends Subject {
    ArrayList<Product> getProducts();
    void addProduct(Product p);
    void deleteProduct(Product p);
  void updateStock(Product p, int desiredQuantity);
}
