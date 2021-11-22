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
  private StringProperty prodDescription1;
  private StringProperty prodDescription2;
  private StringProperty prodDescription3;
  private StringProperty prodDescription4;
  private StringProperty prodDescription5;
  private StringProperty prodDescription6;
  private StringProperty items;
  private IntegerProperty page;
  private BooleanProperty button1;
  private BooleanProperty button2;
  private BooleanProperty button3;
  private BooleanProperty button4;
  private BooleanProperty button5;
  private BooleanProperty button6;
  private ObjectProperty<String> filter;
  private BooleanProperty hideButtons;
  private StringProperty idProperty;

  public BrowserViewModel(LoginModel model)
  {
    this.model = model;
    search = new SimpleStringProperty();
    prodDescription1 = new SimpleStringProperty();
    prodDescription2 = new SimpleStringProperty();
    prodDescription3 = new SimpleStringProperty();
    prodDescription4 = new SimpleStringProperty();
    prodDescription5 = new SimpleStringProperty();
    prodDescription6 = new SimpleStringProperty();
    button1 = new SimpleBooleanProperty(false);
    button2 = new SimpleBooleanProperty(false);
    button3 = new SimpleBooleanProperty(false);
    button4 = new SimpleBooleanProperty(false);
    button5 = new SimpleBooleanProperty(false);
    button6 = new SimpleBooleanProperty(false);
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

  public StringProperty prodDescription1Property()
  {
    return prodDescription1;
  }

  public StringProperty prodDescription2Property()
  {
    return prodDescription2;
  }

  public StringProperty prodDescription3Property()
  {
    return prodDescription3;
  }

  public StringProperty prodDescription4Property()
  {
    return prodDescription4;
  }

  public StringProperty prodDescription5Property()
  {
    return prodDescription5;
  }

  public StringProperty prodDescription6Property()
  {
    return prodDescription6;
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

  public BooleanProperty button1Property()
  {
    return button1;
  }

  public BooleanProperty button2Property()
  {
    return button2;
  }

  public BooleanProperty button3Property()
  {
    return button3;
  }

  public BooleanProperty button4Property()
  {
    return button4;
  }

  public BooleanProperty button5Property()
  {
    return button5;
  }

  public BooleanProperty button6Property()
  {
    return button6;
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
    if (prod != null)
    {
      if (prod.size() == 0)
      {
        pageProperty().setValue(1);
        getProd();
      }
      this.prodDescription1.setValue(prod.get(0).toString());
      if (prod.get(1) != null)
      {
        button2.setValue(false);
        this.prodDescription2.setValue(prod.get(1).toString());
      }
      else
      {
        prodDescription2.setValue("");
        button2.setValue(true);
      }
      if (prod.get(1) != null)
      {
        button3.setValue(false);
        this.prodDescription3.setValue(prod.get(2).toString());
      }
      else
      {
        prodDescription3.setValue("");
        button3.setValue(true);
      }
      if (prod.get(1) != null)
      {
        button4.setValue(false);
        this.prodDescription4.setValue(prod.get(3).toString());
      }
      else
      {
        prodDescription4.setValue("");
        button4.setValue(true);
      }
      if (prod.get(1) != null)
      {
        button5.setValue(false);
        this.prodDescription5.setValue(prod.get(4).toString());
      }

      else
      {
        prodDescription5.setValue("");
        button5.setValue(true);
      }
      if (prod.get(1) != null)
      {
        button6.setValue(false);
        this.prodDescription6.setValue(prod.get(5).toString());
      }
      else
      {
        prodDescription6.setValue("");
        button6.setValue(true);
      }
    }
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
