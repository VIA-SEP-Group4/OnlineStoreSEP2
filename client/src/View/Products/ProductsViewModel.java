package View.Products;

import Model.CredentialsModel;
import Model.ProductsModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProductsViewModel implements PropertyChangeListener
{
  private ObservableList<TableProdViewModel> products;
  private ObjectProperty<TableProdViewModel> selectedProduct;
  private ProductsModel model;

  public ProductsViewModel(ProductsModel model)
  {
    this.model = model;
    this.products = FXCollections.observableArrayList();
    this.selectedProduct = new SimpleObjectProperty<>();
    model.addListener("AddProductReply",this);
  }

  public ObservableList<TableProdViewModel> getProducts()
  {
    return products;
  }

  public void setSelectedProduct(TableProdViewModel selectedUser)
  {
    this.selectedProduct.set(selectedUser);
  }
  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
