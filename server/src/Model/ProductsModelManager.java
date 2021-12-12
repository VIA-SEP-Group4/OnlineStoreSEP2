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

    productsDataAcessor.addListener("ProductsReply",this);
    productsDataAcessor.addListener("productDeleted",this);
  }


  @Override public ArrayList<Product> getProducts() {
    return productsDataAcessor.getProducts();
  }

  @Override public ArrayList<Product> getProducts(int page, int pagQuant)
  {
    return productsDataAcessor.getProducts(page,pagQuant);
  }

  @Override public ArrayList<Product> getFilterProd(int page, int pagQuant, String type)
  {
    return productsDataAcessor.getFilterProd(page,pagQuant,type);
  }

  @Override public void addProduct(Product p) {
    productsDataAcessor.addProduct(p);
  }

  @Override public void deleteProduct(Product p)
  {
    productsDataAcessor.deleteProduct(p);
  }

  @Override public void updateStock(Product p, int prodQuantity)
  {
    productsDataAcessor.updateStock(p, prodQuantity);
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
