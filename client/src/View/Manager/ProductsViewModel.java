package View.Manager;

import Model.Models.Order;
import Model.Models.Product;

import Model.ProductsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ProductsViewModel implements PropertyChangeListener
{
  private ObservableList<Product> products;
 
  private ProductsModel productsModel;

  public ProductsViewModel(ProductsModel productsModel)
  {
    this.productsModel = productsModel;
    this.products = FXCollections.observableArrayList();
    products.setAll(productsModel.getAllProducts());
    productsModel.addListener("ProductsReply",this);
  }

  public ObservableList<Product> getProducts()
  {
    return products;
  }

  public void removeProduct(Product p){
    productsModel.deleteProduct(p);
    products.remove(p);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Product product= (Product) evt.getNewValue();
    boolean found=false;
    for(int i=0;i<products.size();i++){
      if(products.get(i).getProductId()== product.getProductId()){
        products.set(i,product);
        found=true;
        break;
      }
    }
    if(!found){
      products.add(product);
    }
  }

  public void reset()
  {
    products.clear();
  }
}
