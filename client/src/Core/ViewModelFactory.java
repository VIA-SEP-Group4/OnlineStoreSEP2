package Core;

import View.Admin.AdminViewModel;
import View.Checkout.CheckoutViewModel;
import View.Manager.WorkersOverviewViewModel;
import View.Orders.OrdersViewModel;
import View.Manager.AddProductViewModel;
import View.Manager.ProductsViewModel;
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
    private static AdminViewModel adminViewModel;
    private static WorkersOverviewViewModel workersOverviewViewModel;
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
            browserViewModel=new BrowserViewModel(modelFactory.getCustomerModel(),modelFactory.getCredentialsModel());
        }
        return browserViewModel;
    }
    public static ProductsViewModel getProductsViewModel(){
        if(productsViewModel==null){
            modelFactory.getCredentialsModel();
            productsViewModel=new ProductsViewModel(modelFactory.getManagerModel());
        }
        return productsViewModel;
    }
    public static   AddProductViewModel getAddProductViewModel(){
        if(addProductViewModel==null){
            addProductViewModel=new AddProductViewModel(modelFactory.getManagerModel());
        }
        return addProductViewModel;
    }

    public static CheckoutViewModel getCheckoutViewModel(){
        if(checkoutViewModel == null){
            checkoutViewModel = new CheckoutViewModel(modelFactory.getCustomerModel(),modelFactory.getCredentialsModel());
        }
        return checkoutViewModel;
    }

    public static OrdersViewModel getOrdersViewModel(){
        if(ordersViewModel == null){
            ordersViewModel = new OrdersViewModel(modelFactory.getWorkerModel());
        }
        return ordersViewModel;
    }
    public static AdminViewModel getAdminViewModel(){
        if(adminViewModel == null){
            adminViewModel = new AdminViewModel(modelFactory.getAdminModel());
        }
        return adminViewModel;
    }
    public static WorkersOverviewViewModel getWorkersOverviewViewModel(){
        if(workersOverviewViewModel==null){
            workersOverviewViewModel=new WorkersOverviewViewModel(modelFactory.getManagerModel());
        }
        return workersOverviewViewModel;
    }
}
