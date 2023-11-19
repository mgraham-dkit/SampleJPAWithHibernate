package foreign_keys.daos;

import foreign_keys.entities.Department;
import foreign_keys.entities.Employee;
import foreign_keys.entities.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectDaoInterface {
    public List<Project> getAllProjects();
    public Optional<Project> getProjectById(long id);
    public List<Project> getProjectsByName(String name);
    public boolean save(Project p);
    public Project update(Project p);
    public boolean remove(Project p);
}
