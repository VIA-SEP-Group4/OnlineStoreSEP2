package Networking;

import Model.Models.Employee;
import Utils.Subject;

import java.util.ArrayList;

public interface AdminClient extends Subject {
    void startClient();
    ArrayList<Employee> getManagers();
    void addManager(Employee manager);

    void removeManager(Employee manager);
    void editManager(Employee manager);
}
