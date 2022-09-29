package api.iam.role.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.iam.role.domain.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
	
	Role findByRoleId(UUID roleId);
}
