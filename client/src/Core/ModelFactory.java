package Core;

import Model.ModelManager;

public class ModelFactory {
    private ModelManager manager;
    private ClientFactory clientFactory;

    public ModelFactory(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }

    public ModelManager getManager() {
        if(manager == null)
            manager = new ModelManager(clientFactory.getClient());
        return manager;
    }
}
