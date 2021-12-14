package View.Manager;

import Model.CredentialsModel;

public class ManagerTabViewModel
{
  private CredentialsModel credentialsModel;
  public ManagerTabViewModel(CredentialsModel credentialsModel)
  {
    this.credentialsModel = credentialsModel;
  }

  public void logOut()
  {
    //credentialsModel.logOutEmployee();
  }
}
