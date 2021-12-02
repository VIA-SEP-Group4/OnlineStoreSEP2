package View.Login;

import Model.CredentialsModel;
import Model.CredentialsModelManager;
import Networking.LoginClientImpl;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.Semaphore;
import static org.junit.jupiter.api.Assertions.*;

class LoginViewModelTest
{
  public static void waitForRunLater() throws InterruptedException {
    Semaphore semaphore = new Semaphore(0);
    Platform.runLater(() -> semaphore.release());
    semaphore.acquire();
  }

  private CredentialsModel cm;
  private LoginViewModel lvm;


  @BeforeEach
  public void setUp(){
    cm = new CredentialsModelManager(new LoginClientImpl());
    lvm = new LoginViewModel(cm);
  }


  @Test
  public void loginExistingUser_test() throws InterruptedException
  {
    //this must be here even though it is not used ->do NOT REMOVE!!!
    JFXPanel jfxPanel = new JFXPanel();

    SimpleStringProperty userName=new SimpleStringProperty();
    SimpleStringProperty password=new SimpleStringProperty();
    SimpleStringProperty errorPass=new SimpleStringProperty();
    SimpleStringProperty errorUser=new SimpleStringProperty();
    SimpleStringProperty success=new SimpleStringProperty();

    lvm.userNameProperty().bindBidirectional(userName);
    lvm.passwordProperty().bindBidirectional(password);
    lvm.successProperty().bindBidirectional(success);

    userName.setValue("username");
    password.setValue("pass");

    lvm.login("customer");
    waitForRunLater();

    assertTrue(success.getValue().contains("success"), "Expected user does not exist");
  }

  @Test
  public void nonExistingUser_loginAttempt_Test() throws InterruptedException
  {
    //this must be here even though it is not used ->do NOT REMOVE!!!
    JFXPanel jfxPanel = new JFXPanel();

    SimpleStringProperty userName=new SimpleStringProperty();
    SimpleStringProperty password=new SimpleStringProperty();
    SimpleStringProperty success=new SimpleStringProperty();

    lvm.userNameProperty().bindBidirectional(userName);
    lvm.passwordProperty().bindBidirectional(password);
    lvm.successProperty().bindBidirectional(success);

    userName.setValue("non-existing_Username");
    password.setValue("non-existing_Password");

    lvm.login("customer");
    waitForRunLater();

    assertFalse(success.getValue().contains("success"), "Expected user exist");
  }

  @Test
  public void emptyString_in_usernameField_Test() throws InterruptedException
  {
    //this must be here even though it is not used ->do NOT REMOVE!!!
    JFXPanel jfxPanel = new JFXPanel();

    SimpleStringProperty userName=new SimpleStringProperty();
    SimpleStringProperty password=new SimpleStringProperty();
    SimpleStringProperty success=new SimpleStringProperty();

    lvm.userNameProperty().bindBidirectional(userName);
    lvm.passwordProperty().bindBidirectional(password);
    lvm.successProperty().bindBidirectional(success);

    userName.setValue("");
    password.setValue("non-existing_Password");

    lvm.login("customer");
    waitForRunLater();

    assertFalse(success.getValue().contains("success"), "Expected user exist");
  }

  @Test
  public void empty_usernameField_Test() throws InterruptedException
  {
    //this must be here even though it is not used ->do NOT REMOVE!!!
    JFXPanel jfxPanel = new JFXPanel();

    SimpleStringProperty userName=new SimpleStringProperty();
    SimpleStringProperty password=new SimpleStringProperty();
    SimpleStringProperty success=new SimpleStringProperty();

    lvm.userNameProperty().bindBidirectional(userName);
    lvm.passwordProperty().bindBidirectional(password);
    lvm.successProperty().bindBidirectional(success);

    //    userName.setValue("username");
    password.setValue("non-existing_Password");

    lvm.login("customer");
    waitForRunLater();

    assertFalse(success.getValue().contains("success"), "Expected user exist");
  }

  @Test
  public void emptyString_in_passwordField_Test() throws InterruptedException
  {
    //this must be here even though it is not used ->do NOT REMOVE!!!
    JFXPanel jfxPanel = new JFXPanel();

    SimpleStringProperty userName=new SimpleStringProperty();
    SimpleStringProperty password=new SimpleStringProperty();
    SimpleStringProperty success=new SimpleStringProperty();

    lvm.userNameProperty().bindBidirectional(userName);
    lvm.passwordProperty().bindBidirectional(password);
    lvm.successProperty().bindBidirectional(success);

    userName.setValue("username");
    password.setValue("");

    lvm.login("customer");
    waitForRunLater();

    assertFalse(success.getValue().contains("success"), "Expected user exist");
  }

  @Test
  public void empty_passwordField_Test() throws InterruptedException
  {
    //this must be here even though it is not used ->do NOT REMOVE!!!
    JFXPanel jfxPanel = new JFXPanel();

    SimpleStringProperty userName=new SimpleStringProperty();
    SimpleStringProperty password=new SimpleStringProperty();
    SimpleStringProperty success=new SimpleStringProperty();

    lvm.userNameProperty().bindBidirectional(userName);
    lvm.passwordProperty().bindBidirectional(password);
    lvm.successProperty().bindBidirectional(success);

        userName.setValue("username");
//    password.setValue("");

    lvm.login("customer");
    waitForRunLater();

    assertFalse(success.getValue().contains("success"), "Expected user exist");
  }
}