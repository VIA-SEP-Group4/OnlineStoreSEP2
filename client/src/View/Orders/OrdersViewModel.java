package View.Orders;

import Model.Order;
import Model.Product;
import Model.ProductModelManager;
import Model.ProductsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrdersViewModel
{
    private ProductsModel productsModel;
    private ObservableList<Order> openOrders;
    private ObservableList<Product> openOrdersDetail;
    private ObservableList<Order> myOrders;
    private ObservableList<Product> myOrdersDetail;

    public OrdersViewModel(ProductsModel productsModel){
        this.productsModel = productsModel;
        openOrders = FXCollections.observableArrayList();
        openOrdersDetail = FXCollections.observableArrayList();
        myOrders = FXCollections.observableArrayList();
        myOrdersDetail = FXCollections.observableArrayList();
    }

    public ObservableList<Order> getOpenOrders()
    {
        return openOrders;
    }

    public ObservableList<Product> getOpenOrdersDetail()
    {
        return openOrdersDetail;
    }

    public ObservableList<Order> getMyOrders()
    {
        return myOrders;
    }

    public ObservableList<Product> getMyOrdersDetail()
    {
        return myOrdersDetail;
    }
}
