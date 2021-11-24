package View.Browser;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BrowserViewController
{
  public TextField searchTextField;
  public Button addButton;
  public Label itemsLabel;
  public Pagination pagination;
  public ComboBox <String> filterByComboBox;
  public Button loginButton;
  public Button registerButton;
  public Label idLabel;
  public Label nameProdLabel;
  public Label typeLabel;
  public Label descriptionLabel;
  public Label priceLabel;
  public Label quantityLabel;
  public GridPane gridPaneBrowser;
  private ViewHandler viewHandler;
  private BrowserViewModel viewModel;

   public void init(ViewHandler viewHandler) {
    this.viewHandler = viewHandler;
    this.viewModel = ViewModelFactory.getBrowserViewModel();
    searchTextField.textProperty().bindBidirectional(viewModel.searchProperty());
    itemsLabel.textProperty().bindBidirectional(viewModel.itemsProperty());
    pagination.currentPageIndexProperty().bindBidirectional(viewModel.pageProperty());
    filterByComboBox.getItems().addAll("All products", "Type", "Availability");
    //filterByComboBox.valueProperty().bindBidirectional(viewModel.filterProperty());
    nameProdLabel.textProperty().bindBidirectional(viewModel.nameProdProperty());
    typeLabel.textProperty().bindBidirectional(viewModel.typeProperty());
    descriptionLabel.textProperty().bindBidirectional(viewModel.prodDescriptionProperty());
    priceLabel.textProperty().bindBidirectional(viewModel.priceProperty());
    quantityLabel.textProperty().bindBidirectional(viewModel.quantityProperty());
    loginButton.visibleProperty().bind(viewModel.hideButtonsProperty());
    registerButton.visibleProperty().bind(viewModel.hideButtonsProperty());
    idLabel.textProperty().bind(viewModel.idPropertyProperty());
    viewModel.getProd();
    reset();
  }

   private void reset()
  {
    viewModel.reset();
  }

  public void onLoginButtonBrowser(ActionEvent actionEvent)
  {
    viewHandler.openLoginPane();
  }

  public void onRegisterButtonBrowser(ActionEvent actionEvent)
  {
    viewHandler.openRegisterPane();
  }

  public void onSearchTextFieldEnter(KeyEvent keyEvent)
  {
  }

  public void onClickSearchLabel(MouseEvent mouseEvent)
  {
  }

  public void onAddButton(ActionEvent actionEvent)
  {
    Button button = (Button) actionEvent.getSource();
    String idString = button.idProperty().get().substring(9);
    int id = Integer.parseInt(idString);
    viewModel.addBasket(id);
  }

  public void onBasketButton(ActionEvent actionEvent)
  {
  }

  public void onFilterBy(ActionEvent actionEvent)
  {
  }

  public void onPageClick(MouseEvent mouseEvent)
  {
    viewModel.getProd();
  }
}
