package api.iam.scope.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScopeRepository {

    public Page<Scope> findAll(Pageable pageable, Scope scope);

    public Scope findByScopeId(UUID scopeId);

    public List<?> findScopeInScopeName(List<String> scopeList);

    public Scope save(Scope scope) throws Exception;

    public void delete(Scope scope) throws Exception;
}
