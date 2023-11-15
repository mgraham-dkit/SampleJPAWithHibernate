package foreign_keys.daos;

import foreign_keys.entities.Department;
import foreign_keys.entities.Employee;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

public class EmployeeDao implements EmployeeDaoInterface {
    private final EntityManager entityManager;

    public EmployeeDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> getAllEmployees() {
        Query query = entityManager.createQuery("SELECT e FROM Employee e");
        return query.getResultList();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return Optional.ofNullable(entityManager.find(Employee.class, id));
    }

    @Override
    public List<Employee> getEmployeesByDept(Department dept) {
        Query query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.department = ?1");
        query.setParameter(1, dept);
        return query.getResultList();
    }

    @Override
    public Optional<Employee> getEmployeeByEmail(String email) {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE email = ?1",
                Employee.class);
        query.setParameter(1, email);
        try {
            return Optional.ofNullable(query.getSingleResult());
        }catch(NoResultException e){
            System.out.println("No match found for email address: " + email);
            return Optional.empty();
        }catch(NonUniqueResultException e){
            System.out.println("Multiple matches found for email: " + email + ". Returning first match.");
            return Optional.ofNullable(query.getResultList().get(0));
        }
    }

    @Override
    public boolean save(Employee e) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(e);
            transaction.commit();
            return true;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A PersistenceException occurred while persisting\n\t" + e);
            transaction.rollback();
            return false;
        }
    }

    @Override
    public Employee update(Employee e) {
        Optional<Employee> result = getEmployeeById(e.getId());
        if (result.isPresent()) {
            Employee emp = result.get();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Employee updated = entityManager.merge(emp);
                transaction.commit();
                return updated;
            } catch (PersistenceException pe) {
                System.err.println(pe.getMessage());
                System.err.println("A PersistenceException occurred while merging\n\t" + e);
                transaction.rollback();
                return null;
            }
        } else {
            System.err.println("An error occurred while trying to update\n\t" + e);
            System.err.println("No such entity exists in the database");
            return null;
        }
    }

    @Override
    public boolean remove(Employee e) {
        Optional<Employee> result = getEmployeeById(e.getId());
        if (result.isPresent()) {
            Employee loadedEmp = result.get();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.remove(loadedEmp);
                transaction.commit();
                return true;
            } catch (PersistenceException pe) {
                System.err.println(pe.getMessage());
                System.err.println("A PersistenceException occurred while deleting\n\t" + e);
                transaction.rollback();
                return false;
            }
        } else {
            System.err.println("An error occurred while trying to remove\n\t" + e);
            System.err.println("No such entity exists in the database");
            return false;
        }
    }

    public boolean removeFailIfDetached(Employee e) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(e);
            transaction.commit();
            return true;
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Entity\n\t" + e + "\n is currently detached or does not exist");
            transaction.rollback();
            return false;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A PersistenceException occurred while deleting\n\t" + e);
            transaction.rollback();
            return false;
        }
    }
}
