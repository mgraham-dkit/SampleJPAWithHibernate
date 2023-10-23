package application;

import daos.EmployeeDao;
import daos.EmployeeDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.logging.Level;
import java.util.logging.LogManager;

public class TestPersistEmployee {
    public static void main(String[] args) {
        // Hide info messages about hibernate activities
        // Can also hide warnings by setting level to Level.SEVERE
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EmployeeDaoInterface dao = new EmployeeDao(entityManager);

        System.out.println(dao.getAllEmployees());
        System.out.println(dao.getEmployeeByEmail("john.minor54@workdomain.com"));

        entityManager.close();
        entityManagerFactory.close();
    }
}
