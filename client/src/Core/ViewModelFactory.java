package Core;

import View.AddProduct.AddProductViewModel;
import View.Browser.BrowserViewModel;
import View.Login.LoginViewModel;
import View.Register.RegisterViewModel;

public class ViewModelFactory {
    private LoginViewModel loginViewModel;
    private RegisterViewModel registerViewModel;
    private BrowserViewModel browserViewModel;
    private AddProductViewModel addProductViewModel;

    public ViewModelFactory(ModelFactory modelFactory) {
        loginViewModel = new LoginViewModel(modelFactory.getLoginModel());
        registerViewModel=new RegisterViewModel(modelFactory.getLoginModel());
        browserViewModel = new BrowserViewModel(modelFactory.getLoginModel());
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
    public AddProductViewModel getAddProductViewModel(){
        return addProductViewModel;
    }
}
