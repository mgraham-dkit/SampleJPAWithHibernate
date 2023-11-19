package foreign_keys.daos;

import foreign_keys.entities.Project;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

public class ProjectDao implements ProjectDaoInterface {
    private final EntityManager entityManager;

    public ProjectDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Project> getAllProjects() {
        Query query = entityManager.createQuery("SELECT p FROM Project p");
        return query.getResultList();
    }

    @Override
    public Optional<Project> getProjectById(long id) {
        return Optional.ofNullable(entityManager.find(Project.class, id));
    }

    @Override
    public List<Project> getProjectsByName(String name) {
        Query query = entityManager.createQuery("SELECT p FROM Project p WHERE p.name = ?1");
        query.setParameter(1, name);
        return query.getResultList();
    }

    @Override
    public boolean save(Project p) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(p);
            transaction.commit();
            return true;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A PersistenceException occurred while persisting\n\t" + p);
            transaction.rollback();
            return false;
        }
    }

    @Override
    public Project update(Project p) {
        Optional<Project> result = getProjectById(p.getId());
        if (result.isPresent()) {
            Project project = result.get();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Project updated = entityManager.merge(project);
                transaction.commit();
                return updated;
            } catch (PersistenceException pe) {
                System.err.println(pe.getMessage());
                System.err.println("A PersistenceException occurred while merging\n\t" + p);
                transaction.rollback();
                return null;
            }
        } else {
            System.err.println("An error occurred while trying to update\n\t" + p);
            System.err.println("No such entity exists in the database");
            return null;
        }
    }

    @Override
    public boolean remove(Project p) {
        Optional<Project> result = getProjectById(p.getId());
        if (result.isPresent()) {
            Project loadedProj = result.get();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.remove(loadedProj);
                transaction.commit();
                return true;
            } catch (PersistenceException pe) {
                System.err.println(pe.getMessage());
                System.err.println("A PersistenceException occurred while deleting\n\t" + p);
                transaction.rollback();
                return false;
            }
        } else {
            System.err.println("An error occurred while trying to remove\n\t" + p);
            System.err.println("No such entity exists in the database");
            return false;
        }
    }

    public boolean removeFailIfDetached(Project p) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(p);
            transaction.commit();
            return true;
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Entity\n\t" + p + "\n is currently detached or does not exist");
            transaction.rollback();
            return false;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A PersistenceException occurred while deleting\n\t" + p);
            transaction.rollback();
            return false;
        }
    }
}
