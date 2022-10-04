package api.iam.role.application;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import api.shared.domain.Builder;
import api.shared.domain.exception.ServiceException;
import api.shared.domain.response.PaginationResponse;
import api.iam.role.domain.RoleRepository;
import api.iam.role.domain.Role;
import api.iam.role.domain.request.AddRoleRequest;
import api.iam.role.domain.request.UpdateRoleRequest;
import api.iam.role.domain.response.RoleResponse;

public class RoleServiceImp implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    public static RoleServiceImp build(RoleRepository roleRepository) {

        return new RoleServiceImp(roleRepository);
    }

    @Override
    public RoleResponse getRole(UUID roleId) throws Exception {

        Role role = roleRepository.findByRoleId(roleId);

        if (role == null) {
            throw new ServiceException("Role not found");
        }

        return mapToRoleDto(role);
    }

    @Override
    public PaginationResponse getAllRole(Pageable pageable, RoleResponse data) {

        Builder<Role> builder = Builder.set(Role.class);

        if (data.getRoleName() != null) {
            builder.with(r -> r.setRoleName(data.getRoleName().toUpperCase()));
        }

        Role role = builder.build();

        Page<Role> roles = roleRepository.findAll(pageable, role);

        List<Role> roleList = roles.getContent();
        List<RoleResponse> content = roleList
            .stream()
            .map(r -> mapToRoleDto(r))
            .toList();

        return Builder.set(PaginationResponse.class)
            .with(p -> p.setData(content))
            .with(p -> p.setPage((short) roles.getNumber()))
            .with(p -> p.setLimit((byte) roles.getSize()))
            .with(p -> p.setTotalItems((short) roles.getTotalElements()))
            .with(p -> p.setTotalPages((short) roles.getTotalPages()))
            .with(p -> p.setLast(roles.isLast()))
            .build();
    }

    @Override
    public RoleResponse addRole(AddRoleRequest data) throws Exception {

        Role role = Builder.set(Role.class)
            .with(u -> u.setRoleId(UUID.randomUUID()))
            .with(u -> u.setRoleName(data.getRoleName().toUpperCase()))
            .build();

        role = roleRepository.save(role);

        if (role == null) {
            throw new ServiceException("The role is already registered");
        }

        return mapToRoleDto(role);
    }

    @Override
    public RoleResponse updateRole(UpdateRoleRequest data) throws Exception {

        Boolean needUpdate = false;

        Role role = roleRepository.findByRoleId(data.getRoleId());

        if (role == null) {
            throw new ServiceException("The role was not found");
        }

        if (data.getRoleName() != null && !data.getRoleName().equalsIgnoreCase(role.getRoleName())) {
            role.setRoleName(data.getRoleName().toUpperCase());
            needUpdate = true;
        }

        if (needUpdate) {
            role = roleRepository.save(role);

            if (role == null) {
                throw new ServiceException("The role is already regitered");
            }
        }

        return mapToRoleDto(role);
    }

    @Override
    public UUID deleteRole(UUID roleId) throws Exception {

        Role role = roleRepository.findByRoleId(roleId);

        if (role == null) {
            throw new ServiceException("The role was not found");
        }

        roleRepository.delete(role);

        return roleId;
    }

    private RoleResponse mapToRoleDto(Role data) {

        return Builder.set(RoleResponse.class)
            .with(u -> u.setRoleId(data.getRoleId()))
            .with(u -> u.setRoleName(data.getRoleName()))
            .build();
    }
}
