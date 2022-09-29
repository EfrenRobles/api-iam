package api.iam.role.domain.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Length;

public class AddRoleRequest {

    @NotBlank(message = "The role name is required.")
    @Length(min = 4, max = 24, message = "The length of role name must be between 4 and 24 characters.")
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "AddRoleCustomRequest ["
            + ", roleName=" + roleName
            + "]";
    }
}
