package api.iam.client.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.iam.client.domain.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
	
	Client findByClientId(UUID clientId);
}
