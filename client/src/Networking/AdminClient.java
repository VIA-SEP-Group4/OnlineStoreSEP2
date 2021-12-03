package Networking;

import Model.Employee;
import Utils.Subject;

import java.util.ArrayList;

public interface AdminClient extends Subject {
    void startClient();
    ArrayList<Employee> getManagers();
}
