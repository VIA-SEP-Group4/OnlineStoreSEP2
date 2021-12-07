
import Model.CredentialsModelManager;
import Model.OrdersModelManager;
import Model.ProductsModelManager;
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
//        ServerModelManager serverModelManager = new ServerModelManager();
        CredentialsModelManager cmm = new CredentialsModelManager();
        ProductsModelManager pmm = new ProductsModelManager();
        OrdersModelManager omm = new OrdersModelManager();

        try {
//            Server server = new Server(serverModelManager);
            Server server = new Server(cmm, pmm, omm);
            server.start();
            System.out.println("Server running ...");

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
