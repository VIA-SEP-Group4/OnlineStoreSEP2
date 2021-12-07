package Model;

import Model.Models.Employee;
import Utils.Subject;

import java.util.ArrayList;

public interface AdminModel extends Subject {
    ArrayList<Employee> getManagerEmployees();

    void addManager(Employee manager);
    void removeManager(Employee manager);
    void editManager(Employee manager);
}
