package api.iam.role.infrastructure.controller;

import java.util.UUID;

import javax.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import api.shared.domain.response.OnResponse;
import api.shared.application.PageService;
import api.shared.infrastructure.PaginationConstant;
import api.shared.infrastructure.annotation.Scope;

@RestController
@RequestMapping("/api/v1/role")
@Validated
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Scope(value = "ROLE.VIEW")
    @GetMapping(params = "roleId")
    public ResponseEntity<?> getRoleByRoleId(@RequestParam(value = "roleId") UUID roleId) throws Exception {

        return OnResponse.onSuccess(roleService.getRole(roleId), HttpStatus.OK);
    }

    @Scope(value = "ROLE.VIEW.LIST")
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

        return OnResponse.onSuccessPagination(roleService.getAllRole(pageable, role), HttpStatus.OK);
    }

    @Scope(value = "ROLE.ADD")
    @PostMapping
    public ResponseEntity<?> postRole(@Valid @RequestBody AddRoleRequest role) throws Exception {

        return OnResponse.onSuccess(roleService.addRole(role), HttpStatus.CREATED);
    }

    @Scope(value = "ROLE.EDIT")
    @PatchMapping
    public ResponseEntity<?> patchRole(
        @RequestParam UUID roleId,
        @Valid @RequestBody UpdateRoleRequest role
    ) throws Exception {
        role.setRoleId(roleId);
        
        return OnResponse.onSuccess(roleService.updateRole(role), HttpStatus.OK);
    }

    @Scope(value = "ROLE.DELETE")
    @DeleteMapping
    public ResponseEntity<?> deleteRole(@RequestParam UUID roleId) throws Exception {

        return OnResponse.onSuccess(roleService.deleteRole(roleId), HttpStatus.NO_CONTENT);
    }
}
