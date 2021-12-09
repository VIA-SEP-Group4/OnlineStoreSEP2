
import Model.CredentialsModelManager;
import Model.OrdersModelManager;
import Model.ProductsModelManager;
import Networking.CredentialsServer;
import Networking.OrdersServer;
import Networking.ProductsServer;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RunServer {

    public static void main(String[] args) {

        //create registry
        createRegistry();

        //create and start servers
        CredentialsModelManager cmm = new CredentialsModelManager();
        ProductsModelManager pmm = new ProductsModelManager();
        OrdersModelManager omm = new OrdersModelManager();

        try {
            ProductsServer productsServer = new ProductsServer(pmm);
            CredentialsServer credentialsServer = new CredentialsServer(cmm);
            OrdersServer ordersServer = new OrdersServer(omm);
            productsServer.start();
            credentialsServer.start();
            ordersServer.start();
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
