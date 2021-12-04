package Model;

import Utils.Subject;

import java.util.ArrayList;

public interface AdminModel extends Subject {
    ArrayList<Employee> getManagerEmployees();

    void addManager(Employee manager);

    void removeManager(Employee manager);
}
