package Core;

import View.Admin.AdminViewController;
import View.Admin.ManagerDetailViewController;
import View.AccountSettings.AccSettingsViewController;
import View.Browser.ProductDetailController;
import View.Checkout.CheckoutViewController;
import View.Manager.ManagerTabViewController;
import View.Manager.WorkersDetailViewController;
import View.Orders.OrdersViewController;
import View.Manager.AddProductViewController;
import View.Browser.BrowserViewController;
import View.Login.LoginViewController;
import View.Register.RegisterViewController;
import View.ViewController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class ViewHandler {

    private Stage stage;
    private ViewController viewController;
    private static ViewHandler instance;
    private static ReentrantLock lock=new ReentrantLock();

    /**
     * The constructor for the viewHandler, which creates the views
     */
    private ViewHandler() {
    }

    public static ViewHandler getInstance()
    {
        if (instance == null)
        {
            synchronized (lock)
            {
                if (instance == null)
                {
                    instance = new ViewHandler();
                }
            }
        }
        return instance;
    }

    public void initialize(Stage stage){
        this.stage=stage;
    }

    private <T extends Event> void closeWindowEvent(T t)
    {
        if(viewController.beforeExitAction()) {
            System.exit(0);
        }
        else
            t.consume();
    }

    public void start() {
      openBrowserPane();
      stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    public void openLoginPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Login/LoginView.fxml"));
            Parent root = loader.load();
            LoginViewController view = loader.getController();
            view.init();
            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openRegisterPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Register/RegisterView.fxml"));
            Parent root = loader.load();
            RegisterViewController view = loader.getController();
            view.init();
            Scene scene = new Scene(root);
            stage.setTitle("Register");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openBrowserPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Browser/BrowserView.fxml"));
            Parent root = loader.load();
//            BrowserViewController view = loader.getController();
            viewController = loader.getController();
            viewController.init();
            Scene scene = new Scene(root);
            stage.setTitle("Browser");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openManagerPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Manager/ManagerTabView.fxml"));
            Parent root = loader.load();
            ManagerTabViewController view = loader.getController();
            view.init();
            Scene scene = new Scene(root);
            stage.setTitle("Products");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAddProductPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Manager/AddProductView.fxml"));
            Parent root = loader.load();
            AddProductViewController view = loader.getController();
            view.init();
            Scene scene = new Scene(root);
            stage.setTitle("Add Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openProductDetailPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Browser/ProductDetailView.fxml"));
            Parent root = loader.load();
            ProductDetailController view = loader.getController();
            view.init();
            Scene scene = new Scene(root);
            stage.setTitle("Detail Product");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  public void openCheckoutPane()
  {
      try {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("../View/Checkout/checkout.fxml"));
          Parent root = loader.load();
          CheckoutViewController view = loader.getController();
          view.init();
          Scene scene = new Scene(root);
          stage.setTitle("Checkout");
          stage.setScene(scene);
          stage.setResizable(false);
          stage.show();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  public void openOrdersPane(){
      try {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("../View/Orders/OrdersView.fxml"));
          Parent root = loader.load();
          OrdersViewController view = loader.getController();
          view.init();
          Scene scene = new Scene(root);
          stage.setTitle("Orders");
          stage.setScene(scene);
          stage.show();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
    public void openAdminPane(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Admin/AdminView.fxml"));
            Parent root = loader.load();
            AdminViewController view = loader.getController();
            view.init();
            Scene scene = new Scene(root);
            stage.setTitle("Administrator");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openManagerDetailPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Admin/ManagerDetailView.fxml"));
            Parent root = loader.load();
            ManagerDetailViewController view = loader.getController();
            view.init();
            Scene scene = new Scene(root);
            stage.setTitle("Orders");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openWorkersDetailPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Manager/WorkersDetailView.fxml"));
            Parent root = loader.load();
            WorkersDetailViewController view = loader.getController();
            view.init();
            Scene scene = new Scene(root);
            stage.setTitle("Orders");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAccSettingsPane()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/AccountSettings/AccSettingsView.fxml"));
            Parent root = loader.load();
            AccSettingsViewController view = loader.getController();
            view.init();
            Scene scene = new Scene(root);
            stage.setTitle("Account Settings");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
