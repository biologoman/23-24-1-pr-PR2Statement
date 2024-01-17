package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.traversal.Iterator;

public class Role {
    private String roleId;
    private String description;
    private List<Employee> employees;

    public Role(String roleId, String description){
        this.setRoleId(roleId);
        this.setDescription(description);
        employees = new LinkedList<>();
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return this.description;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void removeEmployee(Employee employee) {
        Employee.removeEmployee(employees, employee);
    }

    public void addEmployee(Employee employee) {
            employees.insertEnd(employee);

    }


    public int numEmployees() {
        return employees.size();
    }

    public Iterator<Employee> employees() {
        return employees.values();
    }

    public boolean hasEmployee() {
        return numEmployees()>0;
    }
}
