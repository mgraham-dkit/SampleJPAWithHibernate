package composite_keys.applications;

import composite_keys.daos.ClientDao;
import composite_keys.daos.ClientDaoInterface;
import composite_keys.entities.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class TestPersistClient {
    public static void main(String[] args) {
        // Hide info messages about hibernate activities
        // Can also hide warnings by setting level to Level.SEVERE
        LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ClientDaoInterface dao = new ClientDao(entityManager);

            Client newClient = new Client("Helen Gellar Willick Bunch", "BigArcheologist Corp.", 2, 45000.50);
            dao.save(newClient);
            Client newClient2 = new Client("Alan Taylor", "BigArcheologist Corp.", 1, 45000.50);
            dao.save(newClient2);
            Client newClient3 = new Client("Ivan Perez", "SmallArcheologist Corp.", 2, 15000.50);
            dao.save(newClient3);
            Client newClient4 = new Client("Izzy Perez", "Paleontologists on Parade", 1, 145000.50);
            dao.save(newClient4);

        printList(dao.getAllClients());
        System.out.println("------------");
        printList(dao.getClientsByCompany("BigArcheologist Corp."));

        entityManager.close();
        entityManagerFactory.close();
    }

    public static void printList(List list){
        System.out.println("*****************");
        for (Object o : list) {
            System.out.println(o);
            System.out.println("-----------------");
        }
        System.out.println("*****************");
    }
}
