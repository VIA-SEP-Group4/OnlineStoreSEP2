
import Core.ClientFactory;
import Core.ModelFactory;
import Core.ViewHandler;
import Core.ViewModelFactory;
import Model.*;
import Networking.RMIClient;
import javafx.application.Application;
import javafx.stage.Stage;

public class OnlineStoreApp extends Application {

    @Override
    public void start(Stage stage)
    {
        ClientFactory clientFactory = new ClientFactory();
        ModelFactory modelFactory = new ModelFactory(clientFactory);
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(stage, viewModelFactory);
        viewHandler.startCustomer();
    }
}
