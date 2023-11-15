package foreign_keys.daos;

import foreign_keys.entities.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDaoInterface {
    public List<Department> getAllDepartments();
    public Optional<Department> getDepartmentById(String name);
    public boolean save(Department d);
    public Department update(Department d);
    public boolean remove(Department d);
}
