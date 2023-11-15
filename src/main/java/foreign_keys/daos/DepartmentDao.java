package foreign_keys.daos;

import foreign_keys.entities.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

public class DepartmentDao implements DepartmentDaoInterface {
    private final EntityManager entityManager;

    public DepartmentDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Department> getAllDepartments() {
        Query query = entityManager.createQuery("SELECT d FROM Department d");
        return query.getResultList();
    }

    @Override
    public Optional<Department> getDepartmentById(String id) {
        return Optional.ofNullable(entityManager.find(Department.class, id));
    }

    @Override
    public boolean save(Department d) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(d);
            transaction.commit();
            return true;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A PersistenceException occurred while persisting\n\t" + d);
            transaction.rollback();
            return false;
        }
    }

    @Override
    public Department update(Department d) {
        Optional<Department> result = getDepartmentById(d.getName());
        if (result.isPresent()) {
            Department dept = result.get();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Department updated = entityManager.merge(dept);
                transaction.commit();
                return updated;
            } catch (PersistenceException pe) {
                System.err.println(pe.getMessage());
                System.err.println("A PersistenceException occurred while merging\n\t" + dept);
                transaction.rollback();
                return null;
            }
        } else {
            System.err.println("An error occurred while trying to update\n\t" + d);
            System.err.println("No such entity exists in the database");
            return null;
        }
    }

    @Override
    public boolean remove(Department d) {
        Optional<Department> result = getDepartmentById(d.getName());
        if (result.isPresent()) {
            Department loadedDept = result.get();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.remove(loadedDept);
                transaction.commit();
                return true;
            } catch (PersistenceException pe) {
                System.err.println(pe.getMessage());
                System.err.println("A PersistenceException occurred while deleting\n\t" + d);
                transaction.rollback();
                return false;
            }
        } else {
            System.err.println("An error occurred while trying to remove\n\t" + d);
            System.err.println("No such entity exists in the database");
            return false;
        }
    }

    public boolean removeFailIfDetached(Department d) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(d);
            transaction.commit();
            return true;
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Entity\n\t" + d + "\n is currently detached or does not exist");
            transaction.rollback();
            return false;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A PersistenceException occurred while deleting\n\t" + d);
            transaction.rollback();
            return false;
        }
    }
}
