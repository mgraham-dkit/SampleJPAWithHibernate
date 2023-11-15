package composite_keys.daos;

import composite_keys.entities.Client;
import composite_keys.entities.ClientId;

import java.util.List;
import java.util.Optional;

public interface ClientDaoInterface {
    public List<Client> getAllClients();

    public List<Client> getClientsByCompany(String companyName);
    public Optional<Client> getClientByPrimary(ClientId id);
    public boolean save(Client c);
    public Client update(Client c);
    public boolean remove(Client c);
}
