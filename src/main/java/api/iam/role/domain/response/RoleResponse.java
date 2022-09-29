package api.iam.role.domain.response;

import java.util.UUID;

public class RoleResponse {

    private UUID roleId;

    private String roleName;

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role ["
            + "roleId=" + roleId
            + ", roleName=" + roleName
            + "]";
    }
}
