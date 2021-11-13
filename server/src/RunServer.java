import DataAcess.DBSManager;
import Model.Password;
import Model.ServerModelManager;
import Model.User;
import Model.Username;
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
        } catch (RemoteException | MalformedURLException e)
        {
            System.err.println("Server launching failed ...[RunServer.main()]");
        }

        DBSManager dbsManager = new DBSManager();
        System.out.println("num of users = " + dbsManager.getUserCount());
        dbsManager.getUsers();
        dbsManager.registerUser(new User(new Username("asd"), new Password("dsa")));
        dbsManager.getUsers();
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
