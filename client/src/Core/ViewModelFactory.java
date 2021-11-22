package Core;

import View.Products.AddProductViewModel;
import View.Products.ProductsViewModel;
import View.Browser.BrowserViewModel;
import View.Login.LoginViewModel;
import View.Register.RegisterViewModel;

public class ViewModelFactory {
    private LoginViewModel loginViewModel;
    private RegisterViewModel registerViewModel;
    private BrowserViewModel browserViewModel;
    private ProductsViewModel productsViewModel;
    private AddProductViewModel addProductViewModel;

    public ViewModelFactory(ModelFactory modelFactory) {
        loginViewModel = new LoginViewModel(modelFactory.getLoginModel());
        registerViewModel=new RegisterViewModel(modelFactory.getLoginModel());
        browserViewModel = new BrowserViewModel(modelFactory.getLoginModel());
        productsViewModel = new ProductsViewModel(modelFactory.getLoginModel());
        addProductViewModel = new AddProductViewModel(modelFactory.getLoginModel());
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }
    public RegisterViewModel getRegisterViewModel() {
        return registerViewModel;
    }
    public BrowserViewModel getBrowserViewModel(){
        return browserViewModel;
    }
    public ProductsViewModel getProductsViewModel(){
        return productsViewModel;
    }
    public  AddProductViewModel getAddProductViewModel(){
        return addProductViewModel;
    }
}
