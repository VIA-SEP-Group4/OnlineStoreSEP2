package View.Browser;

import Core.ViewHandler;
import Core.ViewModelFactory;
import View.ViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class BrowserViewController implements ViewController
{
  public TextField searchTextField;
  public Button addButton1;
  public Button addButton2;
  public Button addButton3;
  public Button addButton4;
  public Button addButton5;
  public Button addButton6;
  public Label productDescriptionText1;
  public Label productDescriptionText2;
  public Label productDescriptionText3;
  public Label productDescriptionText4;
  public Label productDescriptionText5;
  public Label productDescriptionText6;
  public Label itemsLabel;
  public Pagination pagination;
  public ComboBox <String> filterByComboBox;
  public Button loginButton;
  public Button registerButton;
  public Label idLabel;
  private ViewHandler viewHandler;
  private BrowserViewModel viewModel;

  @Override public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
    this.viewHandler = viewHandler;
    this.viewModel = viewModelFactory.getBrowserViewModel();
    searchTextField.textProperty().bindBidirectional(viewModel.searchProperty());
    productDescriptionText1.textProperty().bindBidirectional(viewModel.prodDescription1Property());
    productDescriptionText2.textProperty().bindBidirectional(viewModel.prodDescription2Property());
    productDescriptionText3.textProperty().bindBidirectional(viewModel.prodDescription3Property());
    productDescriptionText4.textProperty().bindBidirectional(viewModel.prodDescription4Property());
    productDescriptionText5.textProperty().bindBidirectional(viewModel.prodDescription5Property());
    productDescriptionText6.textProperty().bindBidirectional(viewModel.prodDescription6Property());
    itemsLabel.textProperty().bindBidirectional(viewModel.itemsProperty());
    pagination.currentPageIndexProperty().bindBidirectional(viewModel.pageProperty());
    filterByComboBox.getItems().addAll("All products", "Type", "Availability");
    //filterByComboBox.valueProperty().bindBidirectional(viewModel.filterProperty());
    viewModel.getProd();
    viewModel.button1Property().addListener((obs, oldVal, newVal) -> addButton1.setDisable(newVal));
    viewModel.button2Property().addListener((obs, oldVal, newVal) -> addButton2.setDisable(newVal));
    viewModel.button3Property().addListener((obs, oldVal, newVal) -> addButton3.setDisable(newVal));
    viewModel.button4Property().addListener((obs, oldVal, newVal) -> addButton4.setDisable(newVal));
    viewModel.button5Property().addListener((obs, oldVal, newVal) -> addButton5.setDisable(newVal));
    viewModel.button6Property().addListener((obs, oldVal, newVal) -> addButton6.setDisable(newVal));
    loginButton.visibleProperty().bind(viewModel.hideButtonsProperty());
    registerButton.visibleProperty().bind(viewModel.hideButtonsProperty());
    idLabel.textProperty().bind(viewModel.idPropertyProperty());
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
