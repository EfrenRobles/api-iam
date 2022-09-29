package api.iam.client.application;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import api.iam.client.domain.request.AddClientRequest;
import api.iam.client.domain.request.UpdateClientRequest;
import api.iam.client.domain.response.ClientResponse;
import api.shared.application.PageService;

public interface ClientService {

	public ResponseEntity<?> getClient(UUID clientId) throws Exception;

	public ResponseEntity<?> getAllClient(Pageable pageable, ClientResponse client);

	public ResponseEntity<?> addClient(AddClientRequest request) throws Exception;

	public ResponseEntity<?> updateClient(UpdateClientRequest request) throws Exception;
	
	public ResponseEntity<?> deleteClient(UUID clientId) throws Exception;
}
