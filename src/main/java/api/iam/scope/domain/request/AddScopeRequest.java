package api.iam.scope.domain.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.validator.constraints.Length;

public class AddScopeRequest {

    @NotBlank(message = "The scope name is required.")
    @Length(min = 4, max = 24, message = "The length of scope name must be between 4 and 24 characters.")
    private String scopeName;

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    @Override
    public String toString() {
        return "AddScopeCustomRequest ["
            + ", scopeName=" + scopeName
            + "]";
    }
}
