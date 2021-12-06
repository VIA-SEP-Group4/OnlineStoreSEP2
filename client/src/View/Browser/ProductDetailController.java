package View.Browser;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class ProductDetailController
{
  public Label nameProd;
  public Label typeProd;
  public Label descriptionProd;
  public Label priceProd;

  private ViewHandler viewHandler;
  private BrowserViewModel browserViewModel;

  public void init() {
    this.viewHandler = ViewHandler.getInstance();
    this.browserViewModel = ViewModelFactory.getBrowserViewModel();

    nameProd.textProperty().setValue(browserViewModel.getSelectedProd().getName());
    typeProd.textProperty().setValue(browserViewModel.getSelectedProd().getType());
    descriptionProd.textProperty().setValue(browserViewModel.getSelectedProd().getDescription());
    priceProd.textProperty().setValue(
        String.valueOf(browserViewModel.getSelectedProd().getPrice()));

  }

  public void onBack(ActionEvent actionEvent)
  {
    viewHandler.openBrowserPane();
  }
}