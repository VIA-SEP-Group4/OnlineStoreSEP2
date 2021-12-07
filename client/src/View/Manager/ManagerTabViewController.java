package View.Manager;

import javafx.fxml.FXML;

public class ManagerTabViewController {
    @FXML
    private ProductsViewController productsViewController;
    @FXML
    private WorkersOverviewController workersOverviewController;
    @FXML
    private OrderOverviewController orderOverviewController;
    public void init(){
        productsViewController.init();
        workersOverviewController.init();
        orderOverviewController.init();
    }
}
