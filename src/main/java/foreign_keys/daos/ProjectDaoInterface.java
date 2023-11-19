package foreign_keys.daos;

import foreign_keys.entities.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectDaoInterface {
    List<Project> getAllProjects();
    Optional<Project> getProjectById(long id);
    List<Project> getProjectsByName(String name);
    boolean save(Project p);
    Project update(Project p);
    boolean remove(Project p);
}
