package Core;

import View.Checkout.CheckoutViewModel;
import View.Orders.OrdersViewModel;
import View.Products.AddProductViewModel;
import View.Products.ProductsViewModel;
import View.Browser.BrowserViewModel;
import View.Login.LoginViewModel;
import View.Register.RegisterViewModel;

public class ViewModelFactory {
    private static ModelFactory modelFactory;

    private static  LoginViewModel loginViewModel;
    private static RegisterViewModel registerViewModel;
    private static BrowserViewModel browserViewModel;
    private static ProductsViewModel productsViewModel;
    private static AddProductViewModel addProductViewModel;
    private static CheckoutViewModel checkoutViewModel;
    private static OrdersViewModel ordersViewModel;

    public ViewModelFactory(ModelFactory modelFactory) {
        ViewModelFactory.modelFactory =modelFactory;
    }

    public static LoginViewModel getLoginViewModel() {
        if(loginViewModel==null){
            loginViewModel=new LoginViewModel(modelFactory.getCredentialsModel());
        }
        return loginViewModel;
    }
    public static RegisterViewModel getRegisterViewModel() {
        if(registerViewModel==null){
            registerViewModel=new RegisterViewModel(modelFactory.getCredentialsModel());
        }
        return registerViewModel;
    }
    public static BrowserViewModel getBrowserViewModel(){
        if(browserViewModel==null){
            modelFactory.getCredentialsModel();
            browserViewModel=new BrowserViewModel(modelFactory.getProductsModel());
        }
        return browserViewModel;
    }
    public static ProductsViewModel getProductsViewModel(){
        if(productsViewModel==null){
            modelFactory.getCredentialsModel();
            productsViewModel=new ProductsViewModel(modelFactory.getProductsModel());
        }
        return productsViewModel;
    }
    public static   AddProductViewModel getAddProductViewModel(){
        if(addProductViewModel==null){
            addProductViewModel=new AddProductViewModel(modelFactory.getProductsModel());
        }
        return addProductViewModel;
    }

    public static CheckoutViewModel getCheckoutViewModel(){
        if(checkoutViewModel == null){
            checkoutViewModel = new CheckoutViewModel(modelFactory.getProductsModel());
        }
        return checkoutViewModel;
    }

    public static OrdersViewModel getOrdersViewModel(){
        if(ordersViewModel == null){
            ordersViewModel = new OrdersViewModel(modelFactory.getProductsModel());
        }
        return ordersViewModel;
    }
}
