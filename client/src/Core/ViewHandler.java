package Core;


import View.Browser.ProductDetailController;
import View.Checkout.CheckoutViewController;
import View.Orders.OrdersViewController;
import View.Products.AddProductViewController;
import View.Products.ProductsViewController;
import View.Browser.BrowserViewController;
import View.Login.LoginViewController;
import View.Register.RegisterViewController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class ViewHandler {
    private Stage stage;
    private ViewModelFactory viewModelFactory;

    /**
     * The constructor for the viewHandler, which creates the views
     * @param stage the stage which will be set in the start method of the application
     * @param viewModelFactory the class that instantiates view models
     */
    public ViewHandler(Stage stage, ViewModelFactory viewModelFactory) {
        this.stage = stage;
        this.viewModelFactory = viewModelFactory;
    }

    private <T extends Event> void closeWindowEvent(T t)
    {
        System.exit(0);
    }

    public void startCustomer() {
//      openBrowserPane();
        openOrdersPane();
      stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }
    public void openLoginPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Login/LoginView.fxml"));
            Parent root = loader.load();
            LoginViewController view = loader.getController();
            view.init(this);
            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.setScene(scene);
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
            view.init(this);
            Scene scene = new Scene(root);
            stage.setTitle("Register");
            stage.setScene(scene);
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
            BrowserViewController view = loader.getController();
            view.init(this);
            Scene scene = new Scene(root);
            stage.setTitle("Browser");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openProductsPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Products/ProductsView.fxml"));
            Parent root = loader.load();
            ProductsViewController view = loader.getController();
            view.init(this);
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
            loader.setLocation(getClass().getResource("../View/Products/AddProductView.fxml"));
            Parent root = loader.load();
            AddProductViewController view = loader.getController();
            view.init(this);
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
            view.init(this);
            Scene scene = new Scene(root);
            stage.setTitle("Detail Product");
            stage.setScene(scene);
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
          view.init(this);
          Scene scene = new Scene(root);
          stage.setTitle("Checkout");
          stage.setScene(scene);
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
          view.init(this);
          Scene scene = new Scene(root);
          stage.setTitle("Orders");
          stage.setScene(scene);
          stage.show();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}
