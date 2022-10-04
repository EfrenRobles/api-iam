package api.iam.scope.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import api.shared.domain.Builder;
import api.shared.domain.exception.ServiceException;
import api.shared.domain.response.PaginationResponse;
import api.iam.scope.domain.ScopeRepository;
import api.iam.scope.domain.Scope;
import api.iam.scope.domain.request.AddScopeRequest;
import api.iam.scope.domain.request.UpdateScopeRequest;
import api.iam.scope.domain.response.ScopeResponse;

public class ScopeServiceImp implements ScopeService {

    private ScopeRepository scopeRepository;

    public ScopeServiceImp(ScopeRepository scopeRepository) {

        this.scopeRepository = scopeRepository;
    }

    public static ScopeServiceImp build(ScopeRepository scopeRepository) {

        return new ScopeServiceImp(scopeRepository);
    }

    @Override
    public ScopeResponse getScope(UUID scopeId) throws Exception {

        Scope scope = scopeRepository.findByScopeId(scopeId);

        if (scope == null) {
            throw new ServiceException("Scope not found");
        }

        return mapToScopeDto(scope);
    }

    @Override
    public PaginationResponse getAllScope(Pageable pageable, ScopeResponse data) {

        Builder<Scope> builder = Builder.set(Scope.class);

        if (data.getScopeName() != null) {
            builder.with(r -> r.setScopeName(data.getScopeName().toUpperCase()));
        }

        Scope scope = builder.build();

        Page<Scope> scopes = scopeRepository.findAll(pageable, scope);

        List<Scope> scopeList = scopes.getContent();
        List<ScopeResponse> content = scopeList
            .stream()
            .map(r -> mapToScopeDto(r))
            .toList();

        return Builder.set(PaginationResponse.class)
            .with(p -> p.setData(content))
            .with(p -> p.setPage((short) scopes.getNumber()))
            .with(p -> p.setLimit((byte) scopes.getSize()))
            .with(p -> p.setTotalItems((short) scopes.getTotalElements()))
            .with(p -> p.setTotalPages((short) scopes.getTotalPages()))
            .with(p -> p.setLast(scopes.isLast()))
            .build();
    }

    @Override
    public List<String> getScopeInScopeName(List<String> scopeList) throws Exception {

        List<String> listCloned = new ArrayList<>(scopeList);

        // Using List<?> to avoid the warning on scopeList.removeAll(scope);
        List<?> scope = scopeRepository.findScopeInScopeName(listCloned);
        listCloned.removeAll(scope);

        if (listCloned.size() > 0) {
            throw new ServiceException("The scope(s): " + listCloned.toString() + " do(es) not exist");
        }

        return listCloned;
    }

    @Override
    public ScopeResponse addScope(AddScopeRequest data) throws Exception {

        Scope scope = Builder.set(Scope.class)
            .with(u -> u.setScopeId(UUID.randomUUID()))
            .with(u -> u.setScopeName(data.getScopeName().toUpperCase()))
            .build();

        scope = scopeRepository.save(scope);

        if (scope == null) {
            throw new ServiceException("The scope is already registered");
        }

        return mapToScopeDto(scope);
    }

    @Override
    public ScopeResponse updateScope(UpdateScopeRequest data) throws Exception {

        Boolean needUpdate = false;

        Scope scope = scopeRepository.findByScopeId(data.getScopeId());

        if (scope == null) {
            throw new ServiceException("The scope was not found");
        }

        if (data.getScopeName() != null && !data.getScopeName().equalsIgnoreCase(scope.getScopeName())) {
            scope.setScopeName(data.getScopeName().toUpperCase());
            needUpdate = true;
        }

        if (needUpdate) {
            scope = scopeRepository.save(scope);

            if (scope == null) {
                throw new ServiceException("The scope is already regitered");
            }
        }

        return mapToScopeDto(scope);
    }

    @Override
    public UUID deleteScope(UUID scopeId) throws Exception {

        Scope scope = scopeRepository.findByScopeId(scopeId);

        if (scope == null) {
            throw new ServiceException("The scope was not found");
        }

        scopeRepository.delete(scope);

        return scopeId;
    }

    private ScopeResponse mapToScopeDto(Scope data) {

        return Builder.set(ScopeResponse.class)
            .with(u -> u.setScopeId(data.getScopeId()))
            .with(u -> u.setScopeName(data.getScopeName()))
            .build();
    }
}
