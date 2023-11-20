package composite_keys.daos;

import composite_keys.entities.Client;
import composite_keys.entities.ClientId;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

public class ClientDao implements ClientDaoInterface {
    private final EntityManager entityManager;

    public ClientDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Client> getAllClients() {
        Query query = entityManager.createQuery("SELECT c FROM Client c");
        return query.getResultList();
    }

    @Override
    public List<Client> getClientsByCompany(String companyName) {
        Query query = entityManager.createQuery("SELECT c FROM Client c WHERE c.companyName = ?1");
        query.setParameter(1, companyName);
        return query.getResultList();
    }

    @Override
    public Optional<Client> getClientByPrimary(ClientId id) {
        return Optional.ofNullable(entityManager.find(Client.class, id));
    }

    @Override
    public boolean save(Client c) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(c);
            transaction.commit();
            return true;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A PersistenceException occurred while persisting\n\t" + c);
            transaction.rollback();
            return false;
        }
    }

    @Override
    public Client update(Client c) {
        ClientId id = new ClientId(c.getRepName(), c.getCompanyName());
        Optional<Client> result = getClientByPrimary(id);
        if (result.isPresent()) {
            Client client = result.get();
            client.update(c);
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Client updated = entityManager.merge(client);
                transaction.commit();
                return updated;
            } catch (PersistenceException pe) {
                System.err.println(pe.getMessage());
                System.err.println("A PersistenceException occurred while merging\n\t" + client);
                transaction.rollback();
                return null;
            }
        } else {
            System.err.println("An error occurred while trying to update\n\t" + c);
            System.err.println("No such entity exists in the database");
            return null;
        }
    }

    @Override
    public boolean remove(Client c) {
        ClientId id = new ClientId(c.getRepName(), c.getCompanyName());
        Optional<Client> result = getClientByPrimary(id);
        if (result.isPresent()) {
            Client loadedClient = result.get();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.remove(loadedClient);
                transaction.commit();
                return true;
            } catch (PersistenceException pe) {
                System.err.println(pe.getMessage());
                System.err.println("A PersistenceException occurred while deleting\n\t" + c);
                transaction.rollback();
                return false;
            }
        } else {
            System.err.println("An error occurred while trying to remove\n\t" + c);
            System.err.println("No such entity exists in the database");
            return false;
        }
    }

    public boolean removeFailIfDetached(Client c) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(c);
            transaction.commit();
            return true;
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Entity\n\t" + c + "\n is currently detached or does not exist");
            transaction.rollback();
            return false;
        } catch (PersistenceException pe) {
            System.err.println(pe.getMessage());
            System.err.println("A PersistenceException occurred while deleting\n\t" + c);
            transaction.rollback();
            return false;
        }
    }
}
