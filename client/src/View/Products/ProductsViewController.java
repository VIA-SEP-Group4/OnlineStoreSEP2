package View.Products;

import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductsViewController
{
  @FXML private TableView<TableProdViewModel> tableColumns;
  @FXML private TableColumn<TableProdViewModel, String> nameColumn;
  @FXML private TableColumn<TableProdViewModel, String> typeColumn;
  @FXML private TableColumn<TableProdViewModel, Integer> priceColumn;
  @FXML private TableColumn<TableProdViewModel, Integer> quantityColumn;
  @FXML private TableColumn<TableProdViewModel, String> descriptionColumn;

  private ViewHandler viewHandler;
  private ProductsViewModel viewModel;

    public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
    this.viewModel = ViewModelFactory.getProductsViewModel();
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
    viewHandler.openAddProductPane();
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
