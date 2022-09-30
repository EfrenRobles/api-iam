package api.iam.role.application;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import api.iam.role.domain.request.AddRoleRequest;
import api.iam.role.domain.request.UpdateRoleRequest;
import api.iam.role.domain.response.RoleResponse;
import api.shared.application.PageService;

public interface RoleService {

	public ResponseEntity<?> getRole(UUID roleId) throws Exception;

	public ResponseEntity<?> getAllRole(Pageable pageable, RoleResponse role);

	public ResponseEntity<?> addRole(AddRoleRequest request) throws Exception;

	public ResponseEntity<?> updateRole(UpdateRoleRequest request) throws Exception;
	
	public ResponseEntity<?> deleteRole(UUID roleId) throws Exception;
}