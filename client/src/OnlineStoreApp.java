
import Model.ModelManager;
import Networking.RMIClient;
import javafx.application.Application;
import javafx.stage.Stage;

public class OnlineStoreApp extends Application {

    @Override
    public void start(Stage stage) throws Exception
    {
        System.out.println("Starting the application ...");
        RMIClient rmiClient = new RMIClient();
        ModelManager modelManager = new ModelManager(rmiClient);

        modelManager.sendMessage("message message .. ea snd kdn .");
        modelManager.toUpperCase("d sk ssk KK sa kjd KJ skas kjK Jk.");

        System.exit(0);
    }
}
