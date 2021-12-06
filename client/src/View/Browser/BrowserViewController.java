package View.Browser;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.Models.Product;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.ArrayList;

public class BrowserViewController
{
  public ComboBox<String> filterByComboBox;
  public TextField searchTextField;
  public Button loginButton;

  public Label userLabel;
  public Button basketButton;

  public TableView<Product> browserTable;
  public TableColumn<Product, String> nameColumn;
  public TableColumn<Product, String> typeColumn;
  public TableColumn<Product, Double> priceColumn;
  public TableColumn<Product, String> descriptionColumn;
  public TableColumn<Product, Integer> quantityColumn;
  public TableColumn<Product, Void> addBtnCol;
  public TableColumn<Product, Void> desiredQuantityCol;

  private ViewHandler viewHandler;
  private BrowserViewModel viewModel;

  private ArrayList<Button> buttons = new ArrayList<>();
  private ArrayList<Spinner<Integer>> spinners = new ArrayList<>();

  public void init()
  {
    this.viewHandler = ViewHandler.getInstance();
    this.viewModel = ViewModelFactory.getBrowserViewModel();

    filterByComboBox.getItems().addAll("All products", "Type", "Availability");
    searchTextField.textProperty().bindBidirectional(viewModel.searchProperty());

    //logged In/Out dependents
    basketButton.visibleProperty().bind(viewModel.logInProperty());
    userLabel.visibleProperty().bind(viewModel.logInProperty());
    loginButton.visibleProperty().bind(viewModel.logOutProperty());

    //table
    browserTable.setItems(viewModel.getBrowserTable());
    browserTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> viewModel.setSelectedProd(newValue));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    desiredQuantityCol.setCellFactory(addSpinnerToTable());
    desiredQuantityCol.visibleProperty().bind(viewModel.logInProperty());
    addBtnCol.setCellFactory(addButtonToTable());
    addBtnCol.visibleProperty().bind(viewModel.logInProperty());

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
    viewHandler.openCheckoutPane();
  }

  public void onFilterBy(ActionEvent actionEvent)
  {
  }

  public void onDoubleClick(MouseEvent mouseEvent)
  {
    if (mouseEvent.getClickCount() == 2 && !mouseEvent.isConsumed())
    {
      mouseEvent.consume();

      viewHandler.openProductDetailPane();
    }
  }



  private Callback<TableColumn<Product, Void>, TableCell<Product, Void>> addButtonToTable() {

    Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
      @Override public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
        final TableCell<Product, Void> cell = new TableCell<>() {

          private final Button btn = new Button("Add");
          {
            btn.setOnAction((ActionEvent event) -> {
              Product tempProduct = getTableView().getItems().get(getIndex());
              int desiredQuantity = spinners.get(buttons.indexOf(btn)).getValue();

              viewModel.addToCart(tempProduct, desiredQuantity);
            });
          }

          @Override public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              buttons.add(btn);
              setGraphic(btn);
            }
          }
        };
        return cell;
      }
    };

    return cellFactory;
  }

  private Callback<TableColumn<Product, Void>, TableCell<Product, Void>> addSpinnerToTable() {

    Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<>() {
      @Override public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
        final TableCell<Product, Void> cell = new TableCell<>() {

          private Spinner<Integer> spinner = new Spinner<>(0,10,0);

          @Override public void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              spinners.add(spinner);
              setGraphic(spinner);
            }
          }
        };
        return cell;
      }
    };

    return cellFactory;
  }
}
