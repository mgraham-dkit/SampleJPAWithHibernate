package foreign_keys.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name="departments")
public class Department {
    @Id
    private String name;
    @OneToMany(mappedBy = "department")
    private Set<Employee> employees;

    public Department() {
        employees = new HashSet<>();
    }

    public Department(String name) {
        this.name = name;
        employees = new HashSet<>();
    }

    public Department(String name, Set<Employee> employees) {
        this.name = name;
        this.employees = employees;
        employees = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> staff) {
        this.employees = staff;
    }

    // Needed for synchronisation of data in bidirectional relationship
    public boolean addStaff(Employee e){
        if(employees.add(e)){
            e.setDepartment(this);
            return true;
        }
        return false;
    }
    // Needed for synchronisation of data in bidirectional relationship
    public boolean removeStaff(Employee e){
        if(this.employees.remove(e)){
            e.setDepartment(null);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        String emailList = "";
        for(Employee e : employees){
            emailList+=e.getEmail()+"\n";
        }
        return "Department{" +
                "name='" + name + '\'' + "\nstaffList=\n" + emailList +
                '}';
    }
}
