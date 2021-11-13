package Core;

import View.Login.LoginViewModel;
import View.Register.RegisterViewModel;

public class ViewModelFactory {
    private LoginViewModel loginViewModel;
    private RegisterViewModel registerViewModel;
    public ViewModelFactory(ModelFactory modelFactory) {
        loginViewModel = new LoginViewModel(modelFactory.getLoginModel());
        registerViewModel=new RegisterViewModel(modelFactory.getLoginModel());
    }

    public LoginViewModel getLoginViewModel() {
        return loginViewModel;
    }
    public RegisterViewModel getRegisterViewModel() {
        return registerViewModel;
    }
}
