package Core;


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

    public void start() {
        openLoginPane();
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void openLoginPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/Login/LoginView.fxml"));
            Parent root = loader.load();
            LoginViewController view = loader.getController();
            view.init(this, viewModelFactory);
            Scene scene = new Scene(root);
            stage.setTitle("First");
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
            view.init(this, viewModelFactory);
            Scene scene = new Scene(root);
            stage.setTitle("First");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
