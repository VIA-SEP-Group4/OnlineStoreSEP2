package View.AddProduct;

import Model.Product;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableProdViewModel
{
  private StringProperty nameProperty;
  private StringProperty typeProperty;
  private IntegerProperty priceProperty;
  private IntegerProperty quantityProperty;
  private StringProperty descriptionProperty;

  public TableProdViewModel(Product product)
  {
    nameProperty = new SimpleStringProperty(product.getName());
    typeProperty = new SimpleStringProperty(product.getType());
    priceProperty = new SimpleIntegerProperty(product.getPrice());
    quantityProperty = new SimpleIntegerProperty(product.getQuantityP());
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

  public IntegerProperty pricePropertyProperty()
  {
    return priceProperty;
  }

  public IntegerProperty quantityPropertyProperty()
  {
    return quantityProperty;
  }

  public StringProperty descriptionProperty()
  {
    return descriptionProperty;
  }
}
