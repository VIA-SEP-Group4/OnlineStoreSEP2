
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
        viewHandler.start();



        //System.out.println("Starting the application ...");
        //RMIClient rmiClient = new RMIClient();
        //LoginModelManager modelManager = new LoginModelManager(rmiClient);

//        modelManager.sendMessage("message message .. ea snd kdn .");
//        modelManager.toUpperCase("d sk ssk KK sa kjd KJ skas kjK Jk.");

        //System.out.println("number of users: " + modelManager.getNumberOfUsers());
        //modelManager.registerUser(new User(new Username("bbbbbbb"), new Password("asdfdhfg")));


//        System.exit(0);
    }
}