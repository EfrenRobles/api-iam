package api.iam.role.infrastructure.controller;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.iam.role.application.RoleService;
import api.iam.role.domain.request.AddRoleRequest;
import api.iam.role.domain.request.UpdateRoleRequest;
import api.iam.role.domain.response.RoleResponse;
import api.shared.domain.Builder;
import api.shared.application.PageService;
import api.shared.infrastructure.PaginationConstant;

@RestController
@RequestMapping("/api/v1/role")
@Validated
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(params = "roleId")
    public ResponseEntity<?> getRoleByRoleId(@RequestParam(value = "roleId") UUID roleId) throws Exception {

        return roleService.getRole(roleId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getRoleList(
        @RequestParam(value = "page", defaultValue = PaginationConstant.PAGE_DEFAULT, required = false) Short page,
        @RequestParam(value = "limit", defaultValue = PaginationConstant.LIMIT_DEFAULT, required = false) Byte limit,
        @RequestParam(value = "sortBy", defaultValue = "roleId", required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = PaginationConstant.SORT_ASC, required = false) String sortDir,
        @RequestParam(value = "roleName", required = false) @Length(min = 4, max = 24) String roleName
    ) {
        Pageable pageable = Builder.set(PageService.class)
            .with(p -> p.setPage(page))
            .with(p -> p.setLimit(limit))
            .with(p -> p.setSortBy(sortBy))
            .with(p -> p.setSortDir(sortDir))
            .build()
            .getPageable();

        RoleResponse role = Builder.set(RoleResponse.class)
            .with(u -> u.setRoleName(roleName))
            .build();

        return roleService.getAllRole(pageable, role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> postRole(@Valid @RequestBody AddRoleRequest role) throws Exception {

        return roleService.addRole(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping
    public ResponseEntity<?> patchRole(
        @RequestParam UUID roleId,
        @Valid @RequestBody UpdateRoleRequest role
    ) throws Exception {
        role.setRoleId(roleId);
        
        return roleService.updateRole(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteRole(@RequestParam UUID roleId) throws Exception {

        return roleService.deleteRole(roleId);
    }

}
