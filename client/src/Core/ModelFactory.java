package Core;

import Model.LoginModel;
import Model.LoginModelManager;

public class ModelFactory {
    private LoginModel manager;
    private ClientFactory clientFactory;

    public ModelFactory(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }

    public LoginModel getLoginModel() {
        if(manager == null)
            manager = new LoginModelManager(clientFactory.getClient());
        return manager;
    }
}
