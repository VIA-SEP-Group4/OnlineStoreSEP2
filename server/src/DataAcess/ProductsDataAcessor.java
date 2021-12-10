package DataAcess;

import Model.Models.Product;
import Utils.Subject;

import java.util.ArrayList;

public interface ProductsDataAcessor extends Subject {
    ArrayList<Product> getProducts();
    ArrayList<Product> getProducts(int page, int pagQuant);
    ArrayList<Product> getFilterProd(int page, int pagQuant, String type);
    void addProduct(Product p);
    void deleteProduct(Product p);
  void updateStock(Product p, int desiredQuantity);
  void editProduct(Product p);

}
