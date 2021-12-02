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
  private BrowserViewModel productDetailModel;

  public void init() {
    this.viewHandler = ViewHandler.getInstance();
    this.productDetailModel = ViewModelFactory.getBrowserViewModel();

    nameProd.textProperty().bind(productDetailModel.getSelectedProd().namePropertyProperty());
    typeProd.textProperty().bind(productDetailModel.getSelectedProd().typePropertyProperty());
    descriptionProd.textProperty().bind(productDetailModel.getSelectedProd().descriptionProperty());
    priceProd.textProperty().bind(productDetailModel.getSelectedProd().pricePropertyProperty().asString());

  }

  public void onBack(ActionEvent actionEvent)
  {
    viewHandler.openBrowserPane();
  }
}