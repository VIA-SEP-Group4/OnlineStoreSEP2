package Core;

import Networking.*;

public class ClientFactory {

    private LoginClient loginClient;
    private CustomerClient customerClient;
    private AdminClient adminClient;
    private ManagerClient managerClient;
    private WorkerClient workerClient;

    public LoginClient getLoginClient() {
        if(loginClient==null){
            loginClient=new LoginClientImpl();
        }
        return loginClient;
    }
    public CustomerClient getCustomerClient() {
        if(customerClient==null){
            customerClient=new CustomerClientImpl();
        }
        return customerClient;
    }
    public AdminClient getAdminClient() {
        if(adminClient==null){
            adminClient=new AdminClientImpl();
        }
        return adminClient;
    }
    public ManagerClient getManagerClient() {
        if(managerClient==null){
            managerClient=new ManagerClientImpl();
        }
        return managerClient;
    }
    public WorkerClient getWorkerClient() {
        if(workerClient==null){
            workerClient=new WorkerClientImpl();
        }
        return workerClient;
    }
}
