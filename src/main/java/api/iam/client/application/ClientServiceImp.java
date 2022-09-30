package api.iam.client.application;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;

import api.shared.domain.Builder;
import api.shared.domain.exception.ServiceException;
import api.shared.domain.response.OnResponse;
import api.shared.domain.response.PaginationResponse;
import api.iam.client.domain.ClientRepository;
import api.iam.client.domain.Client;
import api.iam.client.domain.request.AddClientRequest;
import api.iam.client.domain.request.UpdateClientRequest;
import api.iam.client.domain.response.ClientResponse;

public class ClientServiceImp implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImp(ClientRepository clientRepository) {

        this.clientRepository = clientRepository;
    }

    public static ClientServiceImp build(ClientRepository clientRepository) {

        return new ClientServiceImp(clientRepository);
    }

    @Override
    public ResponseEntity<?> getClient(UUID clientId) throws Exception {

        Client client = clientRepository.findByClientId(clientId);

        if (client == null) {
            throw new ServiceException("Client not found");
        }

        return OnResponse.onSuccess(mapToClientDto(client), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllClient(Pageable pageable, ClientResponse data) {

        Builder<Client> builder = Builder.set(Client.class);

        if (data.getClientCode() != null) {
            builder.with(r -> r.setClientCode(data.getClientCode().toUpperCase()));
        }

        if (data.getClientName() != null) {
            builder.with(r -> r.setClientName(data.getClientName()));
        }
        
        Client client = builder.build();

        Page<Client> clients = clientRepository.findAll(pageable, client);

        List<Client> clientList = clients.getContent();
        List<ClientResponse> content = clientList
            .stream()
            .map(r -> mapToClientDto(r))
            .toList();

        PaginationResponse result = Builder.set(PaginationResponse.class)
            .with(p -> p.setData(content))
            .with(p -> p.setPage((short) clients.getNumber()))
            .with(p -> p.setLimit((byte) clients.getSize()))
            .with(p -> p.setTotalItems((short) clients.getTotalElements()))
            .with(p -> p.setTotalPages((short) clients.getTotalPages()))
            .with(p -> p.setLast(clients.isLast()))
            .build();

        return OnResponse.onSuccessPagination(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addClient(AddClientRequest data) throws Exception {

        Client client = Builder.set(Client.class)
            .with(u -> u.setClientId(UUID.randomUUID()))
            .with(u -> u.setClientCode(data.getClientCode().toUpperCase()))
            .with(u -> u.setClientName(data.getClientName()))
            .build();

        client = clientRepository.save(client);

        if (client == null) {
            throw new ServiceException("The client is already registered");
        }

        return OnResponse.onSuccess(mapToClientDto(client), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateClient(UpdateClientRequest data) throws Exception {

        Boolean needUpdate = false;

        Client client = clientRepository.findByClientId(data.getClientId());

        if (client == null) {
            throw new ServiceException("The client was not found");
        }

        if (data.getClientCode() != null && !data.getClientCode().equalsIgnoreCase(client.getClientCode())) {
            client.setClientCode(data.getClientCode().toUpperCase());
            needUpdate = true;
        }
        
        if (data.getClientName() != null && !data.getClientName().equalsIgnoreCase(client.getClientName())) {
            client.setClientName(data.getClientName());
            needUpdate = true;
        }

        if (!needUpdate) {
            return OnResponse.onSuccess(mapToClientDto(client), HttpStatus.OK);
        }

        client = clientRepository.save(client);

        if (client == null) {
            throw new ServiceException("The client is already regitered");
        }

        return OnResponse.onSuccess(mapToClientDto(client), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteClient(UUID clientId) throws Exception {

        Client client = clientRepository.findByClientId(clientId);

        if (client == null) {
            throw new ServiceException("The client was not found");
        }

        clientRepository.delete(client);

        return OnResponse.onSuccess(clientId, HttpStatus.NO_CONTENT);
    }

    private ClientResponse mapToClientDto(Client data) {

        return Builder.set(ClientResponse.class)
            .with(u -> u.setClientId(data.getClientId()))
            .with(u -> u.setClientCode(data.getClientCode()))
            .with(u -> u.setClientName(data.getClientName()))
            .build();
    }
}
