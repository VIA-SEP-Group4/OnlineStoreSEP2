package View.Products;

import Model.Product;
import javafx.beans.property.*;

public class TableProdViewModel
{
  private StringProperty nameProperty;
  private StringProperty typeProperty;
  private StringProperty priceProperty;
  private StringProperty quantityProperty;
  private StringProperty descriptionProperty;

  public TableProdViewModel(Product product)
  {
    nameProperty = new SimpleStringProperty(product.getName());
    typeProperty = new SimpleStringProperty(product.getType());
    priceProperty = new SimpleStringProperty(product.getPrice()+"");
    quantityProperty = new SimpleStringProperty(product.getQuantityP()+"");
    descriptionProperty = new SimpleStringProperty(product.getDescription());
  }


  public StringProperty namePropertyProperty()
  {
    return nameProperty;
  }

  public StringProperty typePropertyProperty()
  {
    return typeProperty;
  }

  public StringProperty pricePropertyProperty()
  {
    return priceProperty;
  }

  public StringProperty quantityPropertyProperty()
  {
    return quantityProperty;
  }

  public StringProperty descriptionProperty()
  {
    return descriptionProperty;
  }
}
