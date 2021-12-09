package View.AccountSettings;

public class AccountDeletedExceptionReply extends RuntimeException
{
  public AccountDeletedExceptionReply(String msg){
    super(msg);
  }
}
