package View.Register;

import Model.CredentialsModel;
import Model.CredentialsModelManager;
import Networking.RMIClient;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class RegisterViewModelTest {
    private CredentialsModel model;
    private RegisterViewModel rvm;
    public static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(() -> semaphore.release());
        semaphore.acquire();
    }
    @BeforeEach
    void setUp() {
        model=new CredentialsModelManager(new RMIClient());
        rvm=new RegisterViewModel(model);
    }
    @Test
    void registerWithCorrectCredentials() throws InterruptedException {
        JFXPanel jfxPanel = new JFXPanel();
         StringProperty fName=new SimpleStringProperty();
         StringProperty lName=new SimpleStringProperty();
        StringProperty userName=new SimpleStringProperty();
        StringProperty email=new SimpleStringProperty();
        StringProperty password=new SimpleStringProperty();
        StringProperty rePassword=new SimpleStringProperty();
        StringProperty success=new SimpleStringProperty() ;
        fName.bindBidirectional(rvm.fNameProperty());
        lName.bindBidirectional(rvm.lNameProperty());
        userName.bindBidirectional(rvm.userNameProperty());
        email.bindBidirectional(rvm.emailProperty());
        password.bindBidirectional(rvm.passwordProperty());
        rePassword.bindBidirectional(rvm.rePasswordProperty());
        success.bindBidirectional(rvm.successProperty());
        fName.setValue("Edward");
        lName.setValue("Kioklovski");
        userName.setValue("edk123");
        email.setValue("edw@gmail.com");
        password.setValue("123Kill");
        rePassword.setValue("123Kill");
        rvm.sendRegisterData();
        assertTrue(success.getValue().contains("success"));
    }
    @Test
    void registerWithNoCredentials() throws InterruptedException {
        JFXPanel jfxPanel = new JFXPanel();
        StringProperty fName=new SimpleStringProperty();
        StringProperty lName=new SimpleStringProperty();
        StringProperty userName=new SimpleStringProperty();
        StringProperty email=new SimpleStringProperty();
        StringProperty password=new SimpleStringProperty();
        StringProperty rePassword=new SimpleStringProperty();
        StringProperty success=new SimpleStringProperty() ;
        fName.bindBidirectional(rvm.fNameProperty());
        lName.bindBidirectional(rvm.lNameProperty());
        userName.bindBidirectional(rvm.userNameProperty());
        email.bindBidirectional(rvm.emailProperty());
        password.bindBidirectional(rvm.passwordProperty());
        rePassword.bindBidirectional(rvm.rePasswordProperty());
        success.bindBidirectional(rvm.successProperty());
        fName.setValue("");
        lName.setValue("");
        userName.setValue("");
        email.setValue("");
        password.setValue("");
        rePassword.setValue("");
        rvm.sendRegisterData();
        assertNull(success.getValue());
    }
    @Test
    void registerWithNonmatchingPassword() throws InterruptedException {
        JFXPanel jfxPanel = new JFXPanel();
        StringProperty fName=new SimpleStringProperty();
        StringProperty lName=new SimpleStringProperty();
        StringProperty userName=new SimpleStringProperty();
        StringProperty email=new SimpleStringProperty();
        StringProperty password=new SimpleStringProperty();
        StringProperty rePassword=new SimpleStringProperty();
        StringProperty success=new SimpleStringProperty() ;
        StringProperty error=new SimpleStringProperty() ;
        fName.bindBidirectional(rvm.fNameProperty());
        lName.bindBidirectional(rvm.lNameProperty());
        userName.bindBidirectional(rvm.userNameProperty());
        email.bindBidirectional(rvm.emailProperty());
        password.bindBidirectional(rvm.passwordProperty());
        rePassword.bindBidirectional(rvm.rePasswordProperty());
        success.bindBidirectional(rvm.successProperty());
        error.bindBidirectional(rvm.errorPassProperty());
        fName.setValue("Marian");
        lName.setValue("Coianu");
        userName.setValue("sexyBalamuc");
        email.setValue("kingofsex@hotmail.com");
        password.setValue("kiss23");
        rePassword.setValue("sex23");
        rvm.sendRegisterData();
        assertEquals(error.getValue(),"Passwords don't match");
    }
    @Test
    void registerWithSomeCredentials() throws InterruptedException {
        JFXPanel jfxPanel = new JFXPanel();
        StringProperty fName=new SimpleStringProperty();
        StringProperty lName=new SimpleStringProperty();
        StringProperty userName=new SimpleStringProperty();
        StringProperty email=new SimpleStringProperty();
        StringProperty password=new SimpleStringProperty();
        StringProperty rePassword=new SimpleStringProperty();
        StringProperty success=new SimpleStringProperty() ;
        StringProperty error=new SimpleStringProperty() ;
        fName.bindBidirectional(rvm.fNameProperty());
        lName.bindBidirectional(rvm.lNameProperty());
        userName.bindBidirectional(rvm.userNameProperty());
        email.bindBidirectional(rvm.emailProperty());
        password.bindBidirectional(rvm.passwordProperty());
        rePassword.bindBidirectional(rvm.rePasswordProperty());
        success.bindBidirectional(rvm.successProperty());
        error.bindBidirectional(rvm.errorFieldsProperty());
        fName.setValue("Marian");
        lName.setValue("Coianu");
        userName.setValue("sexyBalamuc");
        email.setValue("kingofsex@hotmail.com");
        password.setValue("");
        rePassword.setValue("");
        rvm.sendRegisterData();
        assertEquals(error.getValue(),"Fields cannot be empty on registering");
    }
}