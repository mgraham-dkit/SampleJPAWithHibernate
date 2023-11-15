package foreign_keys.daos;

import foreign_keys.entities.Department;
import foreign_keys.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDaoInterface {
    public List<Employee> getAllEmployees();
    public Optional<Employee> getEmployeeById(long id);

    public List<Employee> getEmployeesByDept(Department dept);
    public Optional<Employee> getEmployeeByEmail(String email);
    public boolean save(Employee e);
    public Employee update(Employee e);
    public boolean remove(Employee e);
}
