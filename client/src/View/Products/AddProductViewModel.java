package View.Products;

import Model.LoginModel;
import Model.LoginModelManager;
import Model.Product;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddProductViewModel implements PropertyChangeListener
{
  private LoginModel loginModel;
  private StringProperty prodName;
  private StringProperty prodType;
  private SimpleIntegerProperty prodPrice;
  private SimpleIntegerProperty prodQuantity;
  private StringProperty prodDescription;
  private StringProperty errorLabel;

  public AddProductViewModel(LoginModel loginModel)
  {
    this.loginModel = loginModel;
    prodName = new SimpleStringProperty();
    prodType = new SimpleStringProperty();
    prodPrice = new SimpleIntegerProperty();
    prodQuantity = new SimpleIntegerProperty();
    prodDescription = new SimpleStringProperty();
    errorLabel = new SimpleStringProperty();

    loginModel.addListener("AddProductReply",this);
  }

  public LoginModel getLoginModel()
  {
    return loginModel;
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
      loginModel.addProduct(product);
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
