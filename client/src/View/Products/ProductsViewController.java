package View.Products;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductsViewController
{
  @FXML private TableView<TableProdViewModel> prodColumns;
  @FXML private TableColumn<TableProdViewModel, String> nameColumn;
  @FXML private TableColumn<TableProdViewModel, String> typeColumn;
  @FXML private TableColumn<TableProdViewModel, String> priceColumn;
  @FXML private TableColumn<TableProdViewModel, String> quantityColumn;
  @FXML private TableColumn<TableProdViewModel, String> descriptionColumn;

  private ViewHandler viewHandler;
  private ProductsViewModel viewModel;

    public void init(ViewHandler viewHandler)
  {
    this.viewHandler = viewHandler;
    this.viewModel = ViewModelFactory.getProductsViewModel();

    nameColumn.setCellValueFactory(data -> data.getValue().namePropertyProperty());
    typeColumn.setCellValueFactory(data -> data.getValue().typePropertyProperty());
    priceColumn.setCellValueFactory(data -> data.getValue().pricePropertyProperty());
    quantityColumn.setCellValueFactory(data -> data.getValue().quantityPropertyProperty());
    descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());

    prodColumns.setItems(viewModel.getProducts());

    viewModel.getProd();
    reset();

  }

  private void reset()
  {
    viewModel.reset();
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
    TableProdViewModel temp = prodColumns.getSelectionModel().getSelectedItem();
    viewModel.removeProduct(new Product(temp.namePropertyProperty().getValue(),null,0,null,0));
    viewModel.getProducts().remove(temp);
  }

  public void onBackProdButton(ActionEvent actionEvent)
  {
    viewHandler.openBrowserPane();
  }


}
