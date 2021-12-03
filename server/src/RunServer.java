
import Model.ServerModelManager;
import Networking.Server;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RunServer {

    public static void main(String[] args) {

        //create registry
        createRegistry();

        //create and start server
        ServerModelManager serverModelManager = new ServerModelManager();
        try {
            Server server = new Server(serverModelManager);
            server.start();
            System.out.println("Server running ...");

//            server.registerUser(new Customer("username", "pass", "email", "fname", "lname"));
//            Customer user = serverModelManager.loginUser("username", "pass", "customer");
//            System.out.println(user);

        } catch (RemoteException | MalformedURLException e)
        {
            System.err.println("Server launching failed ...[RunServer.main()]");
            e.printStackTrace();
        }
    }


    private static void createRegistry()
    {
        try {
            LocateRegistry.createRegistry(1099);
            System.out.println("Registry created successfully ...");
        } catch (RemoteException e) {
            System.err.println("Registry creation failed ...[RunServer.createRegistry()]");
        }
    }
}
