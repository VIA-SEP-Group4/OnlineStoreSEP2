package View.Products;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddProductViewController
{
  public TextField productNameField;
  public TextField productTypeField;
  public TextField productPriceField;
  public TextField productQuantityField;
  public TextArea productDescriptionField;
  public Label errorLabel;
  private ViewHandler viewHandler;
  private AddProductViewModel addProductViewModel;

   public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
    this.addProductViewModel = ViewModelFactory.getAddProductViewModel();
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
