package View.Products;

import Model.CredentialsModel;
import Model.Product;
import Model.ProductsModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddProductViewModel implements PropertyChangeListener
{
  private ProductsModel productsModel;
  private StringProperty prodName;
  private StringProperty prodType;
  private SimpleIntegerProperty prodPrice;
  private SimpleIntegerProperty prodQuantity;
  private StringProperty prodDescription;
  private StringProperty errorLabel;

  public AddProductViewModel(ProductsModel credentialsModel)
  {
    this.productsModel = credentialsModel;
    prodName = new SimpleStringProperty();
    prodType = new SimpleStringProperty();
    prodPrice = new SimpleIntegerProperty();
    prodQuantity = new SimpleIntegerProperty();
    prodDescription = new SimpleStringProperty();
    errorLabel = new SimpleStringProperty();

    credentialsModel.addListener("AddProductReply",this);
  }

  public StringProperty prodNameProperty()
  {
    return prodName;
  }

  public StringProperty prodTypeProperty()
  {
    return prodType;
  }

  public SimpleIntegerProperty prodPriceProperty()
  {
    return prodPrice;
  }

  public SimpleIntegerProperty prodQuantityProperty()
  {
    return prodQuantity;
  }

  public StringProperty prodDescriptionProperty()
  {
    return prodDescription;
  }

  public void addProduct() {
    if(prodName.getValue()!=null && !prodName.getValue().equals("")
        && prodType.getValue()!=null && !prodType.getValue().equals("")
        && prodPrice.getValue()!=null && prodQuantity.getValue()!=null
        && prodDescription.getValue()!=null && !prodDescription.getValue().equals(""))
    {
      Product product = new Product(prodName.getValue(), prodType.getValue(),prodPrice.getValue(),prodDescription.getValue(),prodQuantity.getValue());
      productsModel.addProduct(product);
    }
    else
    {
      errorLabel.setValue("Fields cannot be empty");
    }

  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
