package Core;

import Model.*;

public class ModelFactory {
    private CredentialsModel credentialsModel;
    private ClientFactory clientFactory;
    private AdminModel adminModel;
    private CustomerModel customerModel;
    private ManagerModel managerModel;
    private WorkerModel workerModel;
    public ModelFactory(ClientFactory clientFactory){
        this.clientFactory = clientFactory;
    }

    public CredentialsModel getCredentialsModel() {
        if(credentialsModel == null)
            credentialsModel = new CredentialsModelManager(clientFactory.getLoginClient());
        return credentialsModel;
    }

    public AdminModel getAdminModel() {
        if(adminModel == null)
            adminModel = new AdminModelImpl(clientFactory.getAdminClient());
        return adminModel;
    }

    public CustomerModel getCustomerModel() {
        if(customerModel==null) customerModel=new CustomerModelImpl(clientFactory.getCustomerClient(), clientFactory.getLoginClient());
        return customerModel;
    }

    public ManagerModel getManagerModel() {
        if(managerModel==null) managerModel=new ManagerModelImpl(clientFactory.getManagerClient(), clientFactory.getLoginClient());
        return managerModel;
    }

    public WorkerModel getWorkerModel() {
        if(workerModel==null) workerModel=new WorkerModelImpl(clientFactory.getWorkerClient(), clientFactory.getLoginClient());
        return workerModel;
    }
}
