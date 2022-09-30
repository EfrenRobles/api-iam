package api.iam.scope.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import api.iam.scope.domain.Scope;

public interface ScopeRepository extends JpaRepository<Scope, UUID> {
	
	Scope findByScopeId(UUID scopeId);
}
