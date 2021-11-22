package View.Browser;

import Model.LoginModel;
import Model.Product;
import javafx.beans.property.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class BrowserViewModel implements PropertyChangeListener
{
  private LoginModel model;
  private StringProperty search;
  private StringProperty nameProd;
  private StringProperty type;
  private StringProperty prodDescription;
  private StringProperty price;
  private StringProperty quantity;
  private StringProperty items;
  private IntegerProperty page;
  private ObjectProperty<String> filter;
  private BooleanProperty hideButtons;
  private StringProperty idProperty;

  public BrowserViewModel(LoginModel model)
  {
    this.model = model;
    search = new SimpleStringProperty();
    nameProd = new SimpleStringProperty();
    type = new SimpleStringProperty();
    prodDescription = new SimpleStringProperty();
    price = new SimpleStringProperty();
    quantity = new SimpleStringProperty();
    items = new SimpleStringProperty("");
    page = new SimpleIntegerProperty();
    hideButtons = new SimpleBooleanProperty(true);
    idProperty = new SimpleStringProperty();
    model.addListener("BrowserReply",this);
  }

  public IntegerProperty pageProperty()
  {
    return page;
  }

  public StringProperty searchProperty()
  {
    return search;
  }

  public StringProperty nameProdProperty()
  {
    return nameProd;
  }

  public StringProperty typeProperty()
  {
    return type;
  }

  public StringProperty prodDescriptionProperty()
  {
    return prodDescription;
  }

  public StringProperty priceProperty()
  {
    return price;
  }

  public StringProperty quantityProperty()
  {
    return quantity;
  }

  public StringProperty itemsProperty()
  {
    return items;
  }

  public ObjectProperty<String> filterProperty()
    {
    return filter;
    }

  public LoginModel getModel()
  {
    return model;
  }

  public BooleanProperty hideButtonsProperty()
  {
    return hideButtons;
  }

  public StringProperty idPropertyProperty()
  {
    return idProperty;
  }

  public void getProd(){
    ArrayList<Product> prod = model.getProducts(pageProperty().get());
    }

  public void addBasket(int id)
  {
    model.addBasket(model.getProducts(page.getValue()).get(id-1));
    items.setValue("("+model.getBasket().size()+") items");
  }

  public void reset()
  {
    if(model.getId().equals(""))
      hideButtons.setValue(true);
    else {
      hideButtons.setValue(false);
      idProperty.setValue(model.getId());
    }
  }
  @Override public void propertyChange(PropertyChangeEvent evt)
  {

  }
}
