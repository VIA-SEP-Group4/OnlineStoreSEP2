package Core;

import Model.*;

public class ModelFactory {
    private CredentialsModel credentialsModel;
    private ProductsModel productsModel;
    private ClientFactory clientFactory;
    private AdminModel adminModel;
    public ModelFactory(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }

    public CredentialsModel getCredentialsModel() {
        if(credentialsModel == null)
            credentialsModel = new CredentialsModelManager(clientFactory.getLoginClient());
        return credentialsModel;
    }
    public ProductsModel getProductsModel() {
        if(productsModel == null)
            productsModel = new ProductModelManager(clientFactory.getCustomerClient(),clientFactory.getManagerClient());
        return productsModel;
    }
    public AdminModel getAdminModel() {
        if(adminModel == null)
            adminModel = new AdminModelImpl(clientFactory.getAdminClient());
        return adminModel;
    }
}
