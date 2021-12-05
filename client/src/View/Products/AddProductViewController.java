package View.Products;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class AddProductViewController
{
  public TextField productNameField;
  public TextField productTypeField;
  public TextField productPriceField;
  public TextField productQuantityField;
  public TextArea productDescriptionField;
  public Label errorLabel;
  private ViewHandler viewHandler;
  private AddProductViewModel viewModel;

   public void init()
  {
    this.viewHandler = ViewHandler.getInstance();
    this.viewModel = ViewModelFactory.getAddProductViewModel();
    productNameField.textProperty().bindBidirectional(viewModel.prodNameProperty());
    productTypeField.textProperty().bindBidirectional(viewModel.prodTypeProperty());
    productPriceField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue!=null &&!newValue.matches("\\d*") ) {
        productPriceField.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });
    productQuantityField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue!=null &&!newValue.matches("\\d*") ) {
        productQuantityField.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });
    productPriceField.textProperty().bindBidirectional(viewModel.prodPriceProperty(), new NumberStringConverter());
    productPriceField.textProperty().setValue("");
    productQuantityField.textProperty().bindBidirectional(viewModel.prodQuantityProperty(), new NumberStringConverter());
    productQuantityField.textProperty().setValue("");
    productDescriptionField.textProperty().bindBidirectional(viewModel.prodDescriptionProperty());
    errorLabel.textProperty().bind(viewModel.getError());
  }
  public void addButton(ActionEvent actionEvent)
  {
    try
    {
      if (viewModel.addProduct())
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
