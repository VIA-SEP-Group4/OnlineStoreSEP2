package View.Login;

import Model.CredentialsModel;
import Model.CredentialsModelManager;
import Networking.RMIClient;
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
    cm = new CredentialsModelManager(new RMIClient());
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
    lvm.login();
    waitForRunLater();

    assertTrue(success.getValue().contains("success"));
  }

}