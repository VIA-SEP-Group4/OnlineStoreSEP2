
import Core.ClientFactory;
import Core.ModelFactory;
import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.*;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.text.View;

public class OnlineStoreApp extends Application {

    @Override
    public void start(Stage stage)
    {
        ClientFactory clientFactory = new ClientFactory();
        ModelFactory modelFactory = new ModelFactory(clientFactory);
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = ViewHandler.getInstance();
        viewHandler.initialize(stage,viewModelFactory);
        viewHandler.start();
    }
}
