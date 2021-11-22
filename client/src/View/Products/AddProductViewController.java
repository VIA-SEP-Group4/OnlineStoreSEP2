package View.Products;

import Core.ViewHandler;
import Core.ViewModelFactory;
import View.ViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddProductViewController implements ViewController
{
  public TextField productNameField;
  public TextField productTypeField;
  public TextField productPriceField;
  public TextField productQuantityField;
  public TextArea productDescriptionField;
  public Label errorLabel;
  private ViewHandler viewHandler;
  private AddProductViewModel addProductViewModel;





  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory)
  {
    this.viewHandler = viewHandler;
    this.addProductViewModel = viewModelFactory.getAddProductViewModel();
  }
  public void addButton(ActionEvent actionEvent)
  {
    try
    {
      addProductViewModel.addProduct();
      viewHandler.openProductsPane();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void cancelButton(ActionEvent actionEvent)
  {
    viewHandler.openProductsPane();
  }
}
