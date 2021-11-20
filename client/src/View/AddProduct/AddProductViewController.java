package View.AddProduct;

import Core.ViewHandler;
import Core.ViewModelFactory;
import View.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddProductViewController implements ViewController
{
  @FXML private TableView<TableProdViewModel> tableColumns;
  @FXML private TableColumn<TableProdViewModel, String> nameColumn;
  @FXML private TableColumn<TableProdViewModel, String> typeColumn;
  @FXML private TableColumn<TableProdViewModel, Integer> priceColumn;
  @FXML private TableColumn<TableProdViewModel, Integer> quantityColumn;
  @FXML private TableColumn<TableProdViewModel, String> descriptionColumn;

  private ViewHandler viewHandler;
  private AddProductViewModel viewModel;

   @Override public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    this.viewHandler = viewHandler;
    this.viewModel = viewModelFactory.getAddProductViewModel();
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePropertyProperty());
    typeColumn.setCellValueFactory(cellData -> cellData.getValue().typePropertyProperty());
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
    descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    tableColumns.setItems(viewModel.getProducts());
    tableColumns.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> viewModel.setSelectedProduct(newValue));

  }


  public void onAddProdButton(ActionEvent actionEvent)
  {
  }

  public void onEditProdButton(ActionEvent actionEvent)
  {
  }

  public void onDeleteProdButton(ActionEvent actionEvent)
  {
  }

  public void onBackProdButton(ActionEvent actionEvent)
  {
  }


}
