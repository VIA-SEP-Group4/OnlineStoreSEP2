package Core;

import View.FirstViewController;
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

    public ViewHandler(Stage stage, ViewModelFactory viewModelFactory) {
        this.stage = stage;
        this.viewModelFactory = viewModelFactory;
    }

    private <T extends Event> void closeWindowEvent(T t)
    {
        System.exit(0);
    }

    public void start() {
        openPane();

        //on window-close_request action...
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void openPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../View/FirstView.fxml"));
            Parent root = loader.load();
            FirstViewController view = loader.getController();
            view.init(this, viewModelFactory.getFirstViewModel());
            Scene scene = new Scene(root);
            stage.setTitle("First");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
