package View.Orders;

import Model.Order;
import Model.Product;
import Model.ProductModelManager;
import Model.ProductsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

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
        // TODO: 02/12/2021 calculation of the total price - separate quantity of product per order? 
        Product prod1 = new Product("Slinky", "Toy", 52.6, "desc", 5);
        Product prod2 = new Product("Woody", "Toy", 31.1, "desc", 50);
        Product prod3 = new Product("Buzz", "Toy", 50.6, "desc", 20);
        Product prod4 = new Product("Rex", "Toy", 70, "desc", 14);
        ArrayList<Product> list = new ArrayList<>();
        list.add(prod1);
        list.add(prod2);
        ArrayList<Product> list1 = new ArrayList<>();
        list1.add(prod3);
        list1.add(prod4);
        openOrders.add(new Order(1,list));
        openOrders.add(new Order(2, list1));
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

    public void showOpenOrderDetails(int id){
        openOrdersDetail.clear();
        for (Order o : openOrders){
            if (o.getOrderId() == id)
                openOrdersDetail.addAll(o.getProducts());
        }
    }

    public void showMyOrderDetails(int id){
        myOrdersDetail.clear();
        for (Order o : myOrders){
            if (o.getOrderId() == id)
                myOrdersDetail.addAll(o.getProducts());
        }
    }

    public void pickOrder(int id){
        openOrdersDetail.clear();
        List<Order> toRemove = new ArrayList<>();
        for (Order o : openOrders){
            if (o.getOrderId() == id){
                o.setStatus("Processing");
                toRemove.add(o);
            }
        }
        openOrders.remove(toRemove.get(0));
        myOrders.add(toRemove.get(0));
        toRemove.clear();
    }

    public void removeOrder(int id){
        myOrdersDetail.clear();
        List<Order> toRemove = new ArrayList<>();
        for (Order o : myOrders){
            if (o.getOrderId() == id){
                o.setStatus("Waiting");
                toRemove.add(o);
            }
        }
        myOrders.remove(toRemove.get(0));
        openOrders.add(toRemove.get(0));
        toRemove.clear();
    }

    public void completeOrder(int id){
        for (Order o : myOrders){
            if (o.getOrderId() == id){
                o.setStatus("Completed");
            }
        }
    }
}
