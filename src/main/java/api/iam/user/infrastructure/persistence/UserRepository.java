package api.iam.user.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.iam.user.domain.User;

interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserEmail(String email);

    User findByUserId(UUID userId);
}
