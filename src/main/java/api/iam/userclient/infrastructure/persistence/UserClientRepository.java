package api.iam.userclient.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.iam.userclient.domain.UserClient;
import api.iam.userclient.domain.UserClientId;

interface UserClientRepository extends JpaRepository<UserClient, UserClientId> {

    Optional<UserClient> findById(UserClientId userClientId);
}
