package Model;

import DataAcess.ProductsDataAcessor;
import DataAcess.ProductsDataManager;
import Model.Models.Product;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ProductsModelManager implements ProductsModel, PropertyChangeListener
{

  private ProductsDataAcessor productsDataAcessor;
  private PropertyChangeSupport support;

  public ProductsModelManager()
  {
    productsDataAcessor=new ProductsDataManager();
    support=new PropertyChangeSupport(this);

    productsDataAcessor.addListener("ProductReply",this);
  }


  @Override public ArrayList<Product> getProducts() {
    return productsDataAcessor.getProducts();
  }

  @Override public void addProduct(Product p) {
    productsDataAcessor.addProduct(p);
  }

  @Override public void deleteProduct(Product p)
  {
    productsDataAcessor.deleteProduct(p);
  }

  @Override public void updateStock(Product p, int desiredQuantity)
  {
    productsDataAcessor.updateStock(p, desiredQuantity);
  }

  @Override
  public void editProduct(Product p) {
    productsDataAcessor.editProduct(p);
  }


  @Override public void addListener(String eventName, PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName,listener);
  }

  @Override public void removeListener(String eventName, PropertyChangeListener listener) {
    support.removePropertyChangeListener(eventName,listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    support.firePropertyChange(evt);
  }
}
