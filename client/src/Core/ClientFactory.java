package Core;

import Networking.Client;
import Networking.RMIClient;

public class ClientFactory {
    private Client client;
    public Client getClient() {
        if(client==null){
            client=new RMIClient();
        }
        return client;
    }
}
