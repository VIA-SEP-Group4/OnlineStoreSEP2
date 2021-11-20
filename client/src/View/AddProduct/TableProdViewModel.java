package View.AddProduct;

import Model.Product;
import javafx.beans.property.*;

public class TableProdViewModel
{
  private StringProperty nameProperty;
  private StringProperty typeProperty;
  private DoubleProperty priceProperty;
  private IntegerProperty quantityProperty;
  private StringProperty descriptionProperty;

  public TableProdViewModel(Product product)
  {
    nameProperty = new SimpleStringProperty(product.getName());
    typeProperty = new SimpleStringProperty(product.getType());
    priceProperty = new SimpleDoubleProperty(product.getPrice());
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

  public DoubleProperty pricePropertyProperty()
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
