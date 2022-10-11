package api.iam.role.application;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import api.iam.role.domain.request.AddRoleRequest;
import api.iam.role.domain.request.UpdateRoleRequest;
import api.iam.role.domain.response.RoleResponse;
import api.shared.domain.response.PaginationResponse;

public interface RoleService {

    public RoleResponse getRole(UUID roleId) throws Exception;

    public PaginationResponse getAllRole(Pageable pageable, RoleResponse role);

    public RoleResponse addRole(AddRoleRequest request) throws Exception;

    public RoleResponse updateRole(UpdateRoleRequest request) throws Exception;

    public UUID deleteRole(UUID roleId) throws Exception;
}