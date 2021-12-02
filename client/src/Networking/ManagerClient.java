package Networking;

import Model.Product;
import Utils.Subject;

public interface ManagerClient extends Subject {
    void addProduct(Product p);
    void deleteProduct(Product p);
    void startClient();
}
