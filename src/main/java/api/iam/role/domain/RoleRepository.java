package api.iam.role.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleRepository {

    public Page<Role> findAll(Pageable pageable, Role role);

    public Role findByRoleId(UUID roleId);

    public Role save(Role role) throws Exception;

    public void delete(Role role) throws Exception;
}
