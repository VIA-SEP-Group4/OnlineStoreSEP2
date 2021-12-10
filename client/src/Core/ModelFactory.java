package Core;

import Model.*;

public class ModelFactory {
    private CredentialsModel credentialsModel;
    private OrdersModel ordersModel;
    private ProductsModel productsModel;
    private ClientFactory clientFactory;

    public ModelFactory(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }

    public OrdersModel getOrdersModel(){
        if(ordersModel ==  null){
            ordersModel=new OrdersModelManager(clientFactory.getOrdersClient());
        }
        return ordersModel;
    }
    public ProductsModel getProductsModel(){
        if(productsModel == null){
            productsModel=new ProductsModelManager(clientFactory.getProductsClient());
        }
        return productsModel;
    }
    public CredentialsModel getCredentialsModel() {
        if(credentialsModel == null)
            credentialsModel = new CredentialsModelManager(clientFactory.getCredentialsClient());
        return credentialsModel;
    }

}
