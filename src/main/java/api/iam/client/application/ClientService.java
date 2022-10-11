package api.iam.client.application;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import api.iam.client.domain.request.AddClientRequest;
import api.iam.client.domain.request.UpdateClientRequest;
import api.iam.client.domain.response.ClientResponse;
import api.shared.domain.response.PaginationResponse;

public interface ClientService {

    public ClientResponse getClient(UUID clientId) throws Exception;

    public PaginationResponse getAllClient(Pageable pageable, ClientResponse client);

    public ClientResponse addClient(AddClientRequest request) throws Exception;

    public ClientResponse updateClient(UpdateClientRequest request) throws Exception;

    public UUID deleteClient(UUID clientId) throws Exception;
}
