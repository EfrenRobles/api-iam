package api.iam.user.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.iam.user.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	
	User findByUserEmail(String email);
	
	User findByUserId(UUID userId);
}
