package foreign_keys.daos;

import foreign_keys.entities.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDaoInterface {
    List<Department> getAllDepartments();
    Optional<Department> getDepartmentById(String name);
    boolean save(Department d);
    Department update(Department d);
    boolean remove(Department d);
}
