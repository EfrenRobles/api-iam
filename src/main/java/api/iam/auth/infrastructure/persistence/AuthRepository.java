package api.iam.auth.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.iam.auth.domain.UserAuth;

interface AuthRepository extends JpaRepository<UserAuth, UUID> {

    UserAuth findByUserEmail(String email);

    UserAuth findByUserId(UUID userId);
}
