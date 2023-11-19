package foreign_keys.daos;

import foreign_keys.entities.Department;
import foreign_keys.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDaoInterface {
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(long id);
    List<Employee> getEmployeesByDept(Department dept);
    Optional<Employee> getEmployeeByEmail(String email);
    boolean save(Employee e);
    Employee update(Employee e);
    boolean remove(Employee e);
}
