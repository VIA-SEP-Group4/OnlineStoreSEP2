package View.Manager;

import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.CredentialsModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ManagerTabViewController {
  @FXML
  private ProductsViewController productsViewController;
  @FXML
  private WorkersOverviewController workersOverviewController;
  @FXML
  private OrderOverviewController orderOverviewController;

  private ManagerTabViewModel viewModel;

  public void init(){
    productsViewController.init();
    workersOverviewController.init();
    orderOverviewController.init();

    viewModel = ViewModelFactory.getManagerTabViewModel();
  }

  public void logOut(ActionEvent actionEvent)
  {
//    viewModel.logOut();
    orderOverviewController.deactivateListeners();
    ViewHandler.getInstance().openBrowserPane();
  }
}
