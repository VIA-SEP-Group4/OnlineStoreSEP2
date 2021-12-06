package View.Manager;

import Model.ManagerModel;
import Model.Models.Product;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddProductViewModel implements PropertyChangeListener
{
  private ManagerModel managerModel;
  private StringProperty prodName;
  private StringProperty prodType;
  private SimpleIntegerProperty prodPrice;
  private SimpleIntegerProperty prodQuantity;
  private StringProperty prodDescription;
  private StringProperty errorLabel;

  public AddProductViewModel(ManagerModel managerModel)
  {
    this.managerModel = managerModel;
    prodName = new SimpleStringProperty();
    prodType = new SimpleStringProperty();
    prodPrice = new SimpleIntegerProperty();
    prodQuantity = new SimpleIntegerProperty();
    prodDescription = new SimpleStringProperty();
    errorLabel = new SimpleStringProperty();

    managerModel.addListener("ProductsReply",this);
  }



  public boolean addProduct() {
    if(prodName.getValue()!=null && !prodName.getValue().equals("")
        && prodType.getValue()!=null && !prodType.getValue().equals("")
        && prodPrice.getValue()!=null && prodQuantity.getValue()!=null
        && prodDescription.getValue()!=null && !prodDescription.getValue().equals(""))
    {
      Product product = new Product(prodName.getValue(), prodType.getValue(),prodPrice.getValue(),prodDescription.getValue(),prodQuantity.getValue());
      managerModel.addProduct(product);
      return true;
    }
    else
    {
      Platform.runLater(()->{errorLabel.setValue("Fields cannot be empty");});
      return false;
    }

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

  public StringProperty getError(){
    return errorLabel;
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
