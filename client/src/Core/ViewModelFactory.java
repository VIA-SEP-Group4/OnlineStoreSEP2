package Core;

import View.AccountSettings.AccSettingsViewModel;
import View.Admin.AdminViewModel;
import View.Checkout.CheckoutViewModel;
import View.Manager.*;
import View.Orders.OrdersViewModel;
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
    private static OrderOverviewViewModel orderOverviewViewModel;
    private static AccSettingsViewModel accSettingsViewModel;
    private static ManagerTabViewModel managerTabViewModel;


    public ViewModelFactory(ModelFactory modelFactory) {
        ViewModelFactory.modelFactory =modelFactory;
    }


    public static LoginViewModel getLoginViewModel() {
        if(loginViewModel==null){
            loginViewModel=new LoginViewModel(modelFactory.getCredentialsModel(), modelFactory.getProductsModel());
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
            browserViewModel=new BrowserViewModel(modelFactory.getProductsModel(),modelFactory.getCredentialsModel());
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
            checkoutViewModel = new CheckoutViewModel(modelFactory.getProductsModel(),modelFactory.getCredentialsModel(), modelFactory.getOrdersModel());
        }
        return checkoutViewModel;
    }

    public static OrdersViewModel getOrdersViewModel(){
        if(ordersViewModel == null){
            ordersViewModel = new OrdersViewModel(modelFactory.getOrdersModel(), modelFactory.getCredentialsModel());
        }
        return ordersViewModel;
    }

    public static AdminViewModel getAdminViewModel(){
        if(adminViewModel == null){
            adminViewModel = new AdminViewModel(modelFactory.getCredentialsModel());
        }
        return adminViewModel;
    }

    public static WorkersOverviewViewModel getWorkersOverviewViewModel(){
        if(workersOverviewViewModel==null){
            workersOverviewViewModel=new WorkersOverviewViewModel(modelFactory.getCredentialsModel(), modelFactory.getOrdersModel());
        }
        return workersOverviewViewModel;
    }

    public static OrderOverviewViewModel getOrdersOverviewViewModel(){
        if(orderOverviewViewModel ==null){
            orderOverviewViewModel =new OrderOverviewViewModel(modelFactory.getOrdersModel());
        }
        return orderOverviewViewModel;
    }


    public static AccSettingsViewModel getAccSettingsViewModel()
    {
        if (accSettingsViewModel == null){
            accSettingsViewModel = new AccSettingsViewModel(modelFactory.getCredentialsModel());
        }
        return accSettingsViewModel;
    }

    public static ManagerTabViewModel getManagerTabViewModel()
    {
        if (managerTabViewModel == null){
            managerTabViewModel = new ManagerTabViewModel(modelFactory.getCredentialsModel());
        }
        return managerTabViewModel;
    }
}
