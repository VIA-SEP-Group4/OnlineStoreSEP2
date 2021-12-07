package View.Manager;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.Models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductsViewController
{

  @FXML private TableView<Product> prodColumns;
  @FXML private TableColumn<Product, String> nameColumn;
  @FXML private TableColumn<Product, String> typeColumn;
  @FXML private TableColumn<Product, Number> priceColumn;
  @FXML private TableColumn<Product, Number> quantityColumn;
  @FXML private TableColumn<Product, String> descriptionColumn;
  @FXML private TableColumn<Product, Integer> prodIDCol;
  private ViewHandler viewHandler;
  private ProductsViewModel viewModel;
  private AddProductViewModel addViewModel;
    public void init()
  {
    this.viewHandler = ViewHandler.getInstance();
    this.viewModel = ViewModelFactory.getProductsViewModel();
    this.addViewModel=ViewModelFactory.getAddProductViewModel();
    prodColumns.setItems(viewModel.getProducts());
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    prodIDCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
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
    Product p=prodColumns.getSelectionModel().getSelectedItem();
    addViewModel.editProduct(p);
    viewHandler.openAddProductPane();
  }

  public void onDeleteProdButton(ActionEvent actionEvent)
  {
    Product temp = prodColumns.getSelectionModel().getSelectedItem();
    viewModel.removeProduct(new Product(temp.getName(),null,0,null,0));
    viewModel.getProducts().remove(temp);
  }

  public void onBackProdButton(ActionEvent actionEvent)
  {
    viewHandler.openBrowserPane();
  }


}
