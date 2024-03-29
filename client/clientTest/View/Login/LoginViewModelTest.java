package View.Login;

import Model.CredentialsModel;
import Model.CredentialsModelManager;
import Model.ProductsModel;
import Model.ProductsModelManager;
import Networking.CredentialsClientImpl;
import Networking.ProductsClientImpl;
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
  private ProductsModel pm;
  private LoginViewModel lvm;


  @BeforeEach
  public void setUp(){
    cm = new CredentialsModelManager(new CredentialsClientImpl());
    pm = new ProductsModelManager(new ProductsClientImpl());
    lvm = new LoginViewModel(cm,pm);
  }


  @Test
  public void loginExistingCustomer_test() throws InterruptedException
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

    lvm.login("Customer");
    waitForRunLater();

    assertTrue(success.getValue().contains("success"), "Expected user does not exist");
  }

  @Test
  public void nonExistingCustomer_loginAttempt_Test() throws InterruptedException
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

    lvm.login("Customer");
    waitForRunLater();

    assertNull(success.getValue(), "Expected user exist");
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

    lvm.login("Customer");
    waitForRunLater();

    assertNull(success.getValue(), "Expected user exist");
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

    lvm.login("Customer");
    waitForRunLater();

    assertNull(success.getValue(), "Expected user exist");
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

    lvm.login("Customer");
    waitForRunLater();

//    assertFalse(success.getValue().contains("success"), "Expected user exist");
    assertNull(success.getValue(), "Expected user exist");
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

    lvm.login("Customer");
    waitForRunLater();

//    assertTrue(error.getValue().length()>0, "Expected user exist");
    assertNull(success.getValue(), "Expected user exist");
  }
}