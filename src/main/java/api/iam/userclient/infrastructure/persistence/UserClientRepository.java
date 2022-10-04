package api.iam.userclient.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.iam.userclient.domain.UserClient;

interface UserClientRepository extends JpaRepository<UserClient, UUID> {

}
