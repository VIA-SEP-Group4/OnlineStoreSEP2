package Core;

import Model.CredentialsModel;
import Model.CredentialsModelManager;
import Model.ProductModelManager;
import Model.ProductsModel;

public class ModelFactory {
    private CredentialsModel credentialsModel;
    private ProductsModel productsModel;
    private ClientFactory clientFactory;

    public ModelFactory(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }

    public CredentialsModel getCredentialsModel() {
        if(credentialsModel == null)
            credentialsModel = new CredentialsModelManager(clientFactory.getClient());
        return credentialsModel;
    }
    public ProductsModel getProductsModel() {
        if(productsModel == null)
            productsModel = new ProductModelManager(clientFactory.getClient());
        return productsModel;
    }
}
