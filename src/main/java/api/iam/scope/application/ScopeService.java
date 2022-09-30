package api.iam.scope.application;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import api.iam.scope.domain.request.AddScopeRequest;
import api.iam.scope.domain.request.UpdateScopeRequest;
import api.iam.scope.domain.response.ScopeResponse;
import api.shared.application.PageService;

public interface ScopeService {

	public ResponseEntity<?> getScope(UUID scopeId) throws Exception;

	public ResponseEntity<?> getAllScope(Pageable pageable, ScopeResponse scope);

	public ResponseEntity<?> addScope(AddScopeRequest request) throws Exception;

	public ResponseEntity<?> updateScope(UpdateScopeRequest request) throws Exception;
	
	public ResponseEntity<?> deleteScope(UUID scopeId) throws Exception;
}
