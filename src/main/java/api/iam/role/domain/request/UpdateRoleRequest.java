package api.iam.role.domain.request;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

public class UpdateRoleRequest {

    @Nullable
    private UUID roleId;

    @Nullable
    @Length(min = 4, max = 24, message = "The length of role name must be between 4 and 24 characters.")
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
        return "UpdateRoleCustomRequest ["
            + "roleId=" + roleId
            + ", roleName=" + roleName
            + "]";
    }
}
