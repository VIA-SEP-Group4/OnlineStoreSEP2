package Core;

import Model.Models.Order;
import Networking.*;

public class ClientFactory {

    private CredentialsClient credentialsClient;
    private ProductsClient productsClient;
    private OrdersClient ordersClient;
    public CredentialsClient getCredentialsClient() {
        if(credentialsClient ==null){
            credentialsClient =new CredentialsClientImpl();
        }
        return credentialsClient;
    }
    public ProductsClient getProductsClient() {
        if(productsClient ==null){
            productsClient =new ProductsClientImpl();
        }
        return productsClient;
    }
    public OrdersClient getOrdersClient() {
        if(ordersClient ==null){
            ordersClient =new OrdersClientImpl();
        }
        return ordersClient;
    }

}
