package View.Browser;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class BrowserViewController
{
  public ComboBox<String> filterByComboBox;
  public TextField searchTextField;
  public Button loginButton;
  public Label itemsLabel;

  public Label userLabel;
  public Button basketButton;
  public Button addButton;

  public TableView<Product> browserTable;
  public TableColumn<Product, String> nameColumn;
  public TableColumn<Product, String> typeColumn;
  public TableColumn<Product, Double> priceColumn;
  public TableColumn<Product, String> descriptionColumn;
  public TableColumn<Product, Integer> quantityColumn;

  private ViewHandler viewHandler;
  private BrowserViewModel viewModel;

  public void init()
  {
    this.viewHandler = ViewHandler.getInstance();
    this.viewModel = ViewModelFactory.getBrowserViewModel();

    filterByComboBox.getItems().addAll("All products", "Type", "Availability");
    searchTextField.textProperty().bindBidirectional(viewModel.searchProperty());
    itemsLabel.textProperty().bindBidirectional(viewModel.itemsProperty());

    //logged In/Out dependents
    itemsLabel.visibleProperty().bind(viewModel.logInProperty());
    basketButton.visibleProperty().bind(viewModel.logInProperty());
    userLabel.visibleProperty().bind(viewModel.logInProperty());
    addButton.visibleProperty().bind(viewModel.logInProperty());
    loginButton.visibleProperty().bind(viewModel.logOutProperty());


    //table
    browserTable.setItems(viewModel.getBrowserTable());
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    //    viewModel.fetchProducts();
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

  public void onSearchTextFieldEnter(KeyEvent keyEvent)
  {
  }

  public void onClickSearchLabel(MouseEvent mouseEvent)
  {
  }

  public void onBasketButton(ActionEvent actionEvent)
  {
//    viewHandler.openProductsPane();
    viewHandler.openCheckoutPane();
  }

  public void onFilterBy(ActionEvent actionEvent)
  {
  }

  public void addButton(ActionEvent actionEvent)
  {
    viewModel.addBasket();
  }

  public void onDoubleClick(MouseEvent mouseEvent)
  {
    if (mouseEvent.getClickCount() == 2 && !mouseEvent.isConsumed())
    {
      mouseEvent.consume();
      viewHandler.openProductDetailPane();
    }
  }
}
