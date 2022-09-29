package api.iam.client.domain;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientRepository {

    public Page<Client> findAll(Pageable pageable, Client client);

    public Client findByClientId(UUID clientId);

    public Client save(Client client) throws Exception;

    public void delete(Client client) throws Exception;
}
