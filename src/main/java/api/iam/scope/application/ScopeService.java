package api.iam.scope.application;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import api.iam.scope.domain.request.AddScopeRequest;
import api.iam.scope.domain.request.UpdateScopeRequest;
import api.iam.scope.domain.response.ScopeResponse;
import api.shared.domain.response.PaginationResponse;

public interface ScopeService {

    public ScopeResponse getScope(UUID scopeId) throws Exception;

    public List<String> getScopeInScopeName(List<String> scopeList) throws Exception;

    public PaginationResponse getAllScope(Pageable pageable, ScopeResponse scope);

    public ScopeResponse addScope(AddScopeRequest request) throws Exception;

    public ScopeResponse updateScope(UpdateScopeRequest request) throws Exception;

    public UUID deleteScope(UUID scopeId) throws Exception;
}
